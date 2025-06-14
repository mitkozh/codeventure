package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * NavigationObservables provides observable properties for navigation events.
 */
public class NavigationObservables {
    private final BooleanProperty returnedToGame = new SimpleBooleanProperty(false);
    private final StringProperty lastNavigatedTo = new SimpleStringProperty("");

    public BooleanProperty returnedToGameProperty() {
        return returnedToGame;
    }

    public StringProperty lastNavigatedToProperty() {
        return lastNavigatedTo;
    }

    public void setReturnedToGame(boolean returned) {
        returnedToGame.set(returned);
    }

    public void setLastNavigatedTo(String destination) {
        lastNavigatedTo.set(destination);
    }

    public void clearReturnedToGame() {
        returnedToGame.set(false);
    }
}