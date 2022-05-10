package com.amazon.ata.types;

import tct.basewrappers.BoxWrapper;
import tct.basewrappers.FcPackagingOptionWrapper;
import tct.basewrappers.OriginalPackagingWrapper;
import tct.basewrappers.PackagingDatastoreWrapper;
import tct.basewrappers.PackagingWrapper;
import tct.basewrappers.PolyBagWrapper;

import java.util.List;

/**
 * Class in the same package as {@code Packaging} from the base project,
 * so that it can call a protected constructor on {@code Packaging}.
 *
 * Unfortunately, can't really unit test this class in the IntrospectionTests
 * package build (can consider adding a unit test that's run during the
 * Lambda package build as we do with the Wrappers).
 */
public class PackagingFactory {
    private PackagingFactory() {
    }

    /**
     * Finds a PackagingWrapper of any kind. If none found, returns null.
     *
     * @return Valid instance of PackagingWrapper subclass if any found, null otherwise
     */
    public static PackagingWrapper anyPackagingWrapper() {
        PackagingWrapper packagingWrapper = boxWrapperOfAnyDimensions();

        if (null == packagingWrapper) {
            packagingWrapper = polyBagWrapperOfAnyVolume();
        }

        if (null == packagingWrapper) {
            packagingWrapper = originalPackagingOfAnyDimensions();
        }

        return packagingWrapper;
    }

    /**
     * If possible, fetches an instance of the base project's original Packaging class.
     * If no Packaging is found, returns null.
     *
     * @return OriginalPackagingWrapper if found, null otherwise
     */
    public static OriginalPackagingWrapper originalPackagingOfAnyDimensions() {
        return packagingWrapperOfAnySize(OriginalPackagingWrapper.class);
    }

    /**
     * If possible, fetches a Box (Wrapper) of any dimension found. If no Box
     * can be found/created, returns null.
     *
     * @return BoxWrapper if found, null otherwise
     */
    public static BoxWrapper boxWrapperOfAnyDimensions() {
        return packagingWrapperOfAnySize(BoxWrapper.class);
    }

    /**
     * If possible, fetches a PolyBag (Wrapper) of any volume found. If no PolyBag
     * can be found/created, returns null.
     *
     * @return PolyBagWrapper if found, null otherwise
     */
    public static PolyBagWrapper polyBagWrapperOfAnyVolume() {
        return packagingWrapperOfAnySize(PolyBagWrapper.class);
    }

    /**
     * If possible, fetches a packaging (Wrapper) of any size found for the packaging wrapper
     * type specified by {@code packagingWrapperClass}.
     *
     * @param packagingWrapperClass The class of PackagingWrapper to find
     * @param <T> The type of PackagingWrapper to find. Must be a PackagingWrapper subclass
     * @return The PackagingWrapper (subclass) if any found, null otherwise
     */
    public static <T extends PackagingWrapper> T packagingWrapperOfAnySize(Class<T> packagingWrapperClass) {
        PackagingDatastoreWrapper packagingDatastore = new PackagingDatastoreWrapper();
        List<FcPackagingOptionWrapper> packagingOptions = packagingDatastore.getFcPackagingOptions();

        for (FcPackagingOptionWrapper packagingOption : packagingOptions) {
            if (packagingWrapperClass.isInstance(packagingOption.getPackaging())) {
                return (T) packagingOption.getPackaging();
            }
        }

        return null;
    }
}
