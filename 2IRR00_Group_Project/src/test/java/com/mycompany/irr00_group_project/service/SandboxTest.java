package com.mycompany.irr00_group_project.service;

import com.mycompany.irr00_group_project.service.sandbox.InMemoryClassLoader;
import com.mycompany.irr00_group_project.service.sandbox.InMemoryFileManager;
import com.mycompany.irr00_group_project.service.sandbox.JavaClassAsBytes;
import com.mycompany.irr00_group_project.service.sandbox.JavaSourceFromString;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeCompilationService;
import com.mycompany.irr00_group_project.service.sandbox.UserCodeExecutionService;
import com.mycompany.irr00_group_project.utils.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.JavaCompiler;
import javax.tools.DiagnosticCollector;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Class for testing the sandbox functionality.
 */
public class SandboxTest {

    private UserCodeCompilationService compilationService;
    private UserCodeExecutionService executionService;
    private InMemoryFileManager fileManager;
    private StandardJavaFileManager standardFileManager;
    private JavaCompiler compiler;
    private DiagnosticCollector<JavaFileObject> diagnosticsCollector;

    @TempDir
    Path tempDir;
    
    /*
     * method for setting up tests.
     */
    @BeforeEach
    void setUp() {
        compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            fail("JDK not found. Run tests with a JDK.");
        }
        diagnosticsCollector = new DiagnosticCollector<>();
        standardFileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        fileManager = new InMemoryFileManager(standardFileManager);
        compilationService = new UserCodeCompilationService();
        executionService = new UserCodeExecutionService();
    }

    /*
     * method run after each test.
     */
    @AfterEach
    void tearDown() {
        executionService.cleanupTemporaryFiles();
        try {
            standardFileManager.close();
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    //Verifies that the UserCodeCompilationService correctly
    // handles a null input for the user code by throwing a 
    // NullPointerException.
    @Test
    void testCompilationWithNullCode() {
        String sharedJarPath = System.getProperty("java.class.path");
        assertThrows(NullPointerException.class, () -> 
            compilationService.compile(null, sharedJarPath),
                "Should throw NullPointerException for null user code");
    }

    //Tests that the InMemoryClassLoader constructor throws a 
    // NullPointerException when provided with a null InMemoryFileManager.
    @Test
    void testInMemoryClassLoaderNullManager() {
        assertThrows(NullPointerException.class, () -> 
                new InMemoryClassLoader(getClass().getClassLoader(), null),
                "Should throw NullPointerException for null file manager");
    }

    //Ensures that the JavaSourceFromString constructor 
    // throws a NullPointerException when given a null source code string.
    @Test
    void testJavaSourceFromStringNullCode() {
        String name = "TestClass";
        assertThrows(NullPointerException.class, () -> new JavaSourceFromString(name, null),
                "Should throw NullPointerException for null source code");
    }

    //Tests the InMemoryFileManager by adding two different
    // classes (UserCodeImpl and TestClass) to 
    // its bytesMap using getJavaFileForOutput.
    @Test
    void testInMemoryFileManagerMultipleClasses() {
        String className1 = Constants.USER_CODE_FQN;
        String className2 = "com.mycompany.irr00_group_project.service.sandbox.TestClass";
        JavaFileObject sibling1 = new JavaSourceFromString(className1, "dummy1");
        JavaFileObject sibling2 = new JavaSourceFromString(className2, "dummy2");

        JavaFileObject output1 = fileManager.getJavaFileForOutput(
                StandardLocation.CLASS_OUTPUT, className1, JavaFileObject.Kind.CLASS, sibling1);
        JavaFileObject output2 = fileManager.getJavaFileForOutput(
                StandardLocation.CLASS_OUTPUT, className2, JavaFileObject.Kind.CLASS, sibling2);

        assertTrue(output1 instanceof JavaClassAsBytes, 
            "First output should be JavaClassAsBytes");
        assertTrue(output2 instanceof JavaClassAsBytes, 
            "Second output should be JavaClassAsBytes");
        assertEquals(2, fileManager.getBytesMap().size(), 
            "File manager should contain two classes");
        assertTrue(fileManager.getBytesMap().containsKey(className1), 
            "Should contain first class");
        assertTrue(fileManager.getBytesMap().containsKey(className2),
            "Should contain second class");
    }

    //Checks the behavior of the JavaClassAsBytes
    // class when no data is written to its ByteArrayOutputStream.
    @Test
    void testJavaClassAsBytesEmptyData() {
        String className = "TestClass";
        JavaClassAsBytes classAsBytes = new JavaClassAsBytes(className, JavaFileObject.Kind.CLASS);

        byte[] bytes = classAsBytes.getBytes();
        assertNotNull(bytes, "Bytes should not be null");
        assertEquals(0, bytes.length, "Bytes should be empty since nothing was written");
        assertEquals(JavaFileObject.Kind.CLASS, classAsBytes.getKind(), "Kind should be CLASS");
    }
}