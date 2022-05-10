package tct.basewrappers;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.amazon.ata.test.helper.AtaTestHelper.failTestWithException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class PackagingDatastoreWrapperTest {

    @Test
    void packagingDatastoreConstructor_isCallable() {
        // GIVEN - packagingDatastore instance
        Object packagingDatastore = null;
        try {
            packagingDatastore = PackagingDatastoreWrapper.getWrappedClassStatic().getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            fail(String.format(
                "Expected %s to have a no-args constructor but it did not",
                PackagingDatastoreWrapper.getWrappedClassStatic().getSimpleName())
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            failTestWithException(
                e,
                "Failure while no-args constructor of " + PackagingDatastoreWrapper.getWrappedClassStatic()
            );
        }

        // WHEN + THEN - no exceptions
        new PackagingDatastoreWrapper(packagingDatastore);
    }

    @Test
    void noArgsConstructor_isCallable() {
        // GIVEN
        // WHEN + THEN - no exceptions
        new PackagingDatastoreWrapper();
    }

    @Test
    void getFcPackagingOptions_returnsMoreThanOneFcPackagingOptionWrapper() {
        // GIVEN - PackagingDatastoreWrapper
        PackagingDatastoreWrapper packagingDatastoreWrapper = new PackagingDatastoreWrapper();

        // WHEN
        List<FcPackagingOptionWrapper> fcPackagingOptions = packagingDatastoreWrapper.getFcPackagingOptions();

        // THEN
        assertFalse(
            fcPackagingOptions.isEmpty(),
            "Expected PackagingDatastore to return at least one FcPackagingOption!"
        );
    }
}
