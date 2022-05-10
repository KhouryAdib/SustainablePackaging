package tct.basewrappers;

import tct.ProjectClassFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Wraps the Material enum. Behaves a little differently from an enum, as values are accessed via
 * factory methods, {@code fromString(String)} and {@code fromMaterial(Material <as Object>)}.
 */
public class MaterialEnumWrapper extends WrapperBase {
    public static final BigDecimal GRAMS_PER_SQUARE_CM_CORRUGATE = BigDecimal.ONE;
    public static final BigDecimal LAMINATED_PLASTIC_MASS_FACTOR = new BigDecimal("0.6");

    private static final String WRAPPED_CLASS_LOCAL_CLASSPATH = "types";
    private static final String WRAPPED_CLASS_NAME = "Material";
    private static final Class<?> WRAPPED_CLASS =
        ProjectClassFactory.getClass(WRAPPED_CLASS_LOCAL_CLASSPATH, WRAPPED_CLASS_NAME, Enum.class);
    private static final Map<String, MaterialEnumWrapper> nameToWrapperMapping = new HashMap<>();
    private static final Map<Object, MaterialEnumWrapper> materialToWrapperMapping = new HashMap<>();

    /**
     * Instantiates a new MaterialEnumWrapper with a Material instance.
     *
     * @param wrappedInstance The Material instance to wrap
     */
    private MaterialEnumWrapper(final Object wrappedInstance) {
        super(wrappedInstance);

        // also register the instance in mappings for later lookup by String/Material
        materialToWrapperMapping.put(getWrappedInstance(), this);
        nameToWrapperMapping.put(name(), this);
    }

    /**
     * Get the appropriate MaterialEnumWrapper for the given Material enum constant.
     *
     * @param materialEnumConstant The Material enum constant to wrap into MaterialWrapper
     * @return The appropriate MaterialEnumWrapper for the given Material
     */
    public static MaterialEnumWrapper fromMaterial(final Object materialEnumConstant) {
        return getMaterialToWrapperMapping().get(materialEnumConstant);
    }

    /**
     * Get the appropriate MaterialEnumWrapper for the given material name string.
     *
     * @param materialName The Material name (as {@code String} to retrieve the appropriate MaterialEnumWrapper for
     * @return The appropriate MaterialEnumWrapper for the given name
     */
    public static MaterialEnumWrapper fromString(final String materialName) {
        if (!getNameToWrapperMapping().containsKey(materialName)) {
            fail(String.format("%s does not contain enum value '%s'", WRAPPED_CLASS.getSimpleName(), materialName));
        }
        return getNameToWrapperMapping().get(materialName);
    }

    /**
     * Returns the result of calling Material#name() for the wrapped Material.
     *
     * @return The String representation of the wrapped Material
     */
    public String name() {
        return (String) invokeInstanceMethodWithReturnValue(getNameMethod());
    }

    private static Map<Object, MaterialEnumWrapper> getMaterialToWrapperMapping() {
        if (materialToWrapperMapping.isEmpty()) {
            populateWrapperInstances();
        }
        return materialToWrapperMapping;
    }

    private static Map<String, MaterialEnumWrapper> getNameToWrapperMapping() {
        if (nameToWrapperMapping.isEmpty()) {
            populateWrapperInstances();
        }
        return nameToWrapperMapping;
    }

    private static void populateWrapperInstances() {
        for (Object enumConstant : getWrappedClassStatic().getEnumConstants()) {
            // constructor registers the mappings
            new MaterialEnumWrapper(enumConstant);
        }
    }

    @Override
    public Class<?> getWrappedClass() {
        return MaterialEnumWrapper.getWrappedClassStatic();
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

    private Method getNameMethod() {
        return getMethod("name");
    }
}
