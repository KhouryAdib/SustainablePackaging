package com.amazon.ata.types;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentOptionTest {

    private Packaging packaging = new Box(Material.CORRUGATE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
    private Item item = Item.builder()
        .withAsin("asin")
        .withDescription("description")
        .withHeight(BigDecimal.ONE)
        .withLength(BigDecimal.ONE)
        .withWidth(BigDecimal.ONE)
        .build();
    private FulfillmentCenter fulfillmentCenter = new FulfillmentCenter("fcCode");
    private ShipmentOption shipmentOption = ShipmentOption.builder()
        .withPackaging(packaging)
        .withItem(item)
        .withFulfillmentCenter(fulfillmentCenter)
        .build();

    private Item otherItem = Item.builder()
        .withAsin("otherAsin")
        .withDescription("otherDescription")
        .withLength(BigDecimal.TEN)
        .withWidth(BigDecimal.TEN)
        .withHeight(BigDecimal.TEN)
        .build();
    private FulfillmentCenter otherFulfillmentCenter = new FulfillmentCenter("otherFcCode");
    private Packaging otherPackaging = new Box(Material.CORRUGATE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

    @Test
    public void equals_sameObject_returnsTrue() {
        // Given
        Object other = shipmentOption;

        // WHEN
        boolean isEqual = shipmentOption.equals(other);

        // THEN
        assertTrue(isEqual, "An object is equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Object other = null;

        // WHEN
        boolean isEqual = shipmentOption.equals(other);

        // THEN
        assertFalse(isEqual, "A ShipmentOption is not equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Object other = "String type!";

        // WHEN
        boolean isEqual = shipmentOption.equals(other);

        // THEN
        assertFalse(isEqual, "An ShipmentOption is not equal to an object of a different type.");
    }

    @Test
    public void equals_sameItemSameFCSamePackaging_returnsTrue() {
        // GIVEN
        Object other = ShipmentOption.builder()
            .withItem(item)
            .withFulfillmentCenter(fulfillmentCenter)
            .withPackaging(packaging)
            .build();

        // WHEN
        boolean isEqual = shipmentOption.equals(other);

        // THEN
        assertTrue(isEqual, "ShipmentOptions with the same item, packaging, and fulfillment center are equal.");
    }

    @Test
    public void equals_differentItem_returnsFalse() {
        // GIVEN
        Object other = ShipmentOption.builder()
            .withItem(otherItem)
            .withFulfillmentCenter(fulfillmentCenter)
            .withPackaging(packaging)
            .build();

        // WHEN
        boolean isEqual = shipmentOption.equals(other);

        // THEN
        assertFalse(isEqual, "ShipmentOptions with different items are not equal.");
    }

    @Test
    public void equals_differentFCs_returnsFalse() {
        // GIVEN
        Object other = ShipmentOption.builder()
            .withItem(item)
            .withFulfillmentCenter(otherFulfillmentCenter)
            .withPackaging(packaging)
            .build();

        // WHEN
        boolean isEqual = shipmentOption.equals(other);

        // THEN
        assertFalse(isEqual, "ShipmentOptions with different fulfillment centers are not equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        int shipmentOptionHashCode = shipmentOption.hashCode();
        Object other = ShipmentOption.builder()
            .withPackaging(packaging)
            .withFulfillmentCenter(fulfillmentCenter)
            .withItem(item)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertEquals(shipmentOptionHashCode, otherHashCode, "Equal objects should have equal hashCodes");
    }

    @Test
    public void hashCode_differentItems_differentHashCode() {
        // GIVEN
        int shipmentOptionHashCode = shipmentOption.hashCode();
        Object other = ShipmentOption.builder()
            .withPackaging(packaging)
            .withFulfillmentCenter(fulfillmentCenter)
            .withItem(otherItem)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(shipmentOptionHashCode, otherHashCode, "Different items should have different hashCodes");
    }

    @Test
    public void hashCode_differentFCs_differentHashCode() {
        // GIVEN
        int shipmentOptionHashCode = shipmentOption.hashCode();
        Object other = ShipmentOption.builder()
            .withPackaging(packaging)
            .withFulfillmentCenter(otherFulfillmentCenter)
            .withItem(item)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(shipmentOptionHashCode, otherHashCode, "Different FCs should have different hashCodes");
    }

    @Test
    public void hashCode_differentPackaging_differentHashCode() {
        // GIVEN
        int shipmentOptionHashCode = shipmentOption.hashCode();
        Object other = ShipmentOption.builder()
            .withPackaging(otherPackaging)
            .withFulfillmentCenter(fulfillmentCenter)
            .withItem(item)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(shipmentOptionHashCode, otherHashCode, "Different packaging should have different hashCodes");
    }
}
