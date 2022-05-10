package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Constructor;

/**
 * Represents a FulfillmentCenter class from the base project by wrapping an instance of FulfillmentCenter
 * and exposing the necessary methods.
 *
 * When creating a FulfillmentCenterWrapper with a String argument (as if you were creating
 * a FulfillmentCenter object), use the static newFulfillmentCenterWrapper(String) method
 * (it takes care of exception handling so you don't have to).
 */
public class FulfillmentCenterWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "FulfillmentCenter";
    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);

    /**
     * Instantiates a new FulfillmentCenterWrapper with a FulfillmentCenter instance.
     * @param wrappedInstance The FulfillmentCenter instance to wrap
     */
    public FulfillmentCenterWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Instantiates a new FulfillmentCenterWrapper with the given FC code.
     *
     * @param fcCode The fulfillment center code for this FulfillmentCenterWrapper
     */
    public FulfillmentCenterWrapper(final String fcCode) {
        this(WrapperBase.invokeConstructor(FulfillmentCenterWrapper.getStringArgConstructor(), fcCode));
    }

    /**
     * Returns the underlying FC code.
     *
     * @return The FC code of the wrapped FulfillmentCenter
     */
    public String getFcCode() {
        return (String) (invokeInstanceMethodWithReturnValue(getMethod("getFcCode")));
    }

    @Override
    public Class<?> getWrappedClass() {
        return FulfillmentCenterWrapper.getWrappedClassStatic();
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
     *
     * @return The class wrapped by this wrapper class
     */
    public static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }

    private static Constructor<?> getStringArgConstructor() {
        return WrapperBase.getConstructor(WRAPPED_CLASS, String.class);
    }
}
