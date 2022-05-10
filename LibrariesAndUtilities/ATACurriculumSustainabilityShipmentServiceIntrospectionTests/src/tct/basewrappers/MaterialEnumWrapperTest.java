package tct.basewrappers;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaterialEnumWrapperTest {
    private static final String MATERIAL_NAME = "CORRUGATE";

    @Test
    void fromString_onValidMaterial_returnsWrappedMaterial() {
        // GIVEN - valid Material name String
        // WHEN
        MaterialEnumWrapper materialEnumWrapper = MaterialEnumWrapper.fromString(MATERIAL_NAME);

        // THEN
        assertNotNull(materialEnumWrapper);
    }

    @Test
    void fromString_onInvalidMaterialName_assertFires() {
        // GIVEN - invalid material name
        String invalidMaterialName = "NOT_A_VALID_MATERIAL_NAME";
        // WHEN + THEN - assert failure
        assertThrows(AssertionFailedError.class, () -> MaterialEnumWrapper.fromString(invalidMaterialName));
    }

    @Test
    void fromString_onSubsequentCalls_returnsExactSameInstance() {
        // GIVEN - valid Material name String
        // WHEN
        MaterialEnumWrapper materialEnumWrapper1 = MaterialEnumWrapper.fromString(MATERIAL_NAME);
        MaterialEnumWrapper materialEnumWrapper2 = MaterialEnumWrapper.fromString(MATERIAL_NAME);

        // THEN - both are the same instance
        assertSame(materialEnumWrapper1, materialEnumWrapper2);
    }

    @Test
    void fromMaterial_withValidMaterial_returnsWrappedMaterial() {
        // GIVEN - valid Material
        Object wrappedMaterial = MaterialEnumWrapper.fromString(MATERIAL_NAME).getWrappedInstance();

        // WHEN
        MaterialEnumWrapper materialEnumWrapper = MaterialEnumWrapper.fromMaterial(wrappedMaterial);

        // THEN
        assertEquals(MATERIAL_NAME, materialEnumWrapper.name());
    }

    @Test
    void fromMaterial_withValidMaterial_returnsExactSameInstanceAsFromString() {
        // GIVEN - valid Material
        MaterialEnumWrapper materialEnumWrapperFromString = MaterialEnumWrapper.fromString(MATERIAL_NAME);
        Object wrappedMaterial = materialEnumWrapperFromString.getWrappedInstance();

        // WHEN
        MaterialEnumWrapper materialEnumWrapperFromMaterial = MaterialEnumWrapper.fromMaterial(wrappedMaterial);

        // THEN - both are the same instance
        assertSame(materialEnumWrapperFromString, materialEnumWrapperFromMaterial);
    }
}
