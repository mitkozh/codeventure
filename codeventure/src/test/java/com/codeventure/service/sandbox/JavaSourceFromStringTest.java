package com.codeventure.service.sandbox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the JavaSourceFromString class.
 */
public class JavaSourceFromStringTest {

    @Test
    void testConstructorAndGetCharContent() {
        String name = "com.codeventure.service.sandbox.TestClass";
        String sourceCode = "public class TestClass {}";
        JavaSourceFromString source = new JavaSourceFromString(name, sourceCode);
        assertNotNull(source);
        assertEquals(sourceCode, source.getCharContent(true));
        assertEquals(sourceCode, source.getCharContent(false));
    }

    @Test
    void testConstructorNullSourceCode() {
        String name = "com.codeventure.service.sandbox.TestClass";
        assertThrows(NullPointerException.class, () -> new JavaSourceFromString(name, null));
    }
}