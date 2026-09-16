package com.codeventure.service.observable;

import com.codeventure.model.core.dto.LevelDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * LevelSelectionObservables provides observable properties for level selection events.
 * It allows other components to observe and react to level selection changes.
 */
public class LevelSelectionObservables {
    private final ObjectProperty<LevelDTO> selectedLevel = new SimpleObjectProperty<>();

    public ObjectProperty<LevelDTO> selectedLevelProperty() {
        return selectedLevel;
    }

    public void setSelectedLevel(LevelDTO level) {
        selectedLevel.set(level);
    }

    public LevelDTO getSelectedLevel() {
        return selectedLevel.get();
    }
}