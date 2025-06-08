package com.mycompany.irr00_group_project.service.observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * SettingsObservables provides observable properties for game settings.
 * It allows other components to observe and react to changes in settings.
 */
public class SettingsObservables {
    private final StringProperty selectedAvatar = new SimpleStringProperty("");

    public StringProperty selectedAvatarProperty() {
        return selectedAvatar;
    }

    public void setSelectedAvatar(String avatar) {
        selectedAvatar.set(avatar);
    }

    public String getSelectedAvatar() {
        return selectedAvatar.get();
    }

}