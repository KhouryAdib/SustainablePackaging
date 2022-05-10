package com.amazon.ata.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FulfillmentCenterTest {

    private String fcCode = "1";
    private FulfillmentCenter fc = new FulfillmentCenter(fcCode);

    @Test
    public void equals_sameObject_returnsTrue() {
        // Given
        Object other = fc;

        // WHEN
        boolean isEqual = fc.equals(other);

        // THEN
        assertTrue(isEqual, "An object is equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Object other = null;

        // WHEN
        boolean isEqual = fc.equals(other);

        // THEN
        assertFalse(isEqual, "A FulfillmentCenter is not equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Object other = "String type!";

        // WHEN
        boolean isEqual = fc.equals(other);

        // THEN
        assertFalse(isEqual, "An Fulfillment Center is not equal to an object of a different type.");
    }

    @Test
    public void equals_differentFcCode_returnsFalse() {
        // GIVEN
        Object other = new FulfillmentCenter(fcCode + "different code!");

        // WHEN
        boolean isEqual = fc.equals(other);

        // THEN
        assertFalse(isEqual, "Fulfillment centers with different fcCodes are not equal.");
    }

    @Test
    public void hashCode_nonEqualObjects_differentHashes() {
        // GIVEN
        int fcHashCode = fc.hashCode();
        Object other = new FulfillmentCenter(fcCode + "different code!");

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(fcHashCode, otherHashCode, "Objects that are not equal have different hash codes.");
    }

}
