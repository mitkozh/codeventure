package com.mycompany.irr00_group_project.service.resources.impl;

/**
 * Class for maintaining persistency of settings properties file.
 */
import java.io.*;
import java.util.Properties;

import com.mycompany.irr00_group_project.service.resources.PersistenceService;

public class PersistenceServiceImpl implements PersistenceService {
    private String filePath = "game_settings.properties";

    public PersistenceServiceImpl(String settingsFilename) {
        //TODO Auto-generated constructor stub
    }

    public void PropertiesPersistenceManager(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Properties loadProperties() {
        Properties props = new Properties();
        File settingsFile = new File(filePath);

        if (settingsFile.exists()) {
            try (InputStream input = new FileInputStream(settingsFile)) {
                props.load(input);
                System.out.println("PropertiesPersistenceManager: Settings loaded successfully from " + filePath);
            } catch (IOException e) {
                System.err.println("PropertiesPersistenceManager: Error loading settings from " + filePath + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("PropertiesPersistenceManager: Settings file " + filePath + " not found. Returning empty properties.");
        }
        return props;
    }

    @Override
    public void saveProperties(Properties props, String comment) {
        try (OutputStream output = new FileOutputStream(filePath)) {
            props.store(output, comment);
            System.out.println("PropertiesPersistenceManager: Settings saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("PropertiesPersistenceManager: Error saving settings to " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}