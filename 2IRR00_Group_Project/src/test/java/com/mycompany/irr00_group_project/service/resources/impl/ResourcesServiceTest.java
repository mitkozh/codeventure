package com.mycompany.irr00_group_project.service.resources.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourcesServiceTest {

    private File tempFile;
    private String tempFilePath;

    /**
     * This method is called before each test to set up a temporary file.
     */
    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test-settings-", ".properties");
        tempFilePath = tempFile.getAbsolutePath();
    }

    /**
     * .This method is called after each test to 
     *      clean up the temporary file created during the test.
     */
    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testLoadProperties_NonExistentFile() {
        PersistenceServiceImpl persistenceService = 
            new PersistenceServiceImpl(
            "nonexistent.properties");
        Properties props = persistenceService.loadProperties();
        assertNotNull(props);
        assertTrue(props.isEmpty());
    }

    @Test
    void testLoadProperties_ExistingFile() throws IOException {
        Properties expectedProps = new Properties();
        expectedProps.setProperty("key1", "value1");
        expectedProps.setProperty("key2", "value2");
        try (var output = Files.newOutputStream(tempFile.toPath())) {
            expectedProps.store(output, "Test properties");
        }

        PersistenceServiceImpl persistenceService = new PersistenceServiceImpl(tempFilePath);
        Properties props = persistenceService.loadProperties();
        assertNotNull(props);
        assertEquals("value1", props.getProperty("key1"));
        assertEquals("value2", props.getProperty("key2"));
    }

    @Test
    void testSaveProperties() throws IOException {
        PersistenceServiceImpl persistenceService = new PersistenceServiceImpl(tempFilePath);
        Properties props = new Properties();
        props.setProperty("testKey", "testValue");
        persistenceService.saveProperties(props, "Test comment");

        Properties loadedProps = new Properties();
        try (var input = Files.newInputStream(tempFile.toPath())) {
            loadedProps.load(input);
        }
        assertEquals("testValue", loadedProps.getProperty("testKey"));
    }

    @Test
    void testConstructor_InvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PersistenceServiceImpl(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PersistenceServiceImpl("");
        });
    }

    @Test
    void testGetResolvedSharedJarFile_Success() throws IOException {
        byte[] mockJarContent = "Mock JAR content".getBytes();
        try (ByteArrayInputStream mockStream = new ByteArrayInputStream(mockJarContent)) {
            SharedJarServiceImpl jarService = new SharedJarServiceImpl() {
                public java.io.InputStream getResourceAsStream(String path) {
                    return mockStream;
                }
            };

            File jarFile = jarService.getResolvedSharedJarFile();
            assertNotNull(jarFile);
            assertTrue(jarFile.exists());
            assertTrue(jarFile.getName().endsWith(".jar"));
            assertTrue(jarFile.length() > 0);
        }
    }
}