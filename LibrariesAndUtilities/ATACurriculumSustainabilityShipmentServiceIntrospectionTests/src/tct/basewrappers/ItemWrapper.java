package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Represents an Item class from the base project by wrapping an instance of Item
 * and exposing the necessary methods.
 */
public class ItemWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "Item";
    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
    private static final Class<?> WRAPPED_CLASS_BUILDER =
        ProjectClassFactory.findNestedClass(WRAPPED_CLASS_LOCAL_CLASSPATH, "Builder", WRAPPED_CLASS_NAME);

    /**
     * Instantiates a new ItemWrapper with an Item instance.
     * @param wrappedInstance The Item instance to wrap
     */
    public ItemWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    private ItemWrapper(final Builder itemWrapperBuilder) {
        this(buildItemInstance(itemWrapperBuilder));
    }

    /**
     * Returns the wrapped Item's ASIN.
     * @return the Item's ASIN
     */
    public String getAsin() {
        return (String) invokeInstanceMethodWithReturnValue(getMethod("getAsin"));
    }

    /**
     * Returns the wrapped Item's description.
     * @return the Item's description
     */
    public String getDescription() {
        return (String) invokeInstanceMethodWithReturnValue(getMethod("getDescription"));
    }

    /**
     * Returns the wrapped Item's length.
     * @return the Item's length
     */
    public BigDecimal getLength() {
        return (BigDecimal) invokeInstanceMethodWithReturnValue(getMethod("getLength"));
    }

    /**
     * Returns the wrapped Item's width.
     * @return the Item's width
     */
    public BigDecimal getWidth() {
        return (BigDecimal) invokeInstanceMethodWithReturnValue(getMethod("getWidth"));
    }

    /**
     * Returns the wrapped Item's height.
     * @return the Item's height
     */
    public BigDecimal getHeight() {
        return (BigDecimal) invokeInstanceMethodWithReturnValue(getMethod("getHeight"));
    }

    /**
     * Returns a {@code Builder} suitable for building a new {@code ItemWrapper}.
     * @return The {@code Builder}, ready for building
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<?> getWrappedClass() {
        return ItemWrapper.getWrappedClassStatic();
    }

    static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }

    private static Object buildItemInstance(final Builder itemWrapperBuilder) {
        Object itemBuilder = WrapperBase.invokeStaticMethodWithReturnValue(getBuilderStaticMethod());
        itemBuilder = WrapperBase.invokeInstanceMethodWithReturnValue(
            itemBuilder, getBuilderWithAsinMethod(), itemWrapperBuilder.asin);
        itemBuilder = WrapperBase.invokeInstanceMethodWithReturnValue(
            itemBuilder, getBuilderWithDescriptionMethod(), itemWrapperBuilder.description);
        itemBuilder = WrapperBase.invokeInstanceMethodWithReturnValue(
            itemBuilder, getBuilderWithLengthMethod(), itemWrapperBuilder.length);
        itemBuilder = WrapperBase.invokeInstanceMethodWithReturnValue(
            itemBuilder, getBuilderWithWidthMethod(), itemWrapperBuilder.width);
        itemBuilder = WrapperBase.invokeInstanceMethodWithReturnValue(
            itemBuilder, getBuilderWithHeightMethod(), itemWrapperBuilder.height);

        return WrapperBase.invokeInstanceMethodWithReturnValue(itemBuilder, getBuilderBuildMethod());
    }

    private static Method getBuilderStaticMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS, "builder");
    }

    private static Method getBuilderWithAsinMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "withAsin", String.class);
    }

    private static Method getBuilderWithDescriptionMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "withDescription", String.class);
    }

    private static Method getBuilderWithLengthMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "withLength", BigDecimal.class);
    }

    private static Method getBuilderWithWidthMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "withWidth", BigDecimal.class);
    }

    private static Method getBuilderWithHeightMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "withHeight", BigDecimal.class);
    }

    private static Method getBuilderBuildMethod() {
        return WrapperBase.getMethod(WRAPPED_CLASS_BUILDER, "build");
    }

    /**
     * {@code ItemWrapper} builder static inner class.
     */
    public static final class Builder {
        private String asin;
        private String description;
        private BigDecimal length;
        private BigDecimal width;
        private BigDecimal height;

        private Builder() {
        }

        /**
         * Sets the {@code asin} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param asinToUse the {@code asin} to set
         * @return a reference to this Builder
         */
        public Builder withAsin(final String asinToUse) {
            this.asin = asinToUse;
            return this;
        }

        /**
         * Sets the {@code description} and returns a reference to this Builder so that the methods can be chained
         * together.
         *
         * @param descriptionToUse the {@code description} to set
         * @return a reference to this Builder
         */
        public Builder withDescription(final String descriptionToUse) {
            this.description = descriptionToUse;
            return this;
        }

        /**
         * Sets the {@code length} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param lengthToUse the {@code length} to set
         * @return a reference to this Builder
         */
        public Builder withLength(final BigDecimal lengthToUse) {
            this.length = lengthToUse;
            return this;
        }

        /**
         * Sets the {@code width} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param widthToUse the {@code width} to set
         * @return a reference to this Builder
         */
        public Builder withWidth(final BigDecimal widthToUse) {
            this.width = widthToUse;
            return this;
        }

        /**
         * Sets the {@code height} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param heightToUse the {@code height} to set
         * @return a reference to this Builder
         */
        public Builder withHeight(final BigDecimal heightToUse) {
            this.height = heightToUse;
            return this;
        }

        /**
         * Returns a {@code ItemWrapper} built from the parameters previously set.
         *
         * @return a {@code ItemWrapper} built with parameters of this {@code ItemWrapper.Builder}
         */
        public ItemWrapper build() {
            return new ItemWrapper(this);
        }
    }
}
