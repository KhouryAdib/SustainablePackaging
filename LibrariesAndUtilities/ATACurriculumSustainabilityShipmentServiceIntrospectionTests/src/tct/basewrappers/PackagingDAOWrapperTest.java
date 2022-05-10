package tct.basewrappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PackagingDAOWrapperTest {
    private PackagingDAOWrapper packagingDAOWrapper;

    @BeforeEach
    private void setup() {
        packagingDAOWrapper = new PackagingDAOWrapper();
    }

    @Test
    void packagingDaoConstructor_isCallable() {
        // GIVEN - a PackagingDAO instance
        Object packagingDao = packagingDAOWrapper.getWrappedInstance();

        // WHEN + THEN - no exception
        new PackagingDAOWrapper(packagingDao);
    }

    @Test
    void packagingDatastoreConstructor_isCallable() {
        // GIVEN - PackagingDatastoreWrapper
        PackagingDatastoreWrapper packagingDatastoreWrapper = new PackagingDatastoreWrapper();

        // WHEN + THEN - no exception
        new PackagingDAOWrapper(packagingDatastoreWrapper);
    }

    @Test
    void noArgsConstructor_isCallable() {
        // GIVEN
        // WHEN + THEN - no exception
        new PackagingDAOWrapper();
    }

    @Test
    void findShipmentOptions_withItemAndFulfillmentCenter_returnsShipmentOptions() {
        // GIVEN
        // PackagingDAOWrapper
        // item
        ItemWrapper itemWrapper = getItemWrapper();
        // fulfillment center
        FulfillmentCenterWrapper fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD2");

        // WHEN - there is at least one ShipmentOptionWrapper returned
        List<ShipmentOptionWrapper> shipmentOptions =
            packagingDAOWrapper.findShipmentOptions(itemWrapper, fulfillmentCenterWrapper);
        assertFalse(shipmentOptions.isEmpty(), "Expected PackagingDAO to return at least one shipment option");
    }

    private ItemWrapper getItemWrapper() {
        return ItemWrapper.builder()
            .withAsin("234567890")
            .withDescription("A blissfully blithe (and small) item")
            .withLength(new BigDecimal(1))
            .withWidth(new BigDecimal(1))
            .withHeight(new BigDecimal(1))
            .build();
    }
}
