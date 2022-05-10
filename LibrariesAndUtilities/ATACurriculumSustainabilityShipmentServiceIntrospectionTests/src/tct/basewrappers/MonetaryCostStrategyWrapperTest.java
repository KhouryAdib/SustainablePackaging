package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.amazon.ata.test.assertions.AtaAssertions.assertClose;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Since the wrapped class won't exist until participants create it,
 * short-circuit these tests (which run during base package build)
 * so they don't fail because the class is missing.
 */
public class MonetaryCostStrategyWrapperTest {
    private MonetaryCostStrategyWrapper monetaryCostStrategyWrapper;

    @BeforeEach
    private void setup() {
        if (skipTests()) {
            return;
        }
        monetaryCostStrategyWrapper = new MonetaryCostStrategyWrapper();
    }

    @Test
    void monetaryCostStrategyConstructor_withMonetaryCostStrategy_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - monetary cost strategy instance
        Object monetaryCostStrategy = monetaryCostStrategyWrapper.getWrappedInstance();

        // WHEN + THEN - no assert failure
        new MonetaryCostStrategyWrapper(monetaryCostStrategy);
    }

    @Test
    void noArgsConstructor_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN + WHEN + THEN - no assert failure
        new MonetaryCostStrategyWrapper();
    }

    @Test
    void getName_returnsCorrectName() {
        if (skipTests()) {
            return;
        }

        // GIVEN - expected strategy name
        String expectedName = "monetary-cost";

        // WHEN
        String result = monetaryCostStrategyWrapper.getName();

        // THEN - should equal expected
        assertEquals(expectedName, result, "Unexpected result from MonetaryCostStrategy#getName(): " + result);
    }

    @Test
    void getCost_withBoxShipmentOptionWrapper_returnsAccurateCost() {
        if (skipTests()) {
            return;
        }

        // GIVEN - shipment option
        BigDecimal twenty = new BigDecimal("20");
        ItemWrapper itemWrapper = ItemWrapper.builder()
            .withLength(twenty)
            .withWidth(twenty)
            .withHeight(twenty)
            .build();
        FulfillmentCenterWrapper fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD2");
        BoxWrapper boxWrapper = PackagingFactory.boxWrapperOfAnyDimensions();
        ShipmentOptionWrapper shipmentOptionWrapper = ShipmentOptionWrapper.builder()
            .withPackaging(boxWrapper)
            .withItem(itemWrapper)
            .withFulfillmentCenter(fulfillmentCenterWrapper)
            .build();

        // WHEN
        ShipmentCostWrapper shipmentCostWrapper = monetaryCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedMonetaryCost = MonetaryCostStrategyWrapper.computeMonetaryCost(boxWrapper);
        assertClose(
            expectedMonetaryCost,
            result,
            String.format(
                "Expected getCost(ShipmentOption) for box %s to return %s monetarys, but received %s",
                boxWrapper.getWrappedInstance().toString(),
                expectedMonetaryCost,
                result)
        );
    }

    @Test
    void getCost_withPolyBagShipmentOptionWrapper_returnsAccurateCost() {
        if (skipTests()) {
            return;
        }

        // GIVEN - shipment option
        BigDecimal twenty = new BigDecimal("20");
        ItemWrapper itemWrapper = ItemWrapper.builder()
            .withLength(twenty)
            .withWidth(twenty)
            .withHeight(twenty)
            .build();
        FulfillmentCenterWrapper fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD2");
        PolyBagWrapper polyBagWrapper = PackagingFactory.polyBagWrapperOfAnyVolume();
        ShipmentOptionWrapper shipmentOptionWrapper = ShipmentOptionWrapper.builder()
            .withPackaging(polyBagWrapper)
            .withItem(itemWrapper)
            .withFulfillmentCenter(fulfillmentCenterWrapper)
            .build();

        // WHEN
        ShipmentCostWrapper shipmentCostWrapper = monetaryCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedMonetaryCost = MonetaryCostStrategyWrapper.computeMonetaryCost(polyBagWrapper);
        assertClose(
            expectedMonetaryCost,
            result,
            String.format(
                "Expected getCost(ShipmentOption) for polyBag %s to return %s monetarys, but received %s",
                polyBagWrapper.getWrappedInstance().toString(),
                expectedMonetaryCost,
                result)
        );
    }

    private boolean skipTests() {
        return !MonetaryCostStrategyWrapper.testsEnabled();
    }
}
