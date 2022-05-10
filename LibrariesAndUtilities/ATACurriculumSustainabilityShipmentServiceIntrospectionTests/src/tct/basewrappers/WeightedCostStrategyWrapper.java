package tct.basewrappers;

import tct.ProjectClassFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents a WeightedCostStrategy from the base project, which is a class that participants
 * create as part of completing the project.
 *
 * Supports constructing via:
 * * {@code (MonetaryCostStrategy, CarbonCostStrategy)},
 * * {@code (Map<BigDecimal, CostStrategy>)}
 * * {@code (Builder)}, where {@code Builder} has an {@code aaddStrategyWithWeight(CostStrategy, BigDecimal)} method
 */
public class WeightedCostStrategyWrapper  extends CostStrategyWrapper {
    private static final Logger LOG = LogManager.getLogger();
    private static final String WRAPPED_CLASS_SIMPLE_NAME = "WeightedCostStrategy";
    // TODO: can look at param types instead of expecting a certain name
    private static final String WRAPPED_CLASS_BUILDER_ADD_METHOD_NAME = "addStrategyWithWeight";
    private static final BigDecimal MONETARY_COST_STRATEGY_WEIGHT = new BigDecimal("0.8");
    private static final BigDecimal CARBON_COST_STRATEGY_WEIGHT = new BigDecimal("0.2");

    private static Class<?> builderClass;

    /**
     * Instantiates a new WeighteCostStrategyWrapper with a Box instance.
     * @param wrappedInstance The Box instance to wrap
     */
    public WeightedCostStrategyWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Creates a new WeightedCostStrategyWrapper with provided cost strategies.
     *
     * @param monetaryCostStrategyWrapper The monetary cost strategy to use
     * @param carbonCostStrategyWrapper The carbon cost strategy to use
     */
    public WeightedCostStrategyWrapper(
            final MonetaryCostStrategyWrapper monetaryCostStrategyWrapper,
            final CarbonCostStrategyWrapper carbonCostStrategyWrapper) {

        this(WeightedCostStrategyWrapper.invokeApplicableConstruction(
            monetaryCostStrategyWrapper, carbonCostStrategyWrapper)
        );
    }

    public String getName() {
        return (String) invokeInstanceMethodWithReturnValue(getGetNameMethod());
    }

    /**
     * Computes the weighted cost of the given shipment option, using the wrapped
     * object.
     *
     * @param shipmentOptionWrapper The shipment option to compute the cost for
     * @return The weighted cost of the shipment option
     */
    public ShipmentCostWrapper getCost(final ShipmentOptionWrapper shipmentOptionWrapper) {
        return new ShipmentCostWrapper(invokeInstanceMethodWithReturnValue(
            getGetCostMethod(), shipmentOptionWrapper.getWrappedInstance()
        ));
    }

    /**
     * Computes the weighted cost of the provided box. Does *not* use wrapped instance;
     * instead, this method computes the correct cost for the purpose of comparing with
     * the results from wrapped instance.
     *
     * @param boxWrapper The box to compute the weighted cost of
     * @return The box's weighted cost in weighted cost units
     */
    public static BigDecimal computeWeightedCost(final BoxWrapper boxWrapper) {
        return MONETARY_COST_STRATEGY_WEIGHT.multiply(MonetaryCostStrategyWrapper.computeMonetaryCost(boxWrapper))
            .add(CARBON_COST_STRATEGY_WEIGHT.multiply(CarbonCostStrategyWrapper.computeCarbonCost(boxWrapper)));
    }

    /**
     * Computes the weighted cost of the provided polybag. Does *not* use wrapped instance;
     * instead, this method computes the correct cost for the purpose of comparing with
     * the results from wrapped instance.
     *
     * @param polyBagWrapper The polybag to compute the weighted cost of
     * @return The polybag's weighted cost in weighted cost units
     */
    public static BigDecimal computeWeightedCost(final PolyBagWrapper polyBagWrapper) {
        return MONETARY_COST_STRATEGY_WEIGHT.multiply(MonetaryCostStrategyWrapper.computeMonetaryCost(polyBagWrapper))
            .add(CARBON_COST_STRATEGY_WEIGHT.multiply(CarbonCostStrategyWrapper.computeCarbonCost(polyBagWrapper)));
    }

    /**
     * Indicates whether tests should be run on this wrapper class or not.
     *
     * @return {@code true} if tests should be run; {@code false} otherwise
     */
    public static boolean testsEnabled() {
        try {
            getWrappedClassStatic();
        } catch (AssertionFailedError e) {
            return false;
        }

        return true;
    }

    @Override
    public Class<?> getWrappedClass() {
        return WeightedCostStrategyWrapper.getWrappedClassStatic();
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
     * @return The class wrapped by this wrapper class
     */
    public static Class<?> getWrappedClassStatic() {
        return ProjectClassFactory.findClass(WRAPPED_CLASS_SIMPLE_NAME);
    }

    private static Object invokeApplicableConstruction(
            final MonetaryCostStrategyWrapper monetaryCostStrategyWrapper,
            final CarbonCostStrategyWrapper carbonCostStrategyWrapper) {
        if (wrappedClassUsesMonetaryAndCarbonCostStrategyConstructor()) {
            return invokeConstructor(
                getMonetaryAndCarbonCostStrategyConstructor(),
                monetaryCostStrategyWrapper.getWrappedInstance(),
                carbonCostStrategyWrapper.getWrappedInstance()
            );
        } else if (wrappedClassUsesMapConstructor()) {
            Map map = new HashMap();
            map.put(MONETARY_COST_STRATEGY_WEIGHT, monetaryCostStrategyWrapper.getWrappedInstance());
            map.put(CARBON_COST_STRATEGY_WEIGHT, carbonCostStrategyWrapper.getWrappedInstance());
            return invokeConstructor(getMapConstructor(), map);
        } else if (wrappedClassUsesBuilder()) {
            Object wrappedClassBuilder =
                 invokeStaticMethodWithReturnValue(getWrappedClassBuilderMethod());
            wrappedClassBuilder = invokeInstanceMethodWithReturnValue(
                wrappedClassBuilder,
                getAddStrategyWithWeightBuilderMethod(),
                monetaryCostStrategyWrapper.getWrappedInstance(),
                MONETARY_COST_STRATEGY_WEIGHT
            );
            wrappedClassBuilder = invokeInstanceMethodWithReturnValue(
                wrappedClassBuilder,
                getAddStrategyWithWeightBuilderMethod(),
                carbonCostStrategyWrapper.getWrappedInstance(),
                CARBON_COST_STRATEGY_WEIGHT
            );
            return invokeInstanceMethodWithReturnValue(
                wrappedClassBuilder,
                getBuildBuilderMethod()
            );
        } else {
            fail(String.format(
                "%s doesn't seem to support a (%s, %s) constructor," +
                    " (Map<%s, %s>) constructor," +
                    " nor nested Builder class with %s() method. Please implement" +
                    " one of the above for this task.",
                getWrappedClassStatic().getSimpleName(),
                MonetaryCostStrategyWrapper.getWrappedClassStatic().getSimpleName(),
                CarbonCostStrategyWrapper.getWrappedClassStatic().getSimpleName(),
                BigDecimal.class.getSimpleName(),
                CostStrategyWrapper.getWrappedClassStatic().getSimpleName(),
                WRAPPED_CLASS_BUILDER_ADD_METHOD_NAME)
            );
        }

        return null;
    }

    // Constructing via two-arg constructor:

    private static boolean wrappedClassUsesMonetaryAndCarbonCostStrategyConstructor() {
        try {
            getMonetaryAndCarbonCostStrategyConstructor();
        } catch (AssertionFailedError e) {
            return false;
        }

        return true;
    }

    private static Constructor<?> getMonetaryAndCarbonCostStrategyConstructor() {
        return getConstructor(
            getWrappedClassStatic(),
            MonetaryCostStrategyWrapper.getWrappedClassStatic(),
            CarbonCostStrategyWrapper.getWrappedClassStatic()
        );
    }

    // Constructing via Map<BigDecimal, CostStrategy>

    private static boolean wrappedClassUsesMapConstructor() {
        try {
            Constructor ctor = getMapConstructor();
            assertEquals(1, ctor.getGenericParameterTypes().length);
            // get the type parameters of the map
            Type[] types = ((ParameterizedType) ctor.getGenericParameterTypes()[0]).getActualTypeArguments();

            // The Map key type is BigDecimal
            // The Map value type is CostStrategy
            assertEquals(2, types.length);
            assertEquals(types[0], BigDecimal.class);
            assertEquals(types[1], CostStrategyWrapper.getWrappedClassStatic());
        } catch (Error | Exception e) {
            LOG.error(e);
            return false;
        }

        return true;
    }

    private static Constructor<?> getMapConstructor() {

        return getConstructor(
            getWrappedClassStatic(),
            Map.class
        );
    }

    // Constructing via Builder

    private static boolean wrappedClassUsesBuilder() {
        try {
            getWrappedClassBuilderMethod();
        } catch (AssertionFailedError e) {
            LOG.error(e);
            return false;
        }

        return true;
    }

    private static Method getWrappedClassBuilderMethod() {
        return getMethod(getWrappedClassStatic(), "builder");
    }

    private static Method getAddStrategyWithWeightBuilderMethod() {
        return getMethod(
            getWrappedClassBuilderClass(),
            WRAPPED_CLASS_BUILDER_ADD_METHOD_NAME,
            CostStrategyWrapper.getWrappedClassStatic(),
            BigDecimal.class
        );
    }

    private static Method getBuildBuilderMethod() {
        return getMethod(getWrappedClassBuilderClass(), "build");
    }

    private static Class<?> getWrappedClassBuilderClass() {
        try {
            if (builderClass != null) {
                return builderClass;
            }

            builderClass = ProjectClassFactory.findNestedClass("cost",
                "Builder",
                WRAPPED_CLASS_SIMPLE_NAME);
            return builderClass;
        } catch (Exception e) {
            // throwing this so wrappedClassUsesBuilder will catch and return false
            LOG.error(e);
            throw new AssertionFailedError("No nested builder class found.");
        }
    }

    private Method getGetNameMethod() {
        return getMethod(getWrappedClass(), String.class);
    }

    /* expose this to test class */
    Method getGetCostMethod() {
        return getMethod(getWrappedClass(), ShipmentCostWrapper.getWrappedClassStatic(),
            ShipmentOptionWrapper.getWrappedClassStatic());
    }
}
