package com.amazon.ata.types;

import java.util.Objects;

/**
 * Represents a pairing between a packaging option and a fulfillment center that supports that packaging option.
 */
public class FcPackagingOption {

    /**
     * The fulfillment center we are providing packaging information about.
     */
    private FulfillmentCenter fulfillmentCenter;

    /**
     * A packaging that is available at the fulfillment center.
     */
    private Packaging packaging;


    /**
     * Instantiates a new FcPackagingOption object.
     * @param fulfillmentCenter - the FC where the packaging Option is available
     * @param packaging - the packaging option available at the provided FC
     */
    public FcPackagingOption(FulfillmentCenter fulfillmentCenter, Packaging packaging) {
        this.fulfillmentCenter = fulfillmentCenter;
        this.packaging = packaging;
    }

    public FulfillmentCenter getFulfillmentCenter() {
        return fulfillmentCenter;
    }

    public Packaging getPackaging() {
        return packaging;
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

        FcPackagingOption that = (FcPackagingOption) o;
        return Objects.equals(getFulfillmentCenter(), that.getFulfillmentCenter()) &&
            Objects.equals(getPackaging(), that.getPackaging());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFulfillmentCenter().hashCode(), getPackaging().hashCode());
    }
}
