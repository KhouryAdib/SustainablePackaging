package tct.basewrappers;

import tct.ProjectClassFactory;

import java.math.BigDecimal;

/**
 * Represents an instance of the original base project Packaging class. This
 * only exists to allow some tests to run in the original base project, but that
 * should end up using Box or PolyBag once those classes are created by participant.
 */
public class OriginalPackagingWrapper extends PackagingWrapper {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "Packaging";

    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
    /**
     * Instantiates a new OriginalPackagingWrapper with a Packaging instance.
     * @param wrappedInstance The Packaging instance to wrap
     */
    public OriginalPackagingWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Unsupported call to return Packaging mass.
     *
     * @return The mass
     */
    public BigDecimal getMass() {
        throw new UnsupportedOperationException(
            "Received unexpected call to getMass() on an original Packaging instance"
        );
    }

    @Override
    public Class<?> getWrappedClass() {
        return OriginalPackagingWrapper.getWrappedClassStatic();
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
     *
     * @return The class wrapped by this wrapper class
     */
    public static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }
}
