package com.amazon.ata.types;

import tct.basewrappers.FulfillmentCenterWrapper;
import tct.basewrappers.ItemWrapper;
import tct.basewrappers.PackagingWrapper;
import tct.basewrappers.ShipmentOptionWrapper;

import java.math.BigDecimal;

public class ShipmentOptionFactory {
    private static final ItemWrapper ITEM =
        ItemWrapper.builder()
            .withAsin("765432109")
            .withDescription("Anitem")
            .withLength(BigDecimal.TEN)
            .withWidth(BigDecimal.TEN)
            .withHeight(BigDecimal.TEN)
            .build();
    private static final FulfillmentCenterWrapper FULFILLMENT_CENTER = new FulfillmentCenterWrapper("IAD2");

    private ShipmentOptionFactory() {
    }

    /**
     * Returns a ShipmentOptionWrapper for the given packaging type.
     *
     * @param packagingWrapper The Packaging (or subclass) to wrap in the {@code ShipmentOption}
     * @return The populated ShipmentOptionWrapper
     */
    public static ShipmentOptionWrapper shipmentOptionWrapperForPackaging(PackagingWrapper packagingWrapper) {
        return ShipmentOptionWrapper.builder()
            .withItem(ITEM)
            .withFulfillmentCenter(FULFILLMENT_CENTER)
            .withPackaging(packagingWrapper)
            .build();
    }
}
