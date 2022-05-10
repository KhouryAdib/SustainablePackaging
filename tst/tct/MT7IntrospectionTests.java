package tct;

import com.amazon.ata.test.reflect.ClassQuery;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.amazon.ata.test.assertions.IntrospectionAssertions.assertMemberMocked;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("MT07")
public class MT7IntrospectionTests {
    private static final String BASE_PACKAGE = "com.amazon.ata.";
    private static final String SERVICE_PACKAGE = "service";
    private static final String DAO_PACKAGE = "dao";
    private static final String DATASTORE_PACKAGE = "datastore";

    private static final String PACKAGING_DAO_CLASS_NAME = "PackagingDAO";
    private static final String SHIPMENT_SERVICE_TEST_CLASS_NAME = "ShipmentServiceTest";
    private static final String SHIPMENT_SERVICE_CLASS_NAME = "ShipmentService";
    private static final String DATASTORE_CLASS_NAME = "PackagingDatastore";

    @Test
    void mt7_shipmentServiceTest_usesMockForPackagingDao() {
        // GIVEN - ShipmentServiceTest, PackagingDAO types
        Class<?> shipmentServiceTestClass = ClassQuery
            .inExactPackage(BASE_PACKAGE + SERVICE_PACKAGE)
            .withExactSimpleName(SHIPMENT_SERVICE_TEST_CLASS_NAME)
            .findClassOrFail();

        Class<?> packagingDAOClass = ClassQuery
            .inExactPackage(BASE_PACKAGE + DAO_PACKAGE)
            .withExactSimpleName(PACKAGING_DAO_CLASS_NAME)
            .findClassOrFail();

        // non-null PackagingDAO member of test class
        Field packagingDAOMember = getUniqueFieldIfExists(shipmentServiceTestClass, packagingDAOClass);

        // WHEN - verify PackagingDAO member exists + extract annotations from it
        // THEN - annotations include @Mock
        assertMemberMocked(packagingDAOMember, shipmentServiceTestClass, packagingDAOClass);
    }

    @Test
    void mt7_shipmentServiceTest_doesNotUseMockForPackagingDatastore() {
        // GIVEN - ShipmentServiceTest, PackagingDatastore types
        Class<?> shipmentServiceTestClass = ClassQuery
            .inExactPackage(BASE_PACKAGE + SERVICE_PACKAGE)
            .withExactSimpleName(SHIPMENT_SERVICE_TEST_CLASS_NAME)
            .findClassOrFail();
        Class<?> packagingDatastoreClass = ClassQuery
            .inExactPackage(BASE_PACKAGE + DATASTORE_PACKAGE)
            .withExactSimpleName(DATASTORE_CLASS_NAME)
            .findClassOrFail();
        // nullable PackagingDatastore member of test class
        Field packagingDatastoreMember = getUniqueFieldIfExists(shipmentServiceTestClass, packagingDatastoreClass);

        // WHEN - verify PackagingDatastore member either doesn't exist or extract annotations from it
        // THEN - either not a member of test class or member does not include @Mock
        assertMemberMissingOrNotMocked(
            packagingDatastoreMember, shipmentServiceTestClass, packagingDatastoreClass
        );
    }

    @Test
    void mt7_shipmentServiceTest_usesInjectMockAnnotation() {
        // GIVEN - ShipmentServiceTest, ShipmentService types
        Class<?> shipmentServiceTestClass = ClassQuery
            .inExactPackage(BASE_PACKAGE + SERVICE_PACKAGE)
            .withExactSimpleName(SHIPMENT_SERVICE_TEST_CLASS_NAME)
            .findClassOrFail();

        Class<?> shipmentServiceClass = ClassQuery
            .inExactPackage(BASE_PACKAGE + SERVICE_PACKAGE)
            .withExactSimpleName(SHIPMENT_SERVICE_CLASS_NAME)
            .findClassOrFail();

        // WHEN - verify member exists
        // non-null ShipmentService member of test class
        Field shipmentServiceMember = getUniqueFieldIfExists(shipmentServiceTestClass,
            shipmentServiceClass);

        // THEN - annotations include @InjectMocks
        assertNotNull(shipmentServiceMember.getAnnotation(InjectMocks.class), String.format(
            "Expected an @InjectMocks annotation on the %s field in the ShipmentServiceTest class.",
            shipmentServiceMember.getName()));
    }

    private Field getUniqueFieldIfExists(final Class<?> containingClass, final Class<?> fieldClass) {
        Field[] fields = containingClass.getDeclaredFields();

        List<Field> matchingFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.getType().equals(fieldClass)) {
                matchingFields.add(field);
            }
        }

        if (matchingFields.size() > 1) {
            fail(String.format(
                "Unexpected found more than one member of type %s in class %s",
                fieldClass.getSimpleName(),
                containingClass.getSimpleName())
            );
        }

        return matchingFields.size() == 0 ? null : matchingFields.get(0);
    }

    private void assertMemberMissingOrNotMocked(
            final Field fieldOnTest, final Class<?> testClass, final Class<?> memberType) {

        // If there is no member variable, they can't annotate it @Mock, so hopefully it's not mocked!
        if (null == fieldOnTest) {
            return;
        }

        assertMemberMocked(fieldOnTest, testClass, memberType);
    }

}
