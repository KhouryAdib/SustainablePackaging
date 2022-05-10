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
public class CarbonCostStrategyWrapperTest {
    private CarbonCostStrategyWrapper carbonCostStrategyWrapper;

    @BeforeEach
    private void setup() {
        if (skipTests()) {
            return;
        }
        carbonCostStrategyWrapper = new CarbonCostStrategyWrapper();
    }

    @Test
    void carbonCostStrategyConstructor_withCarbonCostStrategy_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - carbon cost strategy instance
        Object carbonCostStrategy = carbonCostStrategyWrapper.getWrappedInstance();

        // WHEN + THEN - no assert failure
        new CarbonCostStrategyWrapper(carbonCostStrategy);
    }

    @Test
    void noArgsConstructor_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN + WHEN + THEN - no assert failure
        new CarbonCostStrategyWrapper();
    }

    @Test
    void getName_returnsCorrectName() {
        if (skipTests()) {
            return;
        }

        // GIVEN - expected strategy name
        String expectedName = "carbon-cost";

        // WHEN
        String result = carbonCostStrategyWrapper.getName();

        // THEN - should equal expected
        assertEquals(expectedName, result, "Unexpected result from CarbonCostStrategy#getName(): " + result);
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
        ShipmentCostWrapper shipmentCostWrapper = carbonCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedCarbonCost = CarbonCostStrategyWrapper.computeCarbonCost(boxWrapper);
        assertClose(
            expectedCarbonCost,
            result,
            String.format(
                "Expected getCost(ShipmentOption) for box %s to return %s carbon units, but received %s",
                boxWrapper.getWrappedInstance().toString(),
                expectedCarbonCost,
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
        ShipmentCostWrapper shipmentCostWrapper = carbonCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedCarbonCost = CarbonCostStrategyWrapper.computeCarbonCost(polyBagWrapper);
        assertClose(
            expectedCarbonCost,
            result,
            String.format(
                "Expected getCost(ShipmentOption) for polyBag %s to return %s carbon units, but received %s",
                polyBagWrapper.getWrappedInstance().toString(),
                expectedCarbonCost,
                result)
        );
    }

    private boolean skipTests() {
        return !CarbonCostStrategyWrapper.testsEnabled();
    }
}
