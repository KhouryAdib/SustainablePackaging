package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipmentCostTest {

    private static final BigDecimal COST = BigDecimal.TEN;
    private static final Packaging PACKAGING = new Box(Material.CORRUGATE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);
    private static FulfillmentCenter FC = new FulfillmentCenter("IND123");
    private ShipmentOption SHIPMENT_OPTION = ShipmentOption.builder()
        .withFulfillmentCenter(FC)
        .withPackaging(PACKAGING)
        .build();

    private ShipmentCost shipmentCost;

    @BeforeEach
    public void setup() {
        shipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Given
        Object other = shipmentCost;

        // WHEN
        boolean isEqual = shipmentCost.equals(other);

        // THEN
        assertTrue(isEqual, "An object is equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Object other = null;

        // WHEN
        boolean isEqual = shipmentCost.equals(other);

        // THEN
        assertFalse(isEqual, "A non-null ShipmentCost is not equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Object other = "String type!";

        // WHEN
        boolean isEqual = shipmentCost.equals(other);

        // THEN
        assertFalse(isEqual, "A ShipmentCost is not equal to an object of a different type.");
    }

    @Test
    public void equals_differentCost_returnsFalse() {
        // GIVEN
        Object other = new ShipmentCost(SHIPMENT_OPTION, COST.add(BigDecimal.ONE));

        // WHEN
        boolean isEqual = shipmentCost.equals(other);

        // THEN
        assertFalse(isEqual, "ShipmentCosts with different costs are not equal.");
    }

    @Test
    public void equals_sameCost_returnsTrue() {
        // GIVEN
        Object other = new ShipmentCost(SHIPMENT_OPTION, COST);

        // WHEN
        boolean isEqual = shipmentCost.equals(other);

        // THEN
        assertTrue(isEqual, "ShipmentCosts with the same costs are equal.");
    }

    @Test
    public void hashCode_equalObjects_sameHashes() {
        // GIVEN
        ShipmentCost shipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);
        int hashCode = shipmentCost.hashCode();
        ShipmentCost otherShipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);

        // WHEN
        int otherHashCode = otherShipmentCost.hashCode();

        // THEN
        assertEquals(hashCode, otherHashCode, "Objects that are equal have the same hash codes.");
    }

    @Test
    public void compareTo_equalCosts_isZero() {
        // GIVEN
        ShipmentCost shipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);
        ShipmentCost otherShipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);

        // WHEN
        int result = shipmentCost.compareTo(otherShipmentCost);

        // THEN
        assertEquals(0, result, "Comparison on equal ShipmentCosts should be zero");
    }

    @Test
    public void compareTo_greaterCost_isNegative() {
        // GIVEN
        ShipmentCost shipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);
        ShipmentCost greaterShipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST.add(BigDecimal.ONE));

        // WHEN
        int result = shipmentCost.compareTo(greaterShipmentCost);

        // THEN
        // Some `compareTo()` methods return -1, but the contract is just for anything negative
        assertTrue(result < 0, "Comparison to greater ShipmentCost should be negative");

    }

    @Test
    public void compareTo_lesserCost_isPositive() {
        // GIVEN
        ShipmentCost shipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST);
        ShipmentCost lesserShipmentCost = new ShipmentCost(SHIPMENT_OPTION, COST.subtract(BigDecimal.ONE));

        // WHEN
        int result = shipmentCost.compareTo(lesserShipmentCost);

        // THEN
        // Some `compareTo()` methods return 1, but the contract is just for anything positive
        assertTrue(result > 0, "Comparison to lesser ShipmentCost should be positive");
    }
}
