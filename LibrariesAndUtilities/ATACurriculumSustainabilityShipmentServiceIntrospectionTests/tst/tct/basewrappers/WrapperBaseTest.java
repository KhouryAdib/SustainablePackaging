package tct.basewrappers;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WrapperBaseTest {

    class ExampleWrapper extends WrapperBase {
        public ExampleWrapper() {
            super(new Example());
        }

        @Override
        public Class<?> getWrappedClass() {
            return Example.class;
        }
    }

    class Example {
        public void voidReturnMethod(String string) {}
        public String stringReturningMethod() { return null; }
        public String stringReturningMethod(int integer) { return null; }
        public String duplicate(int integer) { return null; }
    }

    @Test
    public void getMethod_voidReturnMethod_returnsMethod() throws NoSuchMethodException {
        // GIVEN
        ExampleWrapper exampleWrapper = new ExampleWrapper();

        // WHEN
        Method foundMethod = exampleWrapper.getMethod(Example.class, Void.TYPE, String.class);

        // THEN
        assertEquals(Example.class.getMethod("voidReturnMethod", String.class), foundMethod);
    }

    @Test
    public void getMethod_methodWithNoParameters_returnsMethod() throws NoSuchMethodException {
        // GIVEN
        ExampleWrapper exampleWrapper = new ExampleWrapper();

        // WHEN
        Method foundMethod = exampleWrapper.getMethod(Example.class, String.class);

        // THEN
        assertEquals(Example.class.getMethod("stringReturningMethod"), foundMethod);
    }

    @Test
    public void getMethod_multipleMethodsWithSignatureExist_assertFires() {
        // GIVEN
        ExampleWrapper exampleWrapper = new ExampleWrapper();

        // WHEN + THEN
        assertThrows(AssertionError.class, () -> exampleWrapper.getMethod(Example.class, String.class, Integer.class));
    }

    @Test
    public void getMethod_noMethodWithSignatureExists_assertFires() {
        // GIVEN
        ExampleWrapper exampleWrapper = new ExampleWrapper();

        // WHEN + THEN
        assertThrows(AssertionError.class, () -> exampleWrapper.getMethod(Example.class, String.class, String.class));
    }
}