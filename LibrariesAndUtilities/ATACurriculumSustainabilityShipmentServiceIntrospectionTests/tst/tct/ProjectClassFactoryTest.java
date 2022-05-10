package tct;

import com.amazon.ata.test.reflect.NoClassFoundException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProjectClassFactoryTest {

    @Test
    void getClass_classExists_returnsClass() {
        // WHEN
        Class<?> clazz = ProjectClassFactory.getClass("tct", "ProjectClassFactory");

        // THEN
        Class<ProjectClassFactory> correctClass = (Class<ProjectClassFactory>) clazz;
        assertNotNull(correctClass, "Could not find tct.ProjectClassFactory class.");
    }

    @Test
    void getClass_withSpecifiedParentClass_returnsClass() {
        // WHEN
        Class<?> clazz = ProjectClassFactory.getClass("tct", "AnEnum", Enum.class);

        // THEN
        Class<AnEnum> correctClass = (Class<AnEnum>) clazz;
        assertNotNull(correctClass, "Could not find tct.AnEnum class.");
    }

    @Test
    void getClass_classDoesntExist_assertFires() {
        // WHEN + THEN
        assertThrows(NoClassFoundException.class,
            () -> ProjectClassFactory.getClass("tct.types", "ProjectClassFactory"));
    }

    @Test
    void getClass_nestedClass_assertFires() {
        // WHEN + THEN
        assertThrows(NoClassFoundException.class,
            () -> ProjectClassFactory.getClass("tct", "ProjectClassFactoryTest$NestedClass"));
    }

    @Test
    void getClass_extendsExternalClassOtherThanObject_assertFires() {
        // WHEN + THEN
        assertThrows(NoClassFoundException.class,
            () -> ProjectClassFactory.getClass("tct", "AnEnum"));
    }

    @Test
    void findClass_classExists_returnsClass() {
        // WHEN
        Class<?> clazz = ProjectClassFactory.findClass("ProjectClassFactory");

        // THEN
        Class<ProjectClassFactory> correctClass = (Class<ProjectClassFactory>) clazz;
        assertNotNull(correctClass, "Could not find tct.ProjectClassFactory class.");
    }

    @Test
    void findClass_classDoesNotExist_assertFires() {
        // WHEN + THEN
        assertThrows(NoClassFoundException.class, () -> ProjectClassFactory.findClass("AllMaybeTests"));
    }

    @Test
    void findClass_nestedClass_assertFires() {
        // WHEN + THEN
        assertThrows(NoClassFoundException.class, () -> ProjectClassFactory.findClass("ProjectClassFactoryTest$NestedClass"));
    }

    @Test
    void findNestedClass_nestedClass_returnsClass() {
        // WHEN
        Class<?> clazz = ProjectClassFactory.findNestedClass("tct",
            "NestedClass",
            this.getClass().getSimpleName());

        // THEN
        Class<NestedClass> correctClass = (Class<NestedClass>) clazz;
        assertNotNull(correctClass, "Could not find tct.ProjectClassFactory$NestedClass class.");
    }

    public static class NestedClass { }

    public enum AnEnum { }
}
