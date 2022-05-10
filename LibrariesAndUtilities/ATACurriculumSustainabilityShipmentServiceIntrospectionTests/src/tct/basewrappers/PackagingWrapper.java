package tct.basewrappers;

import tct.ProjectClassFactory;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class PackagingWrapper extends WrapperBase {
    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "Packaging";

    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME);
    /**
     * Instantiates, passing wrapped instance to superclass.
     * @param wrappedInstance The PackagingWrapper subclass instance to wrap
     */
    protected PackagingWrapper(final Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Returns the mass of the wrapped packaging instance.
     *
     * @return The wrapped packaging instance's mass
     */
    public abstract BigDecimal getMass();

    /**
     * Returns an appropriate {@code PackagingWrapper} subclass for the given {@code packaging} instance.
     *
     * {@code fail()}s on invalid objects.
     *
     * @param packaging Instance of a Packaging subclass
     * @return An instance of the appropriate {@code PackagingWrapper} subclass
     */
    public static PackagingWrapper getPackagingWrapperForPackaging(final Object packaging) {
        PackagingWrapper packagingWrapper = null;
        switch (packaging.getClass().getSimpleName()) {
            case "Box":
                packagingWrapper = new BoxWrapper(packaging);
                break;
            case "PolyBag":
                packagingWrapper = new PolyBagWrapper(packaging);
                break;
            case "Packaging":
                packagingWrapper = new OriginalPackagingWrapper(packaging);
                break;
            default:
                fail(String.format("Unrecognized packaging type '%s'", packaging.getClass().getSimpleName()));
                break;
        }

        return packagingWrapper;
    }

    /**
     * Returns this wrapper class's wrapped class type.
     *
     * Needed for cases where we're retrieving the wrapped type without an existing
     * wrapper instance to call {@code getWrappedClass()} from.
     * @return The class wrapped by this wrapper class
     */
    public static Class<?> getWrappedClassStatic() {
        return WRAPPED_CLASS;
    }
}
