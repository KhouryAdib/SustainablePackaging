package tct;

import com.amazon.ata.test.reflect.ClassQuery;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.amazon.ata.test.assertions.IntrospectionAssertions.assertClassContainsMemberMethodNames;
import static com.amazon.ata.test.assertions.IntrospectionAssertions.assertClassContainsMemberVariableTypes;
import static com.amazon.ata.test.assertions.IntrospectionAssertions.assertClassDoesNotContainMemberMethodNames;
import static com.amazon.ata.test.assertions.IntrospectionAssertions.assertDirectlyExtends;

@Tag("MT03")
public class MT3IntrospectionTests {
    private static final String TYPES_PACKAGE = "com.amazon.ata.types";
    private static final String BOX_CLASS_NAME = "Box";
    private static final String POLYBAG_CLASS_NAME = "PolyBag";
    private static final String PACKAGING_CLASS_NAME = "Packaging";

    @Test
    void mt3_introspectBoxClass_exists() {
        // GIVEN + WHEN + THEN - Box class exists
        ClassQuery.inExactPackage(TYPES_PACKAGE).withExactSimpleName(BOX_CLASS_NAME).findClassOrFail();
    }

    @Test
    void mt3_introspectPolyBagClass_exists() {
        // GIVEN + WHEN + THEN - PolyBag class exists
        ClassQuery.inExactPackage(TYPES_PACKAGE).withExactSimpleName(POLYBAG_CLASS_NAME).findClassOrFail();
    }

    @Test
    void mt3_introspectBoxClass_hasExpectedClassHierarchy() {
        // GIVEN - Box class and Packaging class
        Class<?> boxClass = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(BOX_CLASS_NAME)
            .findClassOrFail();

        Class<?> packagingClass = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(PACKAGING_CLASS_NAME)
            .findClassOrFail();

        // WHEN + THEN - Packaging is superclass
        assertDirectlyExtends(boxClass, packagingClass);
    }

    @Test
    void mt3_introspectPolyBagClass_hasExpectedClassHierarchy() {
        // GIVEN - PolyBag class and Packaging class
        Class<?> polyBagClass = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(POLYBAG_CLASS_NAME)
            .findClassOrFail();

        Class<?> packagingClass =  ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(PACKAGING_CLASS_NAME)
            .findClassOrFail();

        // WHEN + THEN - Packaging is superclass
        assertDirectlyExtends(polyBagClass, packagingClass);
    }

    @Test
    void mt3_introspectPackagingClass_hasExpectedMemberVariableType() {
        // GIVEN - the Packaging class
        Class<?> packagingClazz = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(PACKAGING_CLASS_NAME)
            .findClassOrFail();
        // expected member variable types
        String[] expectedMemberTypes = {TYPES_PACKAGE + "." + "Material"};

        // WHEN + THEN
        assertClassContainsMemberVariableTypes(packagingClazz, expectedMemberTypes);
    }

    @Test
    void mt3_introspectPackagingClass_hasExpectedMemberMethods() {
        // GIVEN - the Packaging class
        Class<?> packagingClazz = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(PACKAGING_CLASS_NAME)
            .findClassOrFail();
        // expected member methods
        String[] expectedMethodNames = {"getMaterial", "canFitItem", "getMass"};

        // WHEN + THEN - Packaging has getMaterial() and canFitItem() method
        assertClassContainsMemberMethodNames(packagingClazz, expectedMethodNames);
    }

    @Test
    void mt3_introspectPackagingClass_doesNotHaveUnexpectedMemberMethods() {
        // GIVEN - the Packaging class
        Class<?> packagingClazz = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(PACKAGING_CLASS_NAME)
            .findClassOrFail();
        // method names NOT expected to be seen
        String[] unexpectedMethodNames = {"getLength", "getWidth", "getHeight"};

        // WHEN + THEN = Packaging does not have the old box methods
        assertClassDoesNotContainMemberMethodNames(packagingClazz, unexpectedMethodNames);
    }

    @Test
    void mt3_introspectBoxClass_hasExpectedMemberVariableTypes() {
        // GIVEN - the Box class
        Class<?> boxClazz = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(BOX_CLASS_NAME)
            .findClassOrFail();
        // expected member variable types
        String[] expectedMemberTypes = {"java.math.BigDecimal", "java.math.BigDecimal", "java.math.BigDecimal"};

        // WHEN + THEN - Box contains 3 BigDecimals (W, L, H)
        assertClassContainsMemberVariableTypes(boxClazz, expectedMemberTypes);
    }

    @Test
    void mt3_introspectPolyBagClass_hasExpectedMemberVariableTypes() {
        // GIVEN - the PolyBag class
        Class<?> polyBagClazz = ClassQuery.inExactPackage(TYPES_PACKAGE)
            .withExactSimpleName(POLYBAG_CLASS_NAME)
            .findClassOrFail();
        // expected member variable types
        String[] expectedMemberTypes = {"java.math.BigDecimal"};

        // WHEN + THEN - PolyBag contains a BigDecimal (volume)
        assertClassContainsMemberVariableTypes(polyBagClazz, expectedMemberTypes);
    }
}
