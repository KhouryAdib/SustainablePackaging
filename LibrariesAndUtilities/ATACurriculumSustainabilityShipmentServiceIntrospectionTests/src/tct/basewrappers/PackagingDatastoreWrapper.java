package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Wraps a PackagingDatastore instance from the base project.
 */
public class PackagingDatastoreWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "datastore";
    private static final String WRAPPED_CLASS_NAME = "PackagingDatastore";

    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);

    /**
     * Instantiates a new PackagingDatastoreWrapper with a PackagingDatastore instance.
     *
     * @param wrappedInstance The PackagingDatastore instance to wrap
     */
    public PackagingDatastoreWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Instantiates a new PackagingDatastoreWrapper by creating its own PackagingDatastore instance to wrap.
     */
    public PackagingDatastoreWrapper() {
        this(WrapperBase.invokeConstructor(PackagingDatastoreWrapper.getNoArgsConstructor()));
    }

    /**
     * Returns the list of {@code FcPackagingOption}s in the wrapped {@code PackagingDatastore}.
     *
     * @return List of {@code FcPackagingOptionWrapper} objects, each representing an {@code FcPackagingOption}
     *         the wrapped {@code PackagingDatastore} instance.
     */
    public List<FcPackagingOptionWrapper> getFcPackagingOptions() {
        List<FcPackagingOptionWrapper> fcPackagingOptionWrappers = new ArrayList<>();

        Object fcPackagingOptions = invokeInstanceMethodWithReturnValue(getMethod("getFcPackagingOptions"));
        if (! (fcPackagingOptions instanceof List<?>)) {
            fail("Expected PackagingDatastore#getFcPackagingOptions to return List<> but found " +
                fcPackagingOptions.getClass().getSimpleName());
        }

        List<?> fcPackingOptionsList = (List<?>) fcPackagingOptions;
        for (Object fcPackagingOption : fcPackingOptionsList) {
            if (!FcPackagingOptionWrapper.getWrappedClassStatic().isInstance(fcPackagingOption)) {
                fail(String.format(
                    "getFcPackagingOptions() returned a list; expected all entries to be %s, but encountered a %s: %s",
                    FcPackagingOptionWrapper.getWrappedClassStatic().getSimpleName(),
                    fcPackagingOption.getClass().getSimpleName(),
                    fcPackagingOption.toString())
                );
            }
            fcPackagingOptionWrappers.add(new FcPackagingOptionWrapper(fcPackagingOption));
        }

        return fcPackagingOptionWrappers;
    }

    @Override
    public Class<?> getWrappedClass() {
        return PackagingDatastoreWrapper.getWrappedClassStatic();
    }

    public static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }

    private static Constructor<?> getNoArgsConstructor() {
        return WrapperBase.getConstructor(WRAPPED_CLASS);
    }
}
