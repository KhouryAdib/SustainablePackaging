package tct.basewrappers;

import tct.ProjectClassFactory;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

/**
 * Represents a CarbonCostStrategy from the base project, which is a class that participants
 * create as part of completing the project.
 */
public class CarbonCostStrategyWrapper extends CostStrategyWrapper {
    private static final String WRAPPED_CLASS_SIMPLE_NAME = "CarbonCostStrategy";
    private static Class<?> wrappedClass;

    private static final BigDecimal CARBON_UNITS_PER_GRAM_CORRUGATE = new BigDecimal("0.017");
    private static final BigDecimal CARBON_UNITS_PER_GRAM_LAMINATED_PLASTIC = new BigDecimal("0.012");

    /**
     * Instantiates a new CarbonCostStrategyWrapper with a CarbonCostStrategy instance.
     *
     * @param wrappedInstance The CarbonCostStrategy instance to wrap
     */
    public CarbonCostStrategyWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Creates a new CarbonCostStrategyWrapper.
     */
    public CarbonCostStrategyWrapper() {
        this(invokeConstructor(CarbonCostStrategyWrapper.getNoArgsConstructor()));
    }

    /**
     * Computes the carbon cost of the provided box. Does *not* use wrapped instance;
     * instead, this method computes the correct cost for the purpose of comparing with
     * the results from wrapped instance.
     *
     * @param boxWrapper The box to compute the carbon cost of
     * @return The box's carbon cost in carbon units
     */
    public static BigDecimal computeCarbonCost(final BoxWrapper boxWrapper) {
        return BoxWrapper.computeMass(boxWrapper).multiply(CARBON_UNITS_PER_GRAM_CORRUGATE);
    }

    /**
     * Computes the carbon cost of the provided polybag. Does *not* use wrapped instance;
     * instead, this method computes the correct cost for the purpose of comparing with
     * the results from wrapped instance.
     *
     * @param polyBagWrapper The polybag to compute the carbon cost of
     * @return The polybag's carbon cost in carbon units
     */
    public static BigDecimal computeCarbonCost(final PolyBagWrapper polyBagWrapper) {
        return PolyBagWrapper.computeMass(polyBagWrapper).multiply(CARBON_UNITS_PER_GRAM_LAMINATED_PLASTIC);
    }

    /**
     * Indicates whether tests are allowed access to CarbonCostStrategy or not. (The not case corresponds
     * to the class not being written by participant yet.)
     *
     * @return {@code true} if tests that require CarbonCostStrategy should be allowed to run, {@code false}
     * otherwise
     */
    public static boolean testsEnabled() {
        return getWrappedClassStatic() != null;
    }

    @Override
    public Class<?> getWrappedClass() {
        return CarbonCostStrategyWrapper.getWrappedClassStatic();
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
                wrappedClass = ProjectClassFactory.findClass(WRAPPED_CLASS_SIMPLE_NAME);
            } catch (AssertionFailedError e) {
                return null;
            }
        }

        return wrappedClass;
    }

    private static Constructor<?> getNoArgsConstructor() {
        return getConstructor(getWrappedClassStatic());
    }
}
