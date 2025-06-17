package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleObservablesTest {

    private ConsoleObservables consoleObservables;

    @BeforeEach
    void setUp() {
        consoleObservables = new ConsoleObservables();
    }

    @Test
    void testAddMessageUpdatesListAndProperty() {
        String message = "Test message";
        consoleObservables.addMessage(message);

        ObservableList<String> messages = consoleObservables.getMessages();
        StringProperty lastMessage = consoleObservables.lastMessageProperty();

        assertEquals(1, messages.size(),
            "Messages list should contain one message");
        assertEquals(message, messages.get(0),
            "Messages list should contain the added message");
        assertEquals(message, lastMessage.get(),
            "Last message property should match the added message");
    }

    @Test
    void testAddErrorUpdatesListAndProperty() {
        String error = "Test error";
        consoleObservables.addError(error);

        ObservableList<String> errors = consoleObservables.getErrors();
        StringProperty lastError = consoleObservables.lastErrorProperty();

        assertEquals(1, errors.size(), "Errors list should contain one error");
        assertEquals(error, errors.get(0), "Errors list should contain the added error");
        assertEquals(error, lastError.get(), "Last error property should match the added error");
    }

    @Test
    void testMultipleMessagesMaintainsOrder() {
        String message1 = "First message";
        String message2 = "Second message";
        consoleObservables.addMessage(message1);
        consoleObservables.addMessage(message2);

        ObservableList<String> messages = consoleObservables.getMessages();
        assertEquals(2, messages.size(),
            "Messages list should contain two messages");
        assertEquals(message1, messages.get(0),
            "First message should be first in list");
        assertEquals(message2, messages.get(1),
            "Second message should be second in list");
        assertEquals(message2, consoleObservables.lastMessageProperty().get(),
            "Last message should be the most recent");
    }

    @Test
    void testInitialState() {
        assertEquals("", consoleObservables.lastMessageProperty().get(),
            "Initial last message should be empty");
        assertEquals("", consoleObservables.lastErrorProperty().get(),
            "Initial last error should be empty");
        assertTrue(consoleObservables.getMessages().isEmpty(),
            "Initial messages list should be empty");
        assertTrue(consoleObservables.getErrors().isEmpty(), "Initial errors list should be empty");
    }
}