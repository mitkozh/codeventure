package com.codeventure.service.resources;

import java.util.Properties;
/**
 * Interface for maintaining persistency of settings.
 */

public interface PersistenceService {
    Properties loadProperties();
    
    void saveProperties(Properties props, String comment);
}
