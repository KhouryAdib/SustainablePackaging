package tct;

import com.amazon.ata.test.reflect.ClassQuery;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("MT04")
public class MT4IntrospectionTests {
    private static final String BASE_PACKAGE = "com.amazon.ata.";
    private static final String DAO_PACKAGE = "dao";
    private static final String TYPES_PACKAGE = "types";
    private static final String PACKAGING_DAO_CLASS_NAME = "PackagingDAO";
    private static final String FULFILLMENT_CENTER_CLASS_NAME = "FulfillmentCenter";

    @Test
    void mt4_packagingDAO_fields_includesFulfillmentCenterPackagingSelectionRepresentedWithEfficientDatastore() {
        // GIVEN - PackagingDAO class & FulfillmentCenter class
        Class<?> packagingDaoClass = ClassQuery.inExactPackage(BASE_PACKAGE + DAO_PACKAGE)
            .withExactSimpleName(PACKAGING_DAO_CLASS_NAME)
            .findClassOrFail();

        Class<?> fulfillmentCenterClass = ClassQuery.inExactPackage(BASE_PACKAGE + TYPES_PACKAGE)
            .withExactSimpleName(FULFILLMENT_CENTER_CLASS_NAME)
            .findClassOrFail();

        Field[] fields = packagingDaoClass.getDeclaredFields();

        // WHEN - find a single Map variable
        Field mapField = null;
        for (Field field : fields) {
            if (Map.class.equals(field.getType())) {
                if (null != mapField) {
                    fail(String.format(
                        "Found more than one Map in PackagingDAO:%n%s%n%s",
                        mapField.toString(),
                        field.toString())
                    );
                }

                mapField = field;
            }
        }

        // THEN
        // there is a map variable in PackagingDAO
        assertTrue(
            mapField != null,
            String.format(
                "PackagingDAO does not have packaging items stored in expected data structure; Fields were:%n  %s",
                String.join("\n  ", Arrays.stream(fields).map(Field::toString).collect(Collectors.toList())))
        );

        // the Map variable is parameterized (not raw)
        assertTrue(
            mapField.getGenericType() instanceof ParameterizedType,
            "This Map in PackagingDAO doesn't seem to be a generic!: " + mapField.getName()
        );

        // the Map key type is FulfillmentCenter
        ParameterizedType parameterizedType = (ParameterizedType) mapField.getGenericType();
        Type[] genericParamTypes = parameterizedType.getActualTypeArguments();
        assertEquals(
            fulfillmentCenterClass,
            genericParamTypes[0],
            String.format(
                "Expected the Map (%s) in PackagingDAO to have a different key type. Key type was %s",
                mapField.getName(),
                genericParamTypes[0].toString())
        );

        // The Map value type is Java Set - resorting to name comparison (parameterized interface makes it complicated)
        assertTrue(
            genericParamTypes[1].getTypeName().contains(Set.class.getTypeName()),
            String.format(
                "Expected the Map (%s), in PackagingDAO to have a different value type. Value type was %s",
                mapField.getName(),
                genericParamTypes[1].getTypeName())
        );
    }
}
