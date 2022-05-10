package com.amazon.ata.activity;

import java.math.BigDecimal;
import java.util.Objects;

public class PrepareShipmentRequest {

    /**
    * Represents a request sent to app for packaging information.
    */

    /**
    * The item's unique identifier - asing - (Amazon Standard Identification Number - 10 alphanumeric characters).
    */
    private String asin;
    /**
    * A description of the item.
    */
    private String description;

    /**
    * The length of the item, in centimeters.
    */
    private BigDecimal length;

    /**
    * The shortest dimension of the item, in centimeters.
    */
    private BigDecimal width;

    /**
    * The longest dimension of the item, in centimeters.
    */
    private BigDecimal height;

    /**
    * The unique identifier code for a fulfillment center 4-chars where first 3 are airport code for nearest city.
    */
    private String fcCode;

    public PrepareShipmentRequest() {
        this.asin   = "0000000000";
        this.description = "Frank Test Default Item - should not ever be needed - only used for testing";
        this.length = new BigDecimal(0);
        this.width  = new BigDecimal(0);
        this.height = new BigDecimal(0);
        this.fcCode = "IND1";
    }


    public PrepareShipmentRequest(String asin, String description, BigDecimal length, BigDecimal width,
                                  BigDecimal height, String fcCode) {
        this.asin = asin;
        this.description = description;
        this.length = length;
        this.width = width;
        this.height = height;
        this.fcCode = fcCode;
    }

    public PrepareShipmentRequest(Builder builder) {
        this.asin = builder.asin;
        this.description = builder.description;
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
        this.fcCode = builder.fcCode;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getFcCode() {
        return fcCode;
    }

    public void setFcCode(String fcCode) {
        this.fcCode = fcCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrepareShipmentRequest that = (PrepareShipmentRequest) o;
        return getAsin().equals(that.getAsin()) && getDescription().equals(that.getDescription()) &&
                                                getLength().equals(that.getLength()) &&
                                                getWidth().equals(that.getWidth()) &&
                                                getHeight().equals(that.getHeight()) &&
                                                getFcCode().equals(that.getFcCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAsin(), getDescription(), getLength(), getWidth(), getHeight(), getFcCode());
    }

    @Override
    public String toString() {
        return "PrepareShipmentRequest{" +
                "asin='" + asin + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", fcCode='" + fcCode + '\'' +
                '}';
    }

    /**
     * Returns a new PrepareShipmentActivity.Builder object for constructing an Item.
     *
     * @return new builder ready for constructing a PrepeareShimentActivity
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
    * {@code} Builder builder static inner class.
    */
    public static final class Builder {
        private String asin;
        private String description;
        private BigDecimal length;
        private BigDecimal width;
        private BigDecimal height;
        private String fcCode;

        private Builder() {
        }

        /**
        * Sets the {@code asin} and returns a reference to this Builder so that the methods can be chained together.
        *
        * @param asinToUse the {@code asin} to set
        * @return a reference to this Builder
        */
        public Builder withItemAsin(String asinToUse) {
            this.asin = asinToUse;
            return this;
        }

        /**
        * Sets the {@code description} and returns a reference to this Builder so that the methods can be chained
        * together.
        *
        * @param descriptionToUse the {@code description} to set
        * @return a reference to this Builder
        */
        public Builder withItemDescription(String descriptionToUse) {
            this.description = descriptionToUse;
            return this;
        }

        /**
        * Sets the {@code length} and returns a reference to this Builder so that the methods can be chained together.
        *
        * @param lengthToUse the {@code length} to set
        * @return a reference to this Builder
        */
        public Builder withItemLength(String lengthToUse) {
            this.length = new BigDecimal(lengthToUse);
            return this;
        }

        /**
        * Sets the {@code width} and returns a reference to this Builder so that the methods can be chained together.
        *
        * @param widthToUse the {@code width} to set
        * @return a reference to this Builder
        */
        public Builder withItemWidth(String widthToUse) {
            this.width = new BigDecimal(widthToUse);
            return this;
        }

        /**
        * Sets the {@code height} and returns a reference to this Builder so that the methods can be chained together.
        *
        * @param heightToUse the {@code height} to set
        * @return a reference to this Builder
        */
        public Builder withItemHeight(String heightToUse) {
            this.height = new BigDecimal(heightToUse);
            return this;
        }
        /**
        * Sets the {@code height} and returns a reference to this Builder so that the methods can be chained together.
        *
        * @param fcCode the {@code fcCode} to set
        * @return a reference to this Builder
        */
        public Builder withFcCode(String fcCode) {
            this.fcCode = fcCode;
            return this;
        }

        /**
        * Returns a {@code Item} built from the parameters previously set.
        *
        * @return a {@code Item} built with parameters of this {@code Item.Builder}
        */
        public PrepareShipmentRequest build() {
        return new PrepareShipmentRequest(this);
        }
    }

}
