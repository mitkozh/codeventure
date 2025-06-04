package com.mycompany.irr00_group_project.service.ipc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Class which implements IPC service, to run multiple processes and share data.
 */
public class IPCService {

    private Thread outputListenerThread;
    private Thread errorListenerThread;
    private volatile boolean running = false;

    /**
     * Method to start the IPC Listeners.
     */
    public void startIPCListeners(Process process, 
        Consumer<String> onOutput, Consumer<String> onError) {
        running = true;
        outputListenerThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
                String line;
                while (running && (line = reader.readLine()) != null) {
                    onOutput.accept(line);
                }
            } catch (IOException e) {
                if (running) {
                    onError.accept("IPC Error (stdout): " + e.getMessage());
                }
            }
        });
        outputListenerThread.setDaemon(true);
        outputListenerThread.start();

        errorListenerThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()))) {
                String line;
                while (running && (line = reader.readLine()) != null) {
                    onError.accept("UserProcess stderr: " + line);
                }
            } catch (IOException e) {
                if (running) {
                    onError.accept("IPC Error (stderr): " + e.getMessage());
                }
            }
        });
        errorListenerThread.setDaemon(true);
        errorListenerThread.start();
    }
    
    /**
     * Method to stop the IPC Listeners.
     */
    public void stopListeners() {
        running = false;
        if (outputListenerThread != null && outputListenerThread.isAlive()) {
            outputListenerThread.interrupt();
        }
        if (errorListenerThread != null && errorListenerThread.isAlive()) {
            errorListenerThread.interrupt();
        }
    }
}
