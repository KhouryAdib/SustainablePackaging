package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FcPackagingOptionWrapperTest {
    private FcPackagingOptionWrapper fcPackagingOptionWrapper;
    private FulfillmentCenterWrapper fulfillmentCenterWrapper;
    private PackagingWrapper packagingWrapper;

    @BeforeEach
    private void setup() {
        fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD1");
        packagingWrapper = PackagingFactory.anyPackagingWrapper();

        fcPackagingOptionWrapper = new FcPackagingOptionWrapper(fulfillmentCenterWrapper, packagingWrapper);
    }

    @Test
    void fcPackagingOptionConstructor_withFcPackagingOption_isCallable() {
        // GIVEN - FcPackagingOption instance
        Object fcPackagingOption =
            new FcPackagingOptionWrapper(fulfillmentCenterWrapper, packagingWrapper).getWrappedInstance();

        // WHEN + THEN - no exceptions
        new FcPackagingOptionWrapper(fcPackagingOption);
    }

    @Test
    void fcAndPackagingConstructor_withArgs_isCallable() {
        // GIVEN - FulfillmentCenterWrapper, PackagingWrapper
        // WHEN + THEN - no exception
        new FcPackagingOptionWrapper(fulfillmentCenterWrapper, packagingWrapper);
    }

    @Test
    void getFulfillmentCenter_returnsFulFillmentCenterWrapper() {
        // GIVEN - FcPackagingOptionWrapper
        // WHEN
        FulfillmentCenterWrapper result = fcPackagingOptionWrapper.getFulfillmentCenter();

        // THEN
        assertEquals(
            fulfillmentCenterWrapper,
            result,
            "Expected to get same FulfillmentCenterWrapper back from call to getFulfillmentCenter()"
        );
    }

    @Test
    void getPackaging_returnPackagingWrapper() {
        // GIVEN - FcPackagingOptionWrapper
        // WHEN
        PackagingWrapper result = fcPackagingOptionWrapper.getPackaging();

        // THEN
        assertEquals(
            packagingWrapper,
            result,
            "Expected to get same PackagingWrapper back from call to getPackaging()"
        );
    }
}
