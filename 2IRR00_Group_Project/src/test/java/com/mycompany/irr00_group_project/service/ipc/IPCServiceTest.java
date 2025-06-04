package com.mycompany.irr00_group_project.service.ipc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for tests for IPCService.
 */
public class IPCServiceTest {

    private IPCService ipcService;
    private List<String> outputMessages;
    private List<String> errorMessages;

    /**
     * Set-up method implemented to be used before each test.
     */
    @BeforeEach
    public void setUp() {
        ipcService = new IPCService();
        outputMessages = new ArrayList<>();
        errorMessages = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        ipcService.stopListeners();
    }

    @Test
    public void testReceivesSingleOutputLine() throws Exception {
        // Setup
        String testOutput = "test output line\n";
        FakeProcess process = new FakeProcess(testOutput, "");

        // Execute
        ipcService.startIPCListeners(process, outputMessages::add, errorMessages::add);
        waitForProcessing();

        // Verify
        assertEquals(1, outputMessages.size());
        assertEquals("test output line", outputMessages.get(0));
        assertTrue(errorMessages.isEmpty());
    }

    @Test
    public void testReceivesMultipleOutputLines() throws Exception {
        // Setup
        String testOutput = "line1\nline2\nline3\n";
        FakeProcess process = new FakeProcess(testOutput, "");

        // Execute
        ipcService.startIPCListeners(process, outputMessages::add, errorMessages::add);
        waitForProcessing();

        // Verify
        assertEquals(3, outputMessages.size());
        assertEquals("line1", outputMessages.get(0));
        assertEquals("line2", outputMessages.get(1));
        assertEquals("line3", outputMessages.get(2));
    }

    @Test
    public void testReceivesErrorOutput() throws Exception {
        // Setup
        String testError = "error message\n";
        FakeProcess process = new FakeProcess("", testError);

        // Execute
        ipcService.startIPCListeners(process, outputMessages::add, errorMessages::add);
        waitForProcessing();

        // Verify
        assertEquals(1, errorMessages.size());
        assertEquals("UserProcess stderr: error message", errorMessages.get(0));
        assertTrue(outputMessages.isEmpty());
    }

    private void waitForProcessing() {
        try {
            Thread.sleep(50); // Short delay to allow processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Simple fake process implementation
    private static class FakeProcess extends Process {
        private final InputStream stdout;
        private final InputStream stderr;

        public FakeProcess(String stdoutContent, String stderrContent) {
            this.stdout = new ByteArrayInputStream(stdoutContent.getBytes());
            this.stderr = new ByteArrayInputStream(stderrContent.getBytes());
        }

        @Override public InputStream getInputStream() { 
            return stdout; 
        }

        @Override public InputStream getErrorStream() { 
            return stderr; 
        }

        @Override public OutputStream getOutputStream() { 
            return null; 
        }

        @Override public int waitFor() { 
            return 0; 
        }

        @Override public int exitValue() { 
            return 0; 
        }

        @Override public void destroy() {}
    }

    // Process that blocks on read
    private static class BlockingProcess extends Process {
        private final InputStream blockingStream = new InputStream() {
            @Override
            public int read() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return -1;
                    }
                }
            }
        };

        @Override public InputStream getInputStream() { 
            return blockingStream; 
        }
        
        @Override public InputStream getErrorStream() { 
            return blockingStream; 
        }

        @Override public OutputStream getOutputStream() { 
            return null; 
        }

        @Override public int waitFor() { 
            return 0; 
        }

        @Override public int exitValue() { 
            return 0; 
        }
        
        @Override public void destroy() {}
    }
}