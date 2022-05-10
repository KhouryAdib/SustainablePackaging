package tct.basewrappers;

import tct.ProjectClassFactory;

import org.opentest4j.AssertionFailedError;

import java.math.BigDecimal;

/**
 * Represents a PolyBag from the base project.
 */
public class PolyBagWrapper extends PackagingWrapper {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "PolyBag";

    private static Class<?> wrappedClass;

    /**
     * Instantiates a new PolyBagWrapper with a PolyBag instance.
     * @param wrappedInstance The PolyBag instance to wrap
     */
    public PolyBagWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Returns PolyBag volume.
     * @return The volume
     */
    public BigDecimal getVolume() {
        return (BigDecimal) (invokeInstanceMethodWithReturnValue(getMethod("getVolume")));
    }

    /**
     * Returns PolyBag mass.
     * @return The mass
     */
    public BigDecimal getMass() {
        return (BigDecimal) (invokeInstanceMethodWithReturnValue(getMethod("getMass")));
    }

    /**
     * Computes the mass of the given PolyBag. Does not use the wrapped instance's
     * logic, this method is for evaluating correctness of wrapped instance's logic.
     *
     * @param polyBagWrapper The PolyBag to compute mass of
     * @return The correct mass of PolyBag
     */
    public static BigDecimal computeMass(final PolyBagWrapper polyBagWrapper) {
        return new BigDecimal(
            Math.ceil(Math.sqrt(polyBagWrapper.getVolume().doubleValue()) *
                      MaterialEnumWrapper.LAMINATED_PLASTIC_MASS_FACTOR.doubleValue()
            )
        );
    }

    /**
     * Indicates whether tests are allowed access to PolyBag or not. (The not case corresponds
     * to the class not being sufficiently implemented by participant yet.)
     *
     * Tests are supported if the PolyBag class exists and it implements a getMass() method.
     *
     * @return {@code true} if tests that require PolyBag should be allowed to run, {@code false}
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
        return PolyBagWrapper.getWrappedClassStatic();
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
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
