package tct;

import tct.basewrappers.BoxWrapper;
import tct.basewrappers.FcPackagingOptionWrapper;
import tct.basewrappers.FulfillmentCenterWrapper;
import tct.basewrappers.ItemWrapper;
import tct.basewrappers.PackagingDAOWrapper;
import tct.basewrappers.PackagingDatastoreWrapper;
import tct.basewrappers.PackagingWrapper;
import tct.basewrappers.ShipmentOptionWrapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("MT04_UNIQUE")
public class MT4UniqueIntrospectionTests {
    private static final String IAD2_FC_CODE = "IAD2";
    private static final BigDecimal DUPE_BOX_DIMENSION = new BigDecimal(20);

    @Test
    void mt4_unique_packagingDatastore_getFcPackagingOptions_stillReturnsDuplicateBoxes() {
        // GIVEN - fc code, duped box dimension
        // packaging datastore
        PackagingDatastoreWrapper packagingDatastoreWrapper = new PackagingDatastoreWrapper();
        // expected number of duplicates with same dimensions
        int expectedNumberOfDupes = 2;

        // WHEN
        List<FcPackagingOptionWrapper> fcPackagingOptions = packagingDatastoreWrapper.getFcPackagingOptions();

        // THEN - we still have two of same Box for same FC in the **datastore** (DAO does the filtering)
        int foundDupeBoxes = 0;
        for (FcPackagingOptionWrapper fcPackagingOption : fcPackagingOptions) {
            if (isDupeFcAndBox(fcPackagingOption.getFulfillmentCenter(), fcPackagingOption.getPackaging())) {
                foundDupeBoxes += 1;
            }
        }

        assertEquals(
            expectedNumberOfDupes,
            foundDupeBoxes,
            String.format(
                "Expected PackagingDatastore's FcPackagingOptions to include %d duplicate Boxes, but found %d. " +
                "Please make sure you haven't removed any!",
                expectedNumberOfDupes, foundDupeBoxes)
        );
    }

    @Test
    void mt4_unique_packagingDAO_getShipmentOptions_doesNotReturnDuplicateBoxes() {
        // GIVEN
        // PackagingDAO
        PackagingDAOWrapper packagingDaoWrapper = new PackagingDAOWrapper();
        // item smaller than DUPE_BOX_DIMENSION^3
        ItemWrapper itemWrapper = ItemWrapper.builder()
            .withAsin("345678901")
            .withDescription("A dangerously delightful item")
            .withLength(new BigDecimal(1))
            .withWidth(new BigDecimal(1))
            .withHeight(new BigDecimal(1))
            .build();
        // fulfillment center with relevant FC code
        FulfillmentCenterWrapper fulfillmentCenterWrapper = new FulfillmentCenterWrapper(IAD2_FC_CODE);

        // WHEN
        List<ShipmentOptionWrapper> shipmentOptionWrappers =
            packagingDaoWrapper.findShipmentOptions(itemWrapper, fulfillmentCenterWrapper);

        // THEN - we only find one of the same Box for the same FC from the **DAO**
        int foundDupeBoxes = 0;
        for (ShipmentOptionWrapper shipmentOptionWrapper : shipmentOptionWrappers) {
            if (isDupeFcAndBox(shipmentOptionWrapper.getFulfillmentCenter(), shipmentOptionWrapper.getPackaging())) {
                foundDupeBoxes += 1;
            }
        }

        // expect only one box of this dimension
        if (foundDupeBoxes == 0) {
            fail("Expected PackagingDAO#findShipmentOptions to respond with at least one Box, but found none.");
        }
        assertEquals(
            1,
            foundDupeBoxes,
            String.format(
                "Expected PackagingDAO#findShipmentOptions not to include any duplicate Boxes, but found %d",
                foundDupeBoxes)
        );
    }

    private boolean isDupeFcAndBox(
            final FulfillmentCenterWrapper fulfillmentCenterWrapper,
            final PackagingWrapper packagingWrapper) {

        if (!IAD2_FC_CODE.equals(fulfillmentCenterWrapper.getFcCode())) {
            return false;
        }

        if (!(packagingWrapper instanceof BoxWrapper)) {
            return false;
        }

        BoxWrapper box = (BoxWrapper) packagingWrapper;

        return (DUPE_BOX_DIMENSION.equals(box.getHeight())) &&
            (DUPE_BOX_DIMENSION.equals(box.getLength())) &&
            (DUPE_BOX_DIMENSION.equals(box.getWidth()));
    }
}
