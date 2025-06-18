package com.mycompany.irr00_group_project.service.resources.impl;

import com.mycompany.irr00_group_project.service.resources.SharedJarService;
import com.mycompany.irr00_group_project.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Class used to find and open shared jar file.
 */
public class SharedJarServiceImpl implements SharedJarService {
    private File resolvedSharedJarFile;

    /*
     * Default constructor to initiallize the SharedJarServiceImpl without parameters
     */
    public SharedJarServiceImpl() {}
    
    /**
     * Method to get the Resolved shared jar file.
     */
    @Override
    public File getResolvedSharedJarFile() throws IOException {
        if (resolvedSharedJarFile != null && resolvedSharedJarFile.exists()) {
            return resolvedSharedJarFile;
        }

        InputStream jarStream = getClass().getResourceAsStream(Constants.SHARED_JAR_PATH);
        if (jarStream == null) {
            throw new IOException("Cannot find shared.jar resource at: "
                + Constants.SHARED_JAR_PATH);
        }

        Path tempJarFile = Files.createTempFile("shared-", ".jar");
        Files.copy(jarStream, tempJarFile, StandardCopyOption.REPLACE_EXISTING);
        jarStream.close();

        resolvedSharedJarFile = tempJarFile.toFile();
        resolvedSharedJarFile.deleteOnExit();
        return resolvedSharedJarFile;
    }

}
