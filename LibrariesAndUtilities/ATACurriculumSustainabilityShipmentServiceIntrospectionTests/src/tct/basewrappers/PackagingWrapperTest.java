package tct.basewrappers;

import com.amazon.ata.types.PackagingFactory;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackagingWrapperTest {
    @Test
    void getPackagingWrapperForPackaging_withBox_returnsBoxWrapper() {
        if (!BoxWrapper.testsEnabled()) {
            return;
        }

        // GIVEN - box instance
        Object box = PackagingFactory.boxWrapperOfAnyDimensions().getWrappedInstance();

        // WHEN
        PackagingWrapper result = PackagingWrapper.getPackagingWrapperForPackaging(box);

        // THEN - result is a BoxWrapper
        assertTrue(
            result instanceof BoxWrapper,
            String.format(
                "Expected getPackagingWrapperForPackaging(Box) to return a %s, but got a %s: %s",
                BoxWrapper.class.getSimpleName(),
                result.getClass().getName(),
                result.toString())
        );
    }

    @Test
    void getPackagingWrapperForPackaging_withPolyBag_returnsPolyBagWrapper() {
        if (!PolyBagWrapper.testsEnabled()) {
            return;
        }

        // GIVEN - polyBag instance
        Object polyBag = PackagingFactory.polyBagWrapperOfAnyVolume().getWrappedInstance();

        // WHEN
        PackagingWrapper result = PackagingWrapper.getPackagingWrapperForPackaging(polyBag);

        // THEN - result is a PolyBagWrapper
        assertTrue(
            result instanceof PolyBagWrapper,
            String.format(
                "Expected getPackagingWrapperForPackaging(PolyBag) to return a %s, but got a %s: %s",
                PolyBagWrapper.class.getSimpleName(),
                result.getClass().getName(),
                result.toString())
        );
    }

    @Test
    void getPackagingWrapperForPackaging_withPackaging_returnsOriginalPackagingWrapper() {
        // GIVEN Packaging instance
        OriginalPackagingWrapper originalPackagingWrapper = PackagingFactory.originalPackagingOfAnyDimensions();
        // test passes if no original Packaging found. Participant has implemented Box, PolyBag
        if (null == originalPackagingWrapper) {
            return;
        }
        Object packaging = originalPackagingWrapper.getWrappedInstance();

        // WHEN
        PackagingWrapper result = PackagingWrapper.getPackagingWrapperForPackaging(packaging);

        // THEN - result is an OriginalPackagingWrapper
        assertTrue(
            result instanceof OriginalPackagingWrapper,
            String.format(
                "Expected getPackagingWrapperForPackaging(Packaging) to return a %s, but got a %s: %s",
                OriginalPackagingWrapper.class.getSimpleName(),
                result.getClass().getName(),
                result.toString())
        );
    }

    @Test
    void getPackagingWrapperForPackaging_withOtherObject_assertFires() {
        // GIVEN - FulfillmentCenter
        FulfillmentCenterWrapper fulfillmentCenterWrapper = new FulfillmentCenterWrapper("IAD2");

        // WHEN + THEN - assert failure
        assertThrows(
            AssertionFailedError.class,
            () -> PackagingWrapper.getPackagingWrapperForPackaging(fulfillmentCenterWrapper)
        );
    }
}
