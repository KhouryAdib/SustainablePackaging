package tct;

import com.amazon.ata.test.reflect.MethodInvoker;
import com.amazon.ata.test.reflect.MethodQuery;

import tct.basewrappers.BoxWrapper;
import tct.basewrappers.CarbonCostStrategyWrapper;
import tct.basewrappers.MonetaryCostStrategyWrapper;
import tct.basewrappers.PolyBagWrapper;
import tct.basewrappers.ShipmentCostWrapper;
import tct.basewrappers.ShipmentOptionWrapper;
import tct.basewrappers.WeightedCostStrategyWrapper;
import com.amazon.ata.types.PackagingFactory;
import com.amazon.ata.types.ShipmentOptionFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static com.amazon.ata.test.assertions.AtaAssertions.assertClose;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("MT05_WEIGHTED")
public class MT5WeightedIntrospectionTests {
    private MonetaryCostStrategyWrapper monetaryCostStrategyWrapper = new MonetaryCostStrategyWrapper();
    private CarbonCostStrategyWrapper carbonCostStrategyWrapper = new CarbonCostStrategyWrapper();

    @Test
    void mt5_weightedCostStrategy_getCostOfBox_resultsInCorrectWeightedCost() {
        // GIVEN - valid Box
        BoxWrapper boxWrapper = PackagingFactory.boxWrapperOfAnyDimensions();
        assertNotNull(boxWrapper, "Could not find any Boxes in PackagingDatastore");
        // shipment option wrapper using that Box with FC IAD2
        ShipmentOptionWrapper shipmentOptionWrapper =
            ShipmentOptionFactory.shipmentOptionWrapperForPackaging(boxWrapper);

        WeightedCostStrategyWrapper weightedCostStrategyWrapper = new WeightedCostStrategyWrapper(
            monetaryCostStrategyWrapper,
            carbonCostStrategyWrapper
        );

        // WHEN
        ShipmentCostWrapper shipmentCostWrapper = weightedCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate, should be cheapest for IAD2
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedWeightedCost = WeightedCostStrategyWrapper.computeWeightedCost(boxWrapper);
        assertClose(
            expectedWeightedCost,
            result,
            String.format(
                "Expected weighted cost of %s to be %s, but was %s",
                boxWrapper.toString(),
                expectedWeightedCost,
                result)
        );
    }

    @Test
    void mt5_weightedCostStrategy_getCostOfPolyBag_resultsInCorrectWeightedCost() {
        // GIVEN - valid Box
        PolyBagWrapper boxWrapper = PackagingFactory.polyBagWrapperOfAnyVolume();
        assertNotNull(boxWrapper, "Could not find any Boxes in PackagingDatastore");
        // shipment option wrapper using that Box with FC IAD2
        ShipmentOptionWrapper shipmentOptionWrapper =
            ShipmentOptionFactory.shipmentOptionWrapperForPackaging(boxWrapper);

        WeightedCostStrategyWrapper weightedCostStrategyWrapper = new WeightedCostStrategyWrapper(
            monetaryCostStrategyWrapper,
            carbonCostStrategyWrapper
        );

        // WHEN
        ShipmentCostWrapper shipmentCostWrapper = weightedCostStrategyWrapper.getCost(shipmentOptionWrapper);

        // THEN - cost is accurate, should be cheapest for IAD2
        BigDecimal result = shipmentCostWrapper.getCost();
        BigDecimal expectedWeightedCost = WeightedCostStrategyWrapper.computeWeightedCost(boxWrapper);
        assertClose(
            expectedWeightedCost,
            result,
            String.format(
                "Expected weighted cost of %s to be %s, but was %s",
                boxWrapper.toString(),
                expectedWeightedCost,
                result)
        );
    }

    @Test
    void mt5_appClass_createsShipmentOptionWithWeightedCostStrategy() {
        // GIVEN - the shipment option created from the app class
        Class<?> appClass = ProjectClassFactory.findClass("App");
        Method getShipmentService = MethodQuery.inType(appClass)
            .withExactName("getShipmentService")
            .findMethodOrFail();

        // WHEN
        Object shipmentService = MethodInvoker.invokeStaticMethodWithReturnValue(getShipmentService);

        // THEN - shipmentService's cost strategy is a WeightedCostStrategy
        try {
            Field costStrategyField = shipmentService.getClass().getDeclaredField("costStrategy");
            costStrategyField.setAccessible(true);
            assertEquals(WeightedCostStrategyWrapper.getWrappedClassStatic(),
                costStrategyField.get(shipmentService).getClass(),
                "Expected the ShipmentService to use a WeightedCostStrategy");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            // will print to their RDE logs so we can debug if needed
            e.printStackTrace();
            fail("Expected a 'costStrategy' field in the ShipmentService class. Has it been modified?");
        }
    }
}
