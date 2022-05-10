package tct.basewrappers;

import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;

import static com.amazon.ata.test.helper.AtaTestHelper.failTestWithException;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Superclass for the wrapper classes that allows us to pretend we have
 * direct access to instances of the base project classes.
 */
public abstract class WrapperBase {
    // the object that is an instance of the base package
    private Object wrappedInstance;

    /**
     * Saves the wrapped class instance and type, ensuring the wrapped instance looks like the right type for
     * the given wrapper class.
     *
     * @param wrappedInstance the instance to be wrapped
     */
    protected WrapperBase(final Object wrappedInstance) {
        if (wrappedInstance != null && !getWrappedClass().isInstance(wrappedInstance)) {
            throw new IllegalArgumentException(
                String.format("Unexpected wrapped instance type for %s. Expected instance to be a %s, but is a %s",
                    this.getClass().getSimpleName(),
                    getWrappedClass().getSimpleName(),
                    wrappedInstance.getClass().getSimpleName())
            );
        }

        this.wrappedInstance = wrappedInstance;
    }

    /**
     * Returns this wrapper class's wrapped class.
     *
     * Used by logic in this superclass, so we need an instance method for
     * abstract/overriding/polymorphism purposes.
     *
     * Subclasses likely implement a static version of this method, which
     * this method calls. That is an expected pattern.
     *
     * @return The class wrapped by this wrapper class
     */
    public abstract Class<?> getWrappedClass();

    @Override
    public boolean equals(final Object other) {
        if (isNull()) {
            throw new NullPointerException(
                String.format("Called equals() on a %s with null wrapped instance", this.getClass().getSimpleName())
            );
        }

        if (! (other instanceof WrapperBase)) {
            return false;
        }

        WrapperBase otherWrapper = (WrapperBase) other;

        return wrappedInstance.equals(otherWrapper.getWrappedInstance());
    }

    @Override
    public int hashCode() {
        return wrappedInstance.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s containing: %s", getWrappedClass().getSimpleName(), wrappedInstance.toString());
    }

    /**
     * Returns whether the wrapped object is null.
     *
     * @return true if object is null; false if not
     */
    public boolean isNull() {
        return wrappedInstance == null;
    }

    /**
     * Returns the object wrapped by this BaseWrapper object.
     *
     * @return The wrapped instance, that is of type returned by {@code getWrappedClass}
     */
    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    /**
     * Returns the constructor specified by {@code parameterTypes} for the class indicated by {@code wrappedClass}.
     *
     * If a matching constructor is not found, will {@code fail()}.
     *
     * @param wrappedClass The class to find a constructor for
     * @param parameterTypes The list of parameter types for the requested constructor
     * @return The Constructor object, if it exists.
     */
    protected static Constructor<?> getConstructor(final Class<?> wrappedClass, Class<?>... parameterTypes) {
        Constructor<?> constructor = null;

        try {
            constructor = wrappedClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            List<String> argList = new ArrayList<>();
            for (Class<?> argClass : parameterTypes) {
                argList.add(argClass.getSimpleName());
            }

            fail(String.format(
                "Expected %s to have a (%s) constructor but did not: %s",
                wrappedClass.getSimpleName(),
                String.join(", ", argList),
                e));
        }

        return constructor;
    }

    /**
     * Calls the provided constructor with the given arguments and does exception handling,
     * assertion failure reporting.
     *
     * @param constructor The constructor to call
     * @param parameters The args to pass to the constructor
     * @param <T> The (wrapped class) type the constructor belongs to
     * @return The constructed instance
     */
    protected static <T> T invokeConstructor(final Constructor<T> constructor, Object... parameters) {
        T newInstance = null;

        try {
            newInstance = constructor.newInstance(parameters);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            List<String> argList = new ArrayList<>();
            for (Object arg : parameters) {
                argList.add(arg.toString());
            }

            failTestWithException(
                e,
                String.format("Failed to instantiate a new %s with args (%s)",
                    constructor.getDeclaringClass().getSimpleName(),
                    String.join(", ", argList))
            );
        }

        return newInstance;
    }

    /**
     * Returns the Method object corresponding to the method name provided.
     *
     * @param methodName     The name of the method requested
     * @param parameterTypes The Class objects representing the types of the method arguments
     * @return The Method object corresponding to methodName
     */
    protected Method getMethod(final String methodName, Class<?>... parameterTypes) {
        return WrapperBase.getMethod(getWrappedClass(), methodName, parameterTypes);
    }

    /**
     * Returns the Method object corresponding to the class and method name provided. This method can
     * be an instance or static method.
     *
     * @param clazz The class to fetch the {@code Method} from
     * @param methodName The name of the method requested
     * @param parameterTypes The Class objects representing the types of the method arguments
     * @return The Method object corresponding to methodName
     */
    protected static Method getMethod(final Class<?> clazz, final String methodName, Class<?>... parameterTypes) {
        Method method = null;

        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            String paramTypesToDisplay = buildParamTypesToDisplay(parameterTypes);
            fail(String.format("Expected %s to have a %s(%s) method", clazz, methodName, paramTypesToDisplay));
        }

        return method;
    }

    /**
     * Returns the Method object corresponding to the class and method signature provided. This method can
     * be an instance or static method. This only works predictably when there is one method in the class with this
     * signature.
     *
     * @param clazz The class to fetch the {@code Method} from
     * @param returnType The Class object representing the return type. For void return methods use Void.TYPE.
     * @param parameterTypes The Class objects representing the types of the method arguments
     * @return The Method object corresponding to methodName
     */
    protected static Method getMethod(final Class<?> clazz, final Class<?> returnType, Class<?>... parameterTypes) {
        Set<Method> methods = ReflectionUtils.getMethods(clazz,
                ReflectionUtils.withReturnType(returnType),
                ReflectionUtils.withParameters(parameterTypes));

        // Defensive null checking
        if (null == methods || methods.isEmpty()) {
            String paramTypesToDisplay = buildParamTypesToDisplay(parameterTypes);
            fail(String.format("Expected %s to have a method with parameters %s and return type %s",
                    clazz, paramTypesToDisplay, returnType));
            return null;
        }
        if (methods.size() > 1) {
            String paramTypesToDisplay = buildParamTypesToDisplay(parameterTypes);
            fail(String.format("Found more than one method in class, %s, with parameters %s and return type %s",
                    clazz, paramTypesToDisplay, returnType));
            return null;
        }

        return methods.iterator().next();
    }

    private static String buildParamTypesToDisplay(Class<?>... parameterTypes) {
        BinaryOperator<String> joinCommaDelimited = (combined, name) -> combined + ", " + name;

        return Arrays.stream(parameterTypes)
                .map(Class::getSimpleName)
                .reduce(joinCommaDelimited)
                .orElse("<no args>");
    }

    /**
     * Invokes the given method with the provided arguments and handles the reflection exceptions.
     *
     * @param method The method to invoke
     * @param args   The arguments to provide to the method
     * @return The return value of the method, as an {@code Object}
     */
    protected Object invokeInstanceMethodWithReturnValue(final Method method, Object... args) {
        return WrapperBase.invokeInstanceMethodWithReturnValue(wrappedInstance, method, args);
    }

    /**
     * Invokes the given method with the provided arguments on the specified object, handling the reflection
     * exceptions.
     *
     * @param invokeTarget The object to invoke the given method on
     * @param method The method to invoke
     * @param args The arguments to provide to the method
     * @return The return value of the method, as an {@code Object}
     */
    protected static Object invokeInstanceMethodWithReturnValue(
            final Object invokeTarget, final Method method, Object... args) {

        if (null == invokeTarget) {
            throw new NullPointerException(
                String.format("Attempted to call method %s on a null instance of %s",
                    method.getName(),
                    method.getDeclaringClass().getSimpleName())
            );
        }

        Object returnValue = null;

        try {
            returnValue = method.invoke(invokeTarget, args);
        } catch (IllegalAccessException e) {
            failTestWithException(e, String.format("Unable to access %s on %s",
                    method.getName(),
                    invokeTarget.getClass().getSimpleName()));
        } catch (InvocationTargetException e) {
            failTestWithException(e.getCause(), String.format("Failed to successfully call %s on %s",
                    method.getName(),
                    invokeTarget.getClass().getSimpleName()));
        }

        return returnValue;
    }

    /**
     * Calls the given static method with the provided arguments and handles the reflection exceptions.
     *
     * @param method The method to invoke
     * @param args The arguments to provide to the method
     * @return The return value of the method, as an {@code Object}
     */
    protected static Object invokeStaticMethodWithReturnValue(final Method method, Object... args) {
        Object returnValue = null;

        try {
            // for static methods, pass null for the instance method is invoked on
            returnValue = method.invoke(null, args);
        } catch (IllegalAccessException e) {
            failTestWithException(e, String.format("Unable to access %s on %s",
                    method.getName(),
                    method.getDeclaringClass().getSimpleName()));
        } catch (InvocationTargetException e) {
            failTestWithException(e.getCause(), String.format("Failed to successfully call %s on %s",
                    method.getName(),
                    method.getDeclaringClass().getSimpleName()));
        }

        return returnValue;
    }
}
