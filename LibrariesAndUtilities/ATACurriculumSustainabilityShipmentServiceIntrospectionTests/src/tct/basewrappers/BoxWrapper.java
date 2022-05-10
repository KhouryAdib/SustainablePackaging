package tct.basewrappers;

import tct.ProjectClassFactory;

import org.opentest4j.AssertionFailedError;

import java.math.BigDecimal;

/**
 * Represents a Box class from the base project (though Box doesn't
 * exist in the starting state, participants will create it in MT1).
 *
 * Note that this assumes participants create getters named
 * getLength, getWidth, getHeight. Will fail tests explaining this
 * expectation if they don't provide exactly these (with BigDecimal return type).
 *
 * Since Box will probably be instantiated via a Builder, this wrapper
 * only provides for construction via the WrapperBase-arg constructor.
 *
 * Because Box does not exist in the participants' packages at the time
 * of distribution, it is difficult to unit test this class, as we
 * are unable to create our own instances from within this package
 * for unit testing purposes.
 */
public class BoxWrapper extends PackagingWrapper {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "Box";
    private static Class<?> wrappedClass;

    /**
     * Instantiates a new BoxWrapper with a Box instance.
     * @param wrappedInstance The Box instance to wrap
     */
    public BoxWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Returns Box length.
     * @return The length
     */
    public BigDecimal getLength() {
        return (BigDecimal) (invokeInstanceMethodWithReturnValue(getMethod("getLength")));
    }

    /**
     * Returns Box width.
     * @return The width
     */
    public BigDecimal getWidth() {
        return (BigDecimal) (invokeInstanceMethodWithReturnValue(getMethod("getWidth")));
    }

    /**
     * Returns Box height.
     * @return The height
     */
    public BigDecimal getHeight() {
        return (BigDecimal) (invokeInstanceMethodWithReturnValue(getMethod("getHeight")));
    }

    /**
     * Returns Box mass.
     * @return The mass
     */
    public BigDecimal getMass() {
        return (BigDecimal) (invokeInstanceMethodWithReturnValue(getMethod("getMass")));
    }

    /**
     * Computes the mass of the given Box. Does not use the wrapped instance's
     * logic, this method is for evaluating correctness of wrapped instance's logic.
     *
     * @param boxWrapper The Box to compute mass of
     * @return The correct mass of Box
     */
    public static BigDecimal computeMass(final BoxWrapper boxWrapper) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal endArea = boxWrapper.getLength().multiply(boxWrapper.getWidth());
        BigDecimal shortSideArea = boxWrapper.getLength().multiply(boxWrapper.getHeight());
        BigDecimal longSideArea = boxWrapper.getWidth().multiply(boxWrapper.getHeight());

        return endArea
            .add(shortSideArea)
            .add(longSideArea)
            .multiply(two)
            .multiply(MaterialEnumWrapper.GRAMS_PER_SQUARE_CM_CORRUGATE);
    }

    /**
     * Indicates whether tests are allowed access to Box or not. (The not case corresponds
     * to the class not being sufficiently implemented by participant yet.)
     *
     * Tests are supported if the Box class exists and it implements a getMass() method.
     *
     * @return {@code true} if tests that require Box should be allowed to run, {@code false}
     * otherwise
     */
    public static boolean testsEnabled() {
        if (getWrappedClassStatic() == null) {
            return false;
        }

        try {
            getMethod(getWrappedClassStatic(), "getMass");
        } catch (AssertionFailedError e) {
            return false;
        }

        return true;
    }

    @Override
    public Class<?> getWrappedClass() {
        return BoxWrapper.getWrappedClassStatic();
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
        if (null == wrappedClass) {
            try {
                wrappedClass = ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
            } catch (AssertionFailedError e) {
                return null;
            }
        }

        return wrappedClass;
    }
}
