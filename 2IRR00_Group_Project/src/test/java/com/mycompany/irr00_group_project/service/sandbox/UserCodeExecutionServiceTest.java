package com.mycompany.irr00_group_project.service.sandbox;

import com.mycompany.irr00_group_project.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaFileObject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserCodeExecutionService class.
 */
public class UserCodeExecutionServiceTest {

    private UserCodeExecutionService service;
    private Map<String, JavaClassAsBytes> compiledClasses;
    private String sharedJarPath;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp(@TempDir File tempDir) {
        service = new UserCodeExecutionService();
        compiledClasses = new HashMap<>();
        sharedJarPath = tempDir.getAbsolutePath();
    }

    @Test
    void testStartUserCodeProcessSuccess() throws IOException {
        JavaClassAsBytes classAsBytes = new JavaClassAsBytes(Constants.USER_CODE_FQN, 
            JavaFileObject.Kind.CLASS);
        compiledClasses.put(Constants.USER_CODE_FQN, classAsBytes);
        Process process = service.startUserCodeProcess(compiledClasses, sharedJarPath);
        assertNotNull(process);
        assertTrue(service.isProcessAlive());
        assertEquals(process, service.getProcess());
        service.stopCurrentProcess();
    }

    @Test
    void testStartUserCodeProcessClassNotFound() {
        assertThrows(IOException.class, () -> 
            service.startUserCodeProcess(compiledClasses, sharedJarPath));
    }

    @Test
    void testStopCurrentProcess() throws IOException {
        JavaClassAsBytes classAsBytes = new JavaClassAsBytes(Constants.USER_CODE_FQN, 
            JavaFileObject.Kind.CLASS);
        compiledClasses.put(Constants.USER_CODE_FQN, classAsBytes);
        Process process = service.startUserCodeProcess(compiledClasses, sharedJarPath);
        assertTrue(service.isProcessAlive());
        service.stopCurrentProcess();
        assertFalse(service.isProcessAlive());
    }

    @Test
    void testIsProcessAliveNoProcess() {
        assertFalse(service.isProcessAlive());
    }
}