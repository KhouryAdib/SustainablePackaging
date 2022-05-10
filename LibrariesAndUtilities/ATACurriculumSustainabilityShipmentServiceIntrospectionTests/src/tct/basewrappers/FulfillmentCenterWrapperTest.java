package tct.basewrappers;

import org.junit.jupiter.api.Test;

import static com.amazon.ata.test.helper.AtaTestHelper.failTestWithException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FulfillmentCenterWrapperTest {
    @Test
    void fulfillmentCenterConstructor_withFulfillmentCenter_isCallable() {
        // GIVEN - FulfillmentCenter instance
        Object fulfillmentCenter = new FulfillmentCenterWrapper("IAD1").getWrappedInstance();

        // WHEN construct with string
        // THEN - no exceptions
        try {
            new FulfillmentCenterWrapper(fulfillmentCenter);
        } catch (Exception e) {
            failTestWithException(e, "Failed to instantiate new FulfillmentCenterWrapper with FulfillmentCenter");
        }
    }

    @Test
    void fulfillmentConstructor_onNullValue_doesNotThrowException() {
        // GIVEN - wrapped instance is null
        // WHEN + - no exception
        new FulfillmentCenterWrapper((Object) null);
    }

    @Test
    void stringConstructor_withFcCode_isCallable() {
        // GIVEN - FC code
        String fcCode = "IAD1";

        // WHEN + THEN - no exception
        new FulfillmentCenterWrapper(fcCode);
    }

    @Test
    void getFcCode_returnsFcCode() {
        // GIVEN - wrapper instance with FC code
        String fcCode = "IAD1";
        FulfillmentCenterWrapper fulFillmentCenterWrapper = new FulfillmentCenterWrapper(fcCode);

        // WHEN construct with string
        String result = fulFillmentCenterWrapper.getFcCode();

        // THEN - string matches
        assertEquals(fcCode, result, "Expected FulfillmentCenterWrapper.getFcCode() to match original FC code");
    }

    @Test
    void getFcCode_onNullValue_throwsNullPointerException() {
        // GIVEN - wrapped instance is null
        FulfillmentCenterWrapper nullWrapper = new FulfillmentCenterWrapper((Object) null);

        // WHEN + THEN - assert failure
        assertThrows(NullPointerException.class, () -> nullWrapper.getFcCode());
    }
}
