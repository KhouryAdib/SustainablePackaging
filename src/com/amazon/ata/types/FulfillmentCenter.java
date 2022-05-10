package com.amazon.ata.types;

import java.util.Objects;

/**
 * Represents an Amazon fulfillment center.
 *
 * Fulfillment centers receive orders, pack the items, and ship them to customers.
 */
public class FulfillmentCenter {

    /**
     * The unique identifier code for a fulfillment center - 4-chars where first 3 are airport code for nearest city.
     */
    private String fcCode;

    /**
     * Instantiates a new FulfillmentCenter object.
     * @param fcCode - the unique identifier for the new fulfillment center
     */
    public FulfillmentCenter(String fcCode) {
        this.fcCode = fcCode;
    }

    public String getFcCode() {
        return fcCode;
    }

    @Override
    public String toString() {
        return "FulfillmentCenter{" +
            "fcCode='" + fcCode + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        // Can't be equal to null
        if (o == null) {
            return false;
        }

        // Referentially equal
        if (this == o) {
            return true;
        }

        // Check if it's a different type
        if (getClass() != o.getClass()) {
            return false;
        }

        FulfillmentCenter that = (FulfillmentCenter) o;
        return Objects.equals(getFcCode(), that.getFcCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFcCode());
    }
}
