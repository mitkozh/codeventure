package com.mycompany.irr00_group_project.model.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.service.sandbox.JavaClassAsBytes;

/**
 * Test class for the CompilationResult Class.
 * Verifies the behavior of the compilation result data container.
 */
class CompilationResultTest {

    /**
     * Tests that constructor properly initializes all fields.
     */
    @Test
    void testConstructorInitialization() {
        Map<String, JavaClassAsBytes> testClasses = Collections.singletonMap(
            "TestClass", new JavaClassAsBytes("TestClass", Kind.CLASS));
        List<Diagnostic<? extends JavaFileObject>> testDiagnostics = Collections.emptyList();
        String testFormattedDiags = "No errors";
        
        CompilationResult result = new CompilationResult(
            true, testClasses, testDiagnostics, testFormattedDiags);
        
        assertTrue(result.isSuccess(), "Success flag should be true");
        assertEquals(testClasses, result.getCompiledClasses(), 
            "Compiled classes should match input");
        assertEquals(testDiagnostics, result.getDiagnosticsList(),
            "Diagnostics list should match input");
        assertEquals(testFormattedDiags, result.getFormattedDiagnostics(),
            "Formatted diagnostics should match input");
    }

    /**
     * Tests behavior when compiled with failures (success = false).
     */
    @Test
    void testFailedCompilation() {
        List<Diagnostic<? extends JavaFileObject>> testDiagnostics = Collections.emptyList();
        String testFormattedDiags = "Compilation failed";
        
        CompilationResult result = new CompilationResult(
            false, Collections.emptyMap(), testDiagnostics, testFormattedDiags);
        
        assertFalse(result.isSuccess(), "Success flag should be false");
    }

    /**
     * Tests behavior with empty compiled classes map.
     */
    @Test
    void testEmptyCompiledClasses() {
        CompilationResult result = new CompilationResult(
            true, Collections.emptyMap(), Collections.emptyList(), "");
        
        assertTrue(result.getCompiledClasses().isEmpty(),
            "Compiled classes map should be empty");
    }

    /**
     * Tests that getCompiledClasses returns the exact same map instance passed to the constructor.
     */
    @Test
    void testGetCompiledClassesReturnsSameInstance() {
        Map<String, JavaClassAsBytes> testClasses = Collections.singletonMap(
            "Sample", new JavaClassAsBytes("Sample", Kind.CLASS));
        CompilationResult result = new CompilationResult(
            true, testClasses, Collections.emptyList(), "OK");
        assertEquals(testClasses, result.getCompiledClasses(),
            "getCompiledClasses should return the same map instance as provided");
    }

    /**
     * Tests that getDiagnosticsList returns the exact same list instance passed to the constructor.
     */
    @Test
    void testGetDiagnosticsListReturnsSameInstance() {
        List<Diagnostic<? extends JavaFileObject>> diagnostics = Collections.emptyList();
        CompilationResult result = new CompilationResult(
            true, Collections.emptyMap(), diagnostics, "OK");
        assertEquals(diagnostics, result.getDiagnosticsList(),
            "getDiagnosticsList should return the same list instance as provided");
    }

    /**
     * Tests that getFormattedDiagnostics returns the exact string passed to the constructor.
     */
    @Test
    void testGetFormattedDiagnosticsReturnsSameString() {
        String formatted = "Some diagnostics";
        CompilationResult result = new CompilationResult(
            true, Collections.emptyMap(), Collections.emptyList(), formatted);
        assertEquals(formatted, result.getFormattedDiagnostics(),
            "getFormattedDiagnostics should return the same string as provided");
    }

    /**
     * Tests that CompilationResult works with multiple compiled classes.
     */
    @Test
    void testMultipleCompiledClasses() {
        Map<String, JavaClassAsBytes> classes = Map.of(
            "A", new JavaClassAsBytes("A", Kind.CLASS),
            "B", new JavaClassAsBytes("B", Kind.CLASS)
        );
        CompilationResult result = new CompilationResult(
            true, classes, Collections.emptyList(), "All good");
        assertEquals(2, result.getCompiledClasses().size(),
            "Should contain two compiled classes");
        assertTrue(result.getCompiledClasses().containsKey("A"));
        assertTrue(result.getCompiledClasses().containsKey("B"));
    }

    /**
     * Tests behavior with null diagnostics list (should be allowed).
     */
    @Test
    void testNullDiagnosticsList() {
        CompilationResult result = new CompilationResult(
            true, Collections.emptyMap(), null, "Null diagnostics");
        
        assertNull(result.getDiagnosticsList(),
            "Diagnostics list should be null");
    }

    /**
     * Tests behavior with null formatted diagnostics (should be allowed).
     */
    @Test
    void testNullFormattedDiagnostics() {
        CompilationResult result = new CompilationResult(
            true, Collections.emptyMap(), Collections.emptyList(), null);
        
        assertNull(result.getFormattedDiagnostics(),
            "Formatted diagnostics should be null");
    }

    /**
     * Tests behavior with empty formatted diagnostics string.
     */
    @Test
    void testEmptyFormattedDiagnostics() {
        CompilationResult result = new CompilationResult(
            true, Collections.emptyMap(), Collections.emptyList(), "");
        
        assertEquals("", result.getFormattedDiagnostics(),
            "Formatted diagnostics should be empty string");
    }
}