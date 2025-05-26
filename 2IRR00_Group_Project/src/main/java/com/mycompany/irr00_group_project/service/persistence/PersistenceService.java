package com.mycompany.irr00_group_project.service.persistence;

import com.mycompany.irr00_group_project.model.core.GameState;

import java.util.Properties;
/**
 * Interface for maintaining persistency of settings.
 */
public interface PersistenceService {
    Properties loadProperties();
    void saveProperties(Properties props, String comment);
}
