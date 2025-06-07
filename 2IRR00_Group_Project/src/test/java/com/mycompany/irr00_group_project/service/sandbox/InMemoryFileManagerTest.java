package com.mycompany.irr00_group_project.service.sandbox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the InMemoryFileManager class.
 */
public class InMemoryFileManagerTest {

    private InMemoryFileManager fileManager;
    private StandardJavaFileManager standardFileManager;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("No JDK found. Run tests with a JDK.");
        }
        standardFileManager = compiler.getStandardFileManager(null, null, null);
        fileManager = new InMemoryFileManager(standardFileManager);
    }

    @Test
    void testGetJavaFileForOutput() {
        String className = "com.mycompany.irr00_group_project.service.sandbox.TestClass";
        JavaFileObject.Kind kind = JavaFileObject.Kind.CLASS;
        JavaFileObject result = fileManager.getJavaFileForOutput(null, className, kind, null);
        assertNotNull(result);
        assertTrue(result instanceof JavaClassAsBytes);
        Map<String, JavaClassAsBytes> compiledClasses = fileManager.getBytesMap();
        assertTrue(compiledClasses.containsKey(className));
        assertEquals(result, compiledClasses.get(className));
    }

    @Test
    void testGetClassLoader() {
        ClassLoader loader = fileManager.getClassLoader(null);
        assertNotNull(loader);
        assertTrue(loader instanceof InMemoryClassLoader);
    }

    @Test
    void testGetBytesMap() {
        Map<String, JavaClassAsBytes> compiledClasses = fileManager.getBytesMap();
        assertNotNull(compiledClasses);
        assertTrue(compiledClasses.isEmpty());
        fileManager.getJavaFileForOutput(null, "TestClass", JavaFileObject.Kind.CLASS, null);
        assertFalse(compiledClasses.isEmpty());
        assertTrue(compiledClasses.containsKey("TestClass"));
    }
}