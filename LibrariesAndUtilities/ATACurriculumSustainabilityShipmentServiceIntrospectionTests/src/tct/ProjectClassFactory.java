package tct;

import com.amazon.ata.test.reflect.ClassQuery;
import com.amazon.ata.test.reflect.NoClassFoundException;

/**
 * Finds classes within the project and can instantiate them.
 */
public class ProjectClassFactory {
    private static final String PACKAGE = "com.amazon.ata.";

    private ProjectClassFactory() {}

    /**
     * Finds a class within the project.
     *
     * @param localClassPath The class path following {@code PACKAGE}
     * @param simpleName The name of the class
     * @return The Class corresponding to the package and class name.
     */
    public static Class<?> getClass(String localClassPath, String simpleName) {
        return ClassQuery.inExactPackage(PACKAGE + localClassPath).withExactSimpleName(simpleName).findClass();
    }

    /**
     * Finds a class within the project specifying the super type.
     *
     * @param localClassPath The class path following {@code PACKAGE}
     * @param simpleName The name of the class
     * @param parentClass Override the parent type (defaults to Object)
     * @return The Class corresponding to the package and class name.
     */
    public static Class<?> getClass(String localClassPath, String simpleName, Class<?> parentClass) {
        return ClassQuery.inExactPackage(PACKAGE + localClassPath)
            .withExactSimpleName(simpleName)
            .withSubTypeOf(parentClass)
            .findClass();
    }

    /**
     * Finds a class within the project based on just its simple name (not requiring the full path).
     *
     * @param simpleName The simple name for the class
     * @return The Class corresponding to the string.
     */
    public static Class<?> findClass(String simpleName) {
        return ClassQuery.inContainingPackage(PACKAGE).withExactSimpleName(simpleName).findClass();
    }

    /**
     * Find a nested class by searching for all classes with name {nestedClass}
     * and filtering by the class it's nested in.
     *
     * @param localClassPath The class path following {@code PACKAGE}
     * @param nestedClassSimpleName the class we are searching for
     * @param nestClassSimpleName the class the {nestedClass} is expected to be nested in
     * @return the Class corresponding to the nested builder
     */
    public static Class<?> findNestedClass(String localClassPath, String nestedClassSimpleName,
                                           String nestClassSimpleName) {
        ClassQuery query = ClassQuery.inContainingPackage(PACKAGE + localClassPath)
            .withSimpleNameContaining(nestedClassSimpleName);
        return query.findClasses()
            .stream()
            .filter(c -> c.getCanonicalName().contains(nestClassSimpleName))
            .findFirst()
            .orElseThrow(() -> new NoClassFoundException(query));
    }
}
