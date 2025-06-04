package com.mycompany.irr00_group_project.service.resources.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Properties;

import com.mycompany.irr00_group_project.service.resources.PersistenceService;

/**
 * Class for maintaining persistency of settings properties file.
 */
public class PersistenceServiceImpl implements PersistenceService {
    private String filePath = "game_settings.properties";

    public PersistenceServiceImpl(String settingsFilename) {
        //TODO Auto-generated constructor stub
    }

    /*
     * 
     */
    public void propertiesPersistenceManager(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Properties loadProperties() {
        Properties props = new Properties();
        File settingsFile = new File(filePath);

        if (settingsFile.exists()) {
            try (InputStream input = new FileInputStream(settingsFile)) {
                props.load(input);
            } catch (IOException e) {
                System.err.println("PropertiesPersistenceManager: Error"
                    + " loading settings from " + filePath + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        return props;
    }

    @Override
    public void saveProperties(Properties props, String comment) {
        try (OutputStream output = new FileOutputStream(filePath)) {
            props.store(output, comment);
        } catch (IOException e) {
            System.err.println("PropertiesPersistenceManager: Error saving"
                + " settings to " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}