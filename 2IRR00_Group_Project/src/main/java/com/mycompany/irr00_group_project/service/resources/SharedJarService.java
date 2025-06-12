package com.mycompany.irr00_group_project.service.resources;

import java.io.File;
import java.io.IOException;

/**
 * Service interface for managing the shared JAR file.
 * This service provides a method to retrieve the resolved shared JAR file.
 */
public interface SharedJarService {
    File getResolvedSharedJarFile() throws IOException;
}
