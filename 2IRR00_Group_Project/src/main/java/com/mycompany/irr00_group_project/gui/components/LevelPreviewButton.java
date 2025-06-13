package com.mycompany.irr00_group_project.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * A button that represents a level preview in the level selection screen.
 * It displays the level number and the number of stars earned.
 */
public class LevelPreviewButton extends Button {

    private int levelNumber;
    private int stars;
    private boolean unlocked;
    private Label levelNumberLabel;
    private Label starLabel;
    private VBox content;

    /**
     * Constructor for the LevelPreviewButton.
     * Initializes the button with default values and add styles.
     */
    public LevelPreviewButton() {
        this.stars = 0;
        this.unlocked = false;
        this.getStyleClass().add("level-preview-button");
        this.getStyleClass().add("custom-button");
        initialize();
    }

    private void initialize() {
        levelNumberLabel = new Label();
        levelNumberLabel.getStyleClass().add("level-number-label");
        levelNumberLabel.setTextAlignment(TextAlignment.CENTER);
        starLabel = new Label();
        starLabel.getStyleClass().add("star-label");
        updateStarLabel();
        starLabel.setTextAlignment(TextAlignment.CENTER);

        content = new VBox(levelNumberLabel, starLabel);
        content.setAlignment(Pos.CENTER);
        content.setSpacing(5);

        this.setGraphic(content);
    }

    private void updateStarLabel() {
        starLabel.setText("★".repeat(Math.max(0, stars)));
    }

    /**
     * Sets the current style of the button based on the unlocked state.
     * @param unlocked true if the level is unlocked, false otherwise
     */
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        this.setDisable(!unlocked);

        if (unlocked) {
            this.getStyleClass().remove("locked");
        } else {
            this.getStyleClass().add("locked");
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Sets the level number and updates the label.
     * @param levelNumber The level number to set.
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
        levelNumberLabel.setText(String.valueOf(levelNumber));
    }

    public int getStars() {
        return stars;
    }

    /**
     * Sets the number of stars earned for the level.
     * @param stars The number of stars to set (0-3).
     */
    public void setStars(int stars) {
        // just to be sure
        if (stars < 0) {
            stars = 0;
        } else if (stars > 3) {
            stars = 3;
        }
        this.stars = stars;
        updateStarLabel();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

}
