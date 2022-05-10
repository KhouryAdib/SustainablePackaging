package tct.basewrappers;

import tct.ProjectClassFactory;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import static com.amazon.ata.test.assertions.IntrospectionAssertions.assertImplementsInterface;

/**
 * Represents a {@code ShipmentCost} from the base project. This is a class participants
 * will create from scratch.
 */
public class ShipmentCostWrapper extends WrapperBase implements Comparable<ShipmentCostWrapper> {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "ShipmentCost";

    /**
     * Instantiates a new ShipmentCostWrapper with a ShipmentCost instance.
     *
     * @param wrappedInstance The ShipmentCost instance to wrap
     */
    public ShipmentCostWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Instantiates a ShipmentCostWrapper with a ShipmentOptionWrapper and cost.
     *
     * @param shipmentOptionWrapper The ShipmentOptionWrapper to use
     * @param cost The cost of the given ShipmentOptionWrapper
     */
    public ShipmentCostWrapper(final ShipmentOptionWrapper shipmentOptionWrapper, final BigDecimal cost) {
        this(WrapperBase.invokeConstructor(
            ShipmentCostWrapper.getShipmentOptionAndCostConstructor(),
            shipmentOptionWrapper.getWrappedInstance(),
            cost
            )
        );
    }

    /**
     * Returns this {@code ShipmentCost}'s {@code ShipmentOption}.
     *
     * @return ShipmentOptionWrapper of the wrapped instance's result
     */
    public ShipmentOptionWrapper getShipmentOption() {
        return new ShipmentOptionWrapper(
            invokeInstanceMethodWithReturnValue(getMethod("getShipmentOption"))
        );
    }

    /**
     * Returns this {@code ShipmentCost}'s cost.
     *
     * @return The cost in this {@code ShipmentCost}
     */
    public BigDecimal getCost() {
        return (BigDecimal) invokeInstanceMethodWithReturnValue(getMethod("getCost"));
    }

    @Override
    public int compareTo(final ShipmentCostWrapper other) {
        if (!(getWrappedInstance() instanceof Comparable)) {
            assertImplementsInterface(ShipmentCostWrapper.getWrappedClassStatic(), Comparable.class);
        }
        return ((Comparable) getWrappedInstance()).compareTo(other.getWrappedInstance());
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
        return ShipmentCostWrapper.getWrappedClassStatic();
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
     * @return The class wrapped by this wrapper class
     */
    public static Class<?> getWrappedClassStatic() {
        return ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
    }

    private static Constructor<?> getShipmentOptionAndCostConstructor() {
        return getConstructor(getWrappedClassStatic(), ShipmentOptionWrapper.getWrappedClassStatic(), BigDecimal.class);
    }
}
