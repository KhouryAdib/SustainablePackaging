package tct;

import tct.basewrappers.BoxWrapper;
import tct.basewrappers.CarbonCostStrategyWrapper;
import tct.basewrappers.PolyBagWrapper;
import tct.basewrappers.ShipmentCostWrapper;
import tct.basewrappers.ShipmentOptionWrapper;
import com.amazon.ata.types.PackagingFactory;
import com.amazon.ata.types.ShipmentOptionFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.amazon.ata.test.assertions.AtaAssertions.assertClose;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("MT05_CARBON")
public class MT5CarbonIntrospectionTests {
    @Test
    void mt5_carbonCostStrategy_getCostOfBox_resultsInCorrectCarbonCost() {
        // GIVEN - valid Box
        BoxWrapper boxWrapper = PackagingFactory.boxWrapperOfAnyDimensions();
        assertNotNull(boxWrapper, "Could not find any Boxes in PackagingDatastore");
        // shipment option wrapper using that Box
        ShipmentOptionWrapper shipmentOptionWrapper =
            ShipmentOptionFactory.shipmentOptionWrapperForPackaging(boxWrapper);
        // CarbonCostStrategyWrapper
        CarbonCostStrategyWrapper carbonCostStrategyWrapper = new CarbonCostStrategyWrapper();

        // WHEN
        ShipmentCostWrapper shipmentCostWrapper = carbonCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedCarbonCost = CarbonCostStrategyWrapper.computeCarbonCost(boxWrapper);
        assertClose(
            expectedCarbonCost,
            result,
            String.format(
                "Expected carbon cost of %s to be %s, but was %s",
                boxWrapper.toString(),
                expectedCarbonCost,
                result)
        );
    }

    @Test
    void mt5_carbonCostStrategy_getCostOfPolyBag_resultsInCorrectCarbonCost() {
        // GIVEN - valid PolyBag
        PolyBagWrapper polyBagWrapper = PackagingFactory.polyBagWrapperOfAnyVolume();
        assertNotNull(polyBagWrapper, "Could not find any PolyBags in PackagingDatastore");
        // shipment option wrapper using that PolyBag
        ShipmentOptionWrapper shipmentOptionWrapper =
            ShipmentOptionFactory.shipmentOptionWrapperForPackaging(polyBagWrapper);
        // CarbonCostStrategyWrapper
        CarbonCostStrategyWrapper carbonCostStrategyWrapper = new CarbonCostStrategyWrapper();

        // WHEN
        ShipmentCostWrapper shipmentCostWrapper = carbonCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedCarbonCost = CarbonCostStrategyWrapper.computeCarbonCost(polyBagWrapper);
        assertClose(
            expectedCarbonCost,
            result,
            String.format(
                "Expected carbon cost of %s to be %s, but was %s",
                polyBagWrapper.toString(),
                expectedCarbonCost,
                result)
        );
    }
}
