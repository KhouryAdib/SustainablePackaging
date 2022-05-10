package tct.basewrappers;

import tct.ProjectClassFactory;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents a CostStrategy from the base project, which is a class that participants
 * create as part of completing the project.
 *
 * CostStrategy should be an interface, but we're modeling here with an abstract class.
 */
public abstract class CostStrategyWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_SIMPLE_NAME = "CostStrategy";
    private static Class<?> wrappedClass;

    /**
     * Instantiates, passing wrapped instance to superclass.
     * @param wrappedInstance The PackagingWrapper subclass instance to wrap
     */
    protected CostStrategyWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Returns cost strategy's name.
     *
     * @return The cost strategy's name
     */
    public String getName() {
        Object name = invokeInstanceMethodWithReturnValue(getMethod(getWrappedClass(), String.class));
        if (!(name instanceof String)) {
            fail(String.format(
                "Expected %s's (%s) getName() method to return a String, but returned a %s: %s.",
                getWrappedInstance().getClass().getName(),
                getWrappedInstance().toString(),
                name.getClass().getName(),
                name.toString())
            );
        }

        return (String) name;
    }

    /**
     * The cost for the given shipment option, based on this cost strategy type. Units
     * are dependent on the specific cost strategy.
     *
     * @param shipmentOptionWrapper The shipment option to compute the cost for
     * @return The cost of the provided shipment option, based on this cost strategy type
     */
    public ShipmentCostWrapper getCost(final ShipmentOptionWrapper shipmentOptionWrapper) {
        Method method = getMethod(getWrappedClass(),
            ShipmentCostWrapper.getWrappedClassStatic(),
            ShipmentOptionWrapper.getWrappedClassStatic());
        Object cost = invokeInstanceMethodWithReturnValue(method, shipmentOptionWrapper.getWrappedInstance());

        if (!cost.getClass().equals(ShipmentCostWrapper.getWrappedClassStatic())) {
            fail(String.format(
                "Expected %s's (%s) getCost(ShipmentOption) method to return a BigDecimal, but returned a %s: %s",
                getWrappedInstance().getClass().getName(),
                getWrappedInstance().toString(),
                cost.getClass().getName(),
                cost.toString())
            );
        }

        return new ShipmentCostWrapper(cost);
    }

    /**
     * Returns the appropriate {@code CostStrategyWrapper} for the given {@code CostStrategy} instance
     * from the base project, if possible.
     *
     * If the instance isn't recognized as one of the known cost strategy types, will
     * {@code fail()}.
     *
     * @param costStrategy The instance of {@code CostStrategy} from the base project to find a
     *                     wrapper for
     * @return The appropriate {@code CostStrategyWrapper} for the given cost strategy instance
     */
    public static CostStrategyWrapper getCostStrategyWrapperForCostStrategy(final Object costStrategy) {
        CostStrategyWrapper costStrategyWrapper = null;
        switch (costStrategy.getClass().getSimpleName()) {
            case "CarbonCostStrategy":
                costStrategyWrapper = new CarbonCostStrategyWrapper(costStrategy);
                break;
            case "MonetaryCostStrategy":
                costStrategyWrapper = new MonetaryCostStrategyWrapper(costStrategy);
                break;
            default:
                fail(String.format("Unrecognized CostStrategy type '%s'", costStrategy.getClass().getSimpleName()));
                break;
        }

        return costStrategyWrapper;
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
}
