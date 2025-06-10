package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ConsoleObservables provides observable properties for console messages and errors.
 * It allows other components to observe and react to changes in console output.
 */
public class ConsoleObservables {
    private final StringProperty lastMessage = new SimpleStringProperty("");
    private final StringProperty lastError = new SimpleStringProperty("");
    private final ObservableList<String> messages = FXCollections.observableArrayList();
    private final ObservableList<String> errors = FXCollections.observableArrayList();

    public StringProperty lastMessageProperty() {
        return lastMessage;
    }

    public StringProperty lastErrorProperty() {
        return lastError;
    }

    public ObservableList<String> getMessages() {
        return messages;
    }

    public ObservableList<String> getErrors() {
        return errors;
    }

    /**
     * Adds a message to the observable list and
     * updates the lastMessage property.
     *
     * @param message The message to add.
     */
    public void addMessage(String message) {
        messages.add(message);
        lastMessage.set(message);
    }

    /**
     * Adds an error message to the observable
     * list and updates the lastError property.
     *
     * @param error The error message to add.
     */
    public void addError(String error) {
        errors.add(error);
        lastError.set(error);
    }
}