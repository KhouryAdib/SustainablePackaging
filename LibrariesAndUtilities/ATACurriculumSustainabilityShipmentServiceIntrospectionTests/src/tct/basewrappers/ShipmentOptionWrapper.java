package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Method;

/**
 * Represents a {@code ShipmentOption} from the base project by wrapping an instance of ShipmentOption
 * and exposing the necessary methods.
 */
public class ShipmentOptionWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "ShipmentOption";
    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
    private static final Class<?> WRAPPED_CLASS_BUILDER =
        ProjectClassFactory.findNestedClass(WRAPPED_CLASS_LOCAL_CLASSPATH, "Builder", WRAPPED_CLASS_NAME);

    /**
     * Instantiates a new ShipmentOptionWrapper with a ShipmentOption instance.
     * @param wrappedInstance The ShipmentOption instance to wrap
     */
    public ShipmentOptionWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    private ShipmentOptionWrapper(final Builder shipmentOptionBuilder) {
        this(buildShipmentOptionInstance(shipmentOptionBuilder));
    }

    /**
     * Returns the wrapped {@code ShipmentOption}'s item.
     * @return the {@code ShipmentOption}'s item (as an {@code ItemWrapper})
     */
    public ItemWrapper getItem() {
        return new ItemWrapper(invokeInstanceMethodWithReturnValue(getMethod("getItem")));
    }

    /**
     * Returns the wrapped {@code ShipmentOption}'s packaging.
     * @return the {@code ShipmentOption}'s packaging (as a {@code Packaging} subclass instance)
     */
    public PackagingWrapper getPackaging() {
        return PackagingWrapper.getPackagingWrapperForPackaging(
            invokeInstanceMethodWithReturnValue(getMethod("getPackaging"))
        );
    }

    /**
     * Returns the wrapped {@code ShipmentOption}'s fulfillment center.
     * @return the {@code ShipmentOption}'s fulfillment center (as an {@code FulfillmentCenterWrapper})
     */
    public FulfillmentCenterWrapper getFulfillmentCenter() {
        return new FulfillmentCenterWrapper(
            invokeInstanceMethodWithReturnValue(getMethod("getFulfillmentCenter"))
        );
    }

    /**
     * Returns a {@code Builder} suitable for building a new {@code ShipmentOptionWrapper}.
     * @return The {@code Builder}, ready for building
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<?> getWrappedClass() {
        return ShipmentOptionWrapper.getWrappedClassStatic();
    }

    static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }

    private static Object buildShipmentOptionInstance(final Builder shipmentOptionWrapperBuilder) {
        Object shipmentOptionBuilder = WrapperBase.invokeStaticMethodWithReturnValue(getBuilderStaticMethod());

        shipmentOptionBuilder = invokeInstanceMethodWithReturnValue(
            shipmentOptionBuilder,
            getBuilderWithItemMethod(),
            shipmentOptionWrapperBuilder.itemWrapper.getWrappedInstance());
        shipmentOptionBuilder = invokeInstanceMethodWithReturnValue(
            shipmentOptionBuilder,
            getBuilderWithPackagingMethod(),
            shipmentOptionWrapperBuilder.packagingWrapper.getWrappedInstance());
        shipmentOptionBuilder = invokeInstanceMethodWithReturnValue(
            shipmentOptionBuilder,
            getBuilderWithFulfillmentCenterMethod(),
            shipmentOptionWrapperBuilder.fulfillmentCenterWrapper.getWrappedInstance());

        return WrapperBase.invokeInstanceMethodWithReturnValue(shipmentOptionBuilder, getBuilderBuildMethod());
    }

    private static Method getBuilderStaticMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS, "builder");
    }

    private static Method getBuilderWithItemMethod() {
        return getMethod(WRAPPED_CLASS_BUILDER, "withItem", ItemWrapper.getWrappedClassStatic());
    }

    private static Method getBuilderWithPackagingMethod() {
        return getMethod(
            WRAPPED_CLASS_BUILDER,
            "withPackaging",
            PackagingWrapper.getWrappedClassStatic()
        );
    }

    private static Method getBuilderWithFulfillmentCenterMethod() {
        return getMethod(
            WRAPPED_CLASS_BUILDER,
            "withFulfillmentCenter",
            FulfillmentCenterWrapper.getWrappedClassStatic()
        );
    }

    private static Method getBuilderBuildMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "build");
    }

    /**
     * {@code ShipmentOptionWrapper} builder static inner class.
     */
    public static class Builder {
        private ItemWrapper itemWrapper;
        private PackagingWrapper packagingWrapper;
        private FulfillmentCenterWrapper fulfillmentCenterWrapper;

        private Builder() {
        }

        /**
         * Sets the {@code itemWrapper} and returns a reference to this Builder so
         * that the methods can be chained together.
         *
         * @param itemWrapperToUse the {@code item} to set
         * @return a reference to this Builder
         */
        public Builder withItem(final ItemWrapper itemWrapperToUse) {
            this.itemWrapper = itemWrapperToUse;
            return this;
        }

        /**
         * Sets the {@code packagingWrapper} and returns a reference to this Builder
         * so that the methods can be chained together.
         *
         * @param packagingWrapperToUse the {@code packaging} to set
         * @return a reference to this Builder
         */
        public Builder withPackaging(final PackagingWrapper packagingWrapperToUse) {
            this.packagingWrapper = packagingWrapperToUse;
            return this;
        }

        /**
         * Sets the {@code fulfillmentCenterWrapper} and returns a reference to this Builder
         * so that the methods can be chained together.
         *
         * @param fulfillmentCenterWrapperToUse the {@code fulfillmentCenter} to set
         * @return a reference to this Builder
         */
        public Builder withFulfillmentCenter(final FulfillmentCenterWrapper fulfillmentCenterWrapperToUse) {
            this.fulfillmentCenterWrapper = fulfillmentCenterWrapperToUse;
            return this;
        }

        /**
         * Returns a {@code ShipmentOptionWrapper} built from the parameters previously set.
         *
         * @return a {@code ShipmentOptionWrapper} built with parameters of this
         *         {@code ShipmentOptionWrapper.Builder}
         */
        public ShipmentOptionWrapper build() {
            return new ShipmentOptionWrapper(this);
        }
    }
}
