package com.amazon.ata.types;

import java.math.BigDecimal;

/**
 * Represents any object that can be shipped to a customer.
 */
public class Item {

    /**
     * The item's unique identifier - Amazon Standard Identification Number - 10 alphanumeric characters.
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

    private Item(Builder builder) {
        asin = builder.asin;
        description = builder.description;
        length = builder.length;
        width = builder.width;
        height = builder.height;
    }

    @Override
    public String toString() {
        return "Item{" +
            "asin='" + asin + '\'' +
            ", description='" + description + '\'' +
            ", length=" + length +
            ", width=" + width +
            ", height=" + height +
            '}';
    }

    /**
     * Returns a new Item.Builder object for constructing an Item.
     *
     * @return new builder ready for constructing an Item
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getAsin() {
        return asin;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    /**
     * {@code Item} builder static inner class.
     */
    public static final class Builder {
        private String asin;
        private String description;
        private BigDecimal length;
        private BigDecimal width;
        private BigDecimal height;

        private Builder() {
        }

        /**
         * Sets the {@code asin} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param asinToUse the {@code asin} to set
         * @return a reference to this Builder
         */
        public Builder withAsin(String asinToUse) {
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
        public Builder withDescription(String descriptionToUse) {
            this.description = descriptionToUse;
            return this;
        }

        /**
         * Sets the {@code length} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param lengthToUse the {@code length} to set
         * @return a reference to this Builder
         */
        public Builder withLength(BigDecimal lengthToUse) {
            this.length = lengthToUse;
            return this;
        }

        /**
         * Sets the {@code width} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param widthToUse the {@code width} to set
         * @return a reference to this Builder
         */
        public Builder withWidth(BigDecimal widthToUse) {
            this.width = widthToUse;
            return this;
        }

        /**
         * Sets the {@code height} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param heightToUse the {@code height} to set
         * @return a reference to this Builder
         */
        public Builder withHeight(BigDecimal heightToUse) {
            this.height = heightToUse;
            return this;
        }

        /**
         * Returns a {@code Item} built from the parameters previously set.
         *
         * @return a {@code Item} built with parameters of this {@code Item.Builder}
         */
        public Item build() {
            return new Item(this);
        }
    }
}
