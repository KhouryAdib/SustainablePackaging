package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents a {@code PackagingDAO} from the base project, by wrapping a {@code PackagingDAO}
 * instance, and providing the appropriate methods.
 */
public class PackagingDAOWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "dao";
    private static final String WRAPPED_CLASS_NAME = "PackagingDAO";

    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
    /**
     * Instantiates a new {@code PackagingDAOWrapper} with a {@code PackagingDAO} instance.
     * @param wrappedInstance The {@code PackagingDAO} instance to wrap
     */
    public PackagingDAOWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Instantiates a new {@code PackagingDAOWrapper} from a {@code PackagingDatastoreWrapper}.
     *
     * @param datastoreWrapper The {@code DatastoreWrapper} to use
     */
    public PackagingDAOWrapper(final PackagingDatastoreWrapper datastoreWrapper) {
        this(invokeConstructor(getPackagingDatastoreConstructor(), datastoreWrapper.getWrappedInstance()));
    }

    /**
     * Instantiates a new {@code PackagingDAOWrapper}.
     */
    public PackagingDAOWrapper() {
        this(new PackagingDatastoreWrapper());
    }

    /**
     * Returns the list of available shipment options for a given item and fulfillment center,
     * as provided by the wrapped {@code PackagingDAO}.
     *
     * @param itemWrapper The item to look for shipment options for
     * @param fulfillmentCenterWrapper The fulfillment center to look for shipment options for
     * @return A list of the possible shipment options provided by the wrapped {@code PackagingDAO}
     */
    public List<ShipmentOptionWrapper> findShipmentOptions(
            final ItemWrapper itemWrapper, final FulfillmentCenterWrapper fulfillmentCenterWrapper) {

        List<ShipmentOptionWrapper> shipmentOptionWrappers = new ArrayList<>();

        Object shipmentOptions = invokeInstanceMethodWithReturnValue(
            getMethod(
                "findShipmentOptions",
                ItemWrapper.getWrappedClassStatic(),
                FulfillmentCenterWrapper.getWrappedClassStatic()),
            itemWrapper.getWrappedInstance(),
            fulfillmentCenterWrapper.getWrappedInstance()
        );
        if (! (shipmentOptions instanceof List<?>)) {
            fail("Expected PackagingDAO#findShipmentOptions to return List<> but found " +
                shipmentOptions.getClass().getSimpleName());
        }

        List<?> shipmentOptionsList = (List<?>) shipmentOptions;
        for (Object shipmentOption : shipmentOptionsList) {
            if (!ShipmentOptionWrapper.getWrappedClassStatic().isInstance(shipmentOption)) {
                fail(String.format(
                    "findShipmentOptions() returned a list; expected all entries to be %s, but encountered a %s: %s",
                    ShipmentOptionWrapper.getWrappedClassStatic().getSimpleName(),
                    shipmentOption.getClass().getSimpleName(),
                    shipmentOption.toString())
                );
            }
            shipmentOptionWrappers.add(new ShipmentOptionWrapper(shipmentOption));
        }

        return shipmentOptionWrappers;
    }

    @Override
    public Class<?> getWrappedClass() {
        return PackagingDAOWrapper.getWrappedClassStatic();
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

    private static Constructor<?> getPackagingDatastoreConstructor() {
        return getConstructor(WRAPPED_CLASS, PackagingDatastoreWrapper.getWrappedClassStatic());
    }
}
