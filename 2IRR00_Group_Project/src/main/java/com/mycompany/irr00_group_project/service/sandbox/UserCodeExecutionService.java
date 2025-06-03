package com.mycompany.irr00_group_project.service.sandbox;

import com.mycompany.irr00_group_project.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

public class UserCodeExecutionService {

    private Process userCodeProcess;
    private Path currentTempUserCodeDirRoot;

    public Process startUserCodeProcess(Map<String, JavaClassAsBytes> compiledClasses, String sharedJarPath) throws IOException {
        JavaClassAsBytes userClassBytes = compiledClasses.get(Constants.USER_CODE_FQN);
        if (userClassBytes == null) {
            throw new IOException("Compiled class " + Constants.USER_CODE_FQN + " not found in memory.");
        }

        currentTempUserCodeDirRoot = Paths.get(Constants.TEMP_OUTPUT_BASE_DIR, UUID.randomUUID().toString());
        Path packagePath = currentTempUserCodeDirRoot.resolve(Constants.USER_CODE_PACKAGE.replace('.', File.separatorChar));
        Files.createDirectories(packagePath);

        Path classFilePath = packagePath.resolve(Constants.USER_CODE_CLASS_NAME + ".class");
        Files.write(classFilePath, userClassBytes.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        String javaExecutable = Paths.get(System.getProperty("java.home"), "bin", "java").toString();

        String runtimeClasspath = currentTempUserCodeDirRoot.toAbsolutePath().toString() +
                File.pathSeparator + sharedJarPath;

        ProcessBuilder pb = new ProcessBuilder(
                javaExecutable,
                "-cp",
                runtimeClasspath,
                "com.mycompany.irr00_group_project.service.sandbox.UserCodeRunner",
                currentTempUserCodeDirRoot.toAbsolutePath().toString(),
                Constants.USER_CODE_FQN
        );

        userCodeProcess = pb.start();
        return userCodeProcess;
    }

    public void stopCurrentProcess() {
        if (userCodeProcess != null && userCodeProcess.isAlive()) {
            userCodeProcess.destroyForcibly();
        }
    }

    public boolean isProcessAlive() {
        return userCodeProcess != null && userCodeProcess.isAlive();
    }

    public Process getProcess() {
        return userCodeProcess;
    }

    public void cleanupTemporaryFiles() {
        if (currentTempUserCodeDirRoot != null && Files.exists(currentTempUserCodeDirRoot)) {
            try {
                Files.walk(currentTempUserCodeDirRoot)
                        .sorted(java.util.Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                currentTempUserCodeDirRoot = null;
            } catch (IOException ex) {
                System.err.println("Error cleaning temp directory: " + currentTempUserCodeDirRoot + " - " + ex.getMessage());
            }
        }
    }
}
