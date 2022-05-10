package com.amazon.ata.datastore;

import com.amazon.ata.types.FcPackagingOption;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Stores all configured packaging pairs for all fulfillment centers.
 */
public class PackagingDatastore {

    /**
     * The stored pairs of fulfillment centers to the packaging options they support.
     */
    private final List<FcPackagingOption> fcPackagingOptions = Arrays.asList(
            createFcPackagingOption("IND1", Material.CORRUGATE, "10", "10", "10"),
            createFcPackagingOption("ABE2", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingOption("ABE2", Material.CORRUGATE, "40", "40", "40"),
            createFcPackagingOption("YOW4", Material.CORRUGATE, "10", "10", "10"),
            createFcPackagingOption("YOW4", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingOption("YOW4", Material.CORRUGATE, "60", "60", "60"),
            createFcPackagingOption("IAD2", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingOption("IAD2", Material.CORRUGATE, "20", "20", "20"),
            createFcPackagingOption("PDX1", Material.CORRUGATE, "40", "40", "40"),
            createFcPackagingOption("PDX1", Material.CORRUGATE, "60", "60", "60"),
            createFcPackagingOption("PDX1", Material.CORRUGATE, "60", "60", "60")
    );

    /**
     * Create fulfillment center packaging option from provided parameters.
     */
    private FcPackagingOption createFcPackagingOption(String fcCode, Material material,
                                                      String length, String width, String height) {
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(fcCode);
        Packaging packaging = new Packaging(material, new BigDecimal(length), new BigDecimal(width),
                new BigDecimal(height));

        return new FcPackagingOption(fulfillmentCenter, packaging);
    }

    public List<FcPackagingOption> getFcPackagingOptions() {
        return fcPackagingOptions;
    }
}
