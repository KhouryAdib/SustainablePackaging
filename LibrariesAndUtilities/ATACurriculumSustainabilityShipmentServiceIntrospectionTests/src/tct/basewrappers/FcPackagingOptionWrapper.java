package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Constructor;

/**
 * Represents an FcPackaingOption from the base package.
 */
public class FcPackagingOptionWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "FcPackagingOption";

    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);

    /**
     * Instantiates a new FcPackagingOptionWrapper with an FcPackagingOption instance.
     * @param wrappedInstance The FcPackagingOption instance to wrap
     */
    public FcPackagingOptionWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Instantiates a new FcPackagingOptionWrapper with the given FulfillmentCenterWrapper and PackagingWrapper.
     *
     * @param fulfillmentCenterWrapper the FulfillmentCenterWrapper to use
     * @param packagingWrapper the PackagingWrapper to use
     */
    public FcPackagingOptionWrapper(
            final FulfillmentCenterWrapper fulfillmentCenterWrapper, final PackagingWrapper packagingWrapper) {

        this(WrapperBase.invokeConstructor(
                FcPackagingOptionWrapper.getFcAndPackagingConstructor(),
                fulfillmentCenterWrapper.getWrappedInstance(),
                packagingWrapper.getWrappedInstance())
        );
    }

    /**
     * Returns the fulfillment center (Wrapper) for this option.
     * @return FulfillmentCenterWrapper representing the FC in this option
     */
    public FulfillmentCenterWrapper getFulfillmentCenter() {
        return new FulfillmentCenterWrapper(
            invokeInstanceMethodWithReturnValue(getMethod("getFulfillmentCenter"))
        );
    }

    /**
     * Returns the packaging (Wrapper) for this option. Fails if receives an instance of Packaging or any
     * unrecognized class.
     *
     * @return The PackagingWrapper subclass instance representing the packaging in this option
     */
    public PackagingWrapper getPackaging() {
        Object packaging = invokeInstanceMethodWithReturnValue(getMethod("getPackaging"));

        return PackagingWrapper.getPackagingWrapperForPackaging(packaging);
    }

    @Override
    public Class<?> getWrappedClass() {
        return FcPackagingOptionWrapper.getWrappedClassStatic();
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
     * @return The class wrapped by this wrapper class
     */
    static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }

    private static Constructor<?> getFcAndPackagingConstructor() {
        return WrapperBase.getConstructor(
            WRAPPED_CLASS,
            FulfillmentCenterWrapper.getWrappedClassStatic(),
            PackagingWrapper.getWrappedClassStatic()
        );
    }
}
