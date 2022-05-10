package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipmentOptionWrapperTest {
    private ItemWrapper itemWrapper;
    private PackagingWrapper packagingWrapper;
    private FulfillmentCenterWrapper fulfillmentCenterWrapper;
    private ShipmentOptionWrapper shipmentOptionWrapper;

    @BeforeEach
    private void setup() {
        itemWrapper = ItemWrapper.builder()
            .withAsin("012345678")
            .withDescription("A woefully wistful item")
            .withLength(new BigDecimal(1))
            .withWidth(new BigDecimal(2))
            .withHeight(new BigDecimal(3))
            .build();

        packagingWrapper = PackagingFactory.anyPackagingWrapper();
        fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD2");
        shipmentOptionWrapper = ShipmentOptionWrapper.builder()
            .withItem(itemWrapper)
            .withPackaging(packagingWrapper)
            .withFulfillmentCenter(fulfillmentCenterWrapper)
            .build();
    }

    @Test
    void getItem_returnsCorrectItem() {
        // GIVEN - shipment option with known item
        // WHEN
        ItemWrapper result = shipmentOptionWrapper.getItem();

        // THEN
        assertEquals(itemWrapper, result, "Expected ShipmentOption's getItem to return correct ItemWrapper");
    }

    @Test
    void getPackaging_returnsCorrectPackaging() {
        // GIVEN - shipment option with known packaging
        // WHEN
        PackagingWrapper result = shipmentOptionWrapper.getPackaging();

        // THEN
        assertEquals(
            packagingWrapper,
            result,
            "Expected ShipmentOption's getPackaging to return correct PackagingWrapper"
        );
    }

    @Test
    void getFulfillmentCenter_returnsCorrectFulfillmentCenter() {
        // GIVEN - shipment option with known fulfillment center
        // WHEN
        FulfillmentCenterWrapper result = shipmentOptionWrapper.getFulfillmentCenter();

        // THEN
        assertEquals(
            fulfillmentCenterWrapper,
            result,
            "Expected ShipmentOption's getFulfillmentCenter to return correct FulfillmentCenterWrapper"
        );
    }
}
