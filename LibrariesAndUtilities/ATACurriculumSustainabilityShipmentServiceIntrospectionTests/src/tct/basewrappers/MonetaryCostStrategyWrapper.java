package tct.basewrappers;

import tct.ProjectClassFactory;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

/**
 * Represents a MonetaryCostStrategy from the base project.
 */
public class MonetaryCostStrategyWrapper extends CostStrategyWrapper {
    private static final String WRAPPED_CLASS_SIMPLE_NAME = "MonetaryCostStrategy";
    private static Class<?> wrappedClass;

    private static final BigDecimal FIXED_LABOR_COST_PER_BOX = new BigDecimal("0.43");
    private static final BigDecimal FIXED_LABOR_COST_PER_POLY_BAG = new BigDecimal("0.43");
    private static final BigDecimal DOLLAR_PER_GRAM_CORRUGATE = new BigDecimal("0.005");
    private static final BigDecimal DOLLAR_PER_GRAM_LAMINATED_PLASTIC = new BigDecimal("0.25");

    /**
     * Instantiates a new MonetaryCostStrategyWrapper with a MonetaryCostStrategy instance.
     *
     * @param wrappedInstance The MonetaryCostStrategy instance to wrap
     */
    public MonetaryCostStrategyWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Creates a new MonetaryCostStrategyWrapper.
     */
    public MonetaryCostStrategyWrapper() {
        this(invokeConstructor(MonetaryCostStrategyWrapper.getNoArgsConstructor()));
    }

    /**
     * Computes the monetary cost of the provided box. Does *not* use wrapped instance;
     * instead, this method computes the correct cost for the purpose of comparing with
     * the results from wrapped instance.
     *
     * @param boxWrapper The box to compute the monetary cost of
     * @return The box's monetary cost
     */
    public static BigDecimal computeMonetaryCost(final BoxWrapper boxWrapper) {
        return FIXED_LABOR_COST_PER_BOX.add(
            BoxWrapper.computeMass(boxWrapper).multiply(DOLLAR_PER_GRAM_CORRUGATE)
        );
    }

    /**
     * Computes the monetary cost of the provided polybag. Does *not* use wrapped instance;
     * instead, this method computes the correct cost for the purpose of comparing with
     * the results from wrapped instance.
     *
     * @param polyBagWrapper The polybag to compute the monetary cost of
     * @return The polybag's monetary cost
     */
    public static BigDecimal computeMonetaryCost(final PolyBagWrapper polyBagWrapper) {
        return FIXED_LABOR_COST_PER_POLY_BAG.add(
            PolyBagWrapper.computeMass(polyBagWrapper).multiply(DOLLAR_PER_GRAM_LAMINATED_PLASTIC)
        );
    }

    /**
     * Indicates whether tests are allowed access to MonetaryCostStrategy or not. (The not case corresponds
     * to the class not being written by participant yet.)
     *
     * @return {@code true} if tests that require MonetaryCostStrategy should be allowed to run, {@code false}
     * otherwise
     */
    public static boolean testsEnabled() {
        return getWrappedClassStatic() != null;
    }

    @Override
    public Class<?> getWrappedClass() {
        return MonetaryCostStrategyWrapper.getWrappedClassStatic();
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

