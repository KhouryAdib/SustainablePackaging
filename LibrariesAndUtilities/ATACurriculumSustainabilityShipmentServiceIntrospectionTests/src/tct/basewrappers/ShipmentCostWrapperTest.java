package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;
import com.amazon.ata.types.ShipmentOptionFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShipmentCostWrapperTest {
    private ShipmentCostWrapper boxShipmentCostWrapperWithCostOfOne;
    private ShipmentCostWrapper boxShipmentCostWrapperWithCostOfTen;
    private ShipmentCostWrapper polyBagShipmentCostWrapperWithCostOfOne;
    private ShipmentCostWrapper polyBagShipmentCostWrapperWithCostOfTen;

    @BeforeEach
    private void setup() {
        initMocks(this);

        boxShipmentCostWrapperWithCostOfOne = new ShipmentCostWrapper(getBoxShipmentOptionWrapper(), BigDecimal.ONE);
        boxShipmentCostWrapperWithCostOfTen = new ShipmentCostWrapper(getBoxShipmentOptionWrapper(), BigDecimal.TEN);

        polyBagShipmentCostWrapperWithCostOfOne =
            new ShipmentCostWrapper(getPolyBagShipmentOptionWrapper(), BigDecimal.ONE);
        polyBagShipmentCostWrapperWithCostOfTen =
            new ShipmentCostWrapper(getPolyBagShipmentOptionWrapper(), BigDecimal.TEN);
    }

    @Test
    void shipmentCostConstructor_isCallable() {
        if (skipTests()) {
            return;
        }

        // GIVEN - ShipmentCost instance
        Object shipmentCost = boxShipmentCostWrapperWithCostOfOne.getWrappedInstance();

        // WHEN + THEN - no assert failure
        new ShipmentCostWrapper(shipmentCost);
    }

    @Test
    void shipmentOptionAndCostConstructor_isCallable() {
        if (skipTests()) {
            return;
        }

        // WHEN + THEN - no assert failure
        new ShipmentCostWrapper(getBoxShipmentOptionWrapper(), BigDecimal.ONE);
    }

    @Test
    void getShipmentOption_returnsCorrectShipmentOption() {
        if (skipTests()) {
            return;
        }

        // GIVEN - expected shipment option
        ShipmentOptionWrapper expectedShipmentOptionWrapper = getBoxShipmentOptionWrapper();

        // WHEN
        ShipmentOptionWrapper result = boxShipmentCostWrapperWithCostOfOne.getShipmentOption();

        // THEN - matches expected option
        assertEquals(
            expectedShipmentOptionWrapper,
            result,
            String.format(
                "Expected shipment option of %s to be %s but was %s",
                boxShipmentCostWrapperWithCostOfOne,
                expectedShipmentOptionWrapper.toString(),
                result.toString())
        );
    }

    @Test
    void getCost_returnsCorrectCost() {
        if (skipTests()) {
            return;
        }

        // GIVEN - expected cost
        BigDecimal expectedCost = BigDecimal.ONE;

        // WHEN
        BigDecimal result = boxShipmentCostWrapperWithCostOfOne.getCost();

        // THEN - matches expected option
        assertEquals(
            expectedCost,
            result,
            String.format(
                "Expected cost of %s to be %s but was %s",
                boxShipmentCostWrapperWithCostOfOne,
                expectedCost.toString(),
                result.toString())
        );
    }

    @Test
    void compareTo_onNull_throwsException() {
        if (skipTests()) {
            return;
        }

        // GIVEN valid shipment cost wrapper, null object to compare to
        // WHEN + THEN - throw NPE
        assertThrows(NullPointerException.class, () -> boxShipmentCostWrapperWithCostOfOne.compareTo(null));
    }

    @Test
    void compareTo_withDifferentShipmentOptionsButEqualCost_returnsZero() {
        if (skipTests()) {
            return;
        }

        // GIVEN - two shipment costs with different options but same cost
        ShipmentCostWrapper firstCost = boxShipmentCostWrapperWithCostOfOne;
        ShipmentCostWrapper secondCost = polyBagShipmentCostWrapperWithCostOfOne;

        // WHEN
        int result1 = firstCost.compareTo(secondCost);
        int result2 = secondCost.compareTo(firstCost);

        // THEN - both should be zero
        assertEquals(
            0,
            result1,
            String.format("Expected compareTo to return zero for %s, %s", firstCost.toString(), secondCost.toString())
        );
        assertEquals(
            0,
            result2,
            String.format("Expected compareTo to return zero for %s, %s", secondCost.toString(), firstCost.toString())
        );
    }

    @Test
    void compareTo_onLowerCostShipmentCost_returnsPositive() {
        if (skipTests()) {
            return;
        }

        // GIVEN - two shipment costs with same options but different cost
        ShipmentCostWrapper lowerCost = boxShipmentCostWrapperWithCostOfOne;
        ShipmentCostWrapper higherCost = boxShipmentCostWrapperWithCostOfTen;

        // WHEN
        int result = higherCost.compareTo(lowerCost);

        // THEN - higherCost.compareTo(lowerCost) > 0
        assertTrue(result > 0, String.format("Expected higherCost.compareTo(lowerCost) > 0, but was %s", result));
    }

    @Test
    void compareTo_onHigherCostShipmentCost_returnsNegative() {
        if (skipTests()) {
            return;
        }

        // GIVEN - two shipment costs with same options but different cost
        ShipmentCostWrapper lowerCost = polyBagShipmentCostWrapperWithCostOfOne;
        ShipmentCostWrapper higherCost = polyBagShipmentCostWrapperWithCostOfTen;

        // WHEN
        int result = lowerCost.compareTo(higherCost);

        // THEN - lowerCost.compareTo(higherCost) < 0
        assertTrue(result < 0, String.format("Expected lowerCost.compareTo(higherCost) > 0, but was %s", result));
    }

    private ShipmentOptionWrapper getBoxShipmentOptionWrapper() {
        return ShipmentOptionFactory.shipmentOptionWrapperForPackaging(PackagingFactory.boxWrapperOfAnyDimensions());
    }

    private ShipmentOptionWrapper getPolyBagShipmentOptionWrapper() {
        return ShipmentOptionFactory.shipmentOptionWrapperForPackaging(PackagingFactory.polyBagWrapperOfAnyVolume());
    }

    private boolean skipTests() {
        return !ShipmentCostWrapper.testsEnabled();
    }
}
