package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.WinScreenController;
import com.mycompany.irr00_group_project.utils.Constants;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * This class represents the Win Screen of the game shown when a player successfully completes a level.
 * This screen includes a congrats message, star ratings based on performance,
 * and provides navigation options to restart the level, go to the next level, or return to menus.
 * @see AbstractScreen
 * @see WinScreenController
 */
public class WinScreen extends AbstractScreen {

    private WinScreenController controller;

    /**
     * Creates and initializes the screen content.
     * @return the root Parent node containing all screen elements
     * @see #createUI() for the actual UI construction
     */
    @Override
    protected Parent createContent() {
        controller = new WinScreenController();
        controller.initialize(); 
        Parent ui = createUI();
        return ui;
    }

    /**
     * Constructs the complete user interface for the win screen.
     * 
     * @return the root node of the constructed UI
     */
    private Parent createUI() {
        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("popup-bg");

        VBox mainPanel = new VBox();
        mainPanel.setAlignment(Pos.CENTER);
        mainPanel.setSpacing(30);
        mainPanel.getStyleClass().add("popup-panel");

        Label congratsLabel = new Label("CONGRATS!");
        congratsLabel.getStyleClass().add("popup-title-win");

        Label youWinLabel = new Label("You Win!");
        youWinLabel.getStyleClass().add("popup-title");

        HBox starsContainer = new HBox();
        starsContainer.setSpacing(10);
        starsContainer.setAlignment(Pos.CENTER);
        controller.setStarsContainer(starsContainer);
        controller.updateStarsDisplay();

        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);

        if (controller.hasNextLevel(Constants.MAX_LEVEL)) {
            Button nextLevelButton = new Button("Next Level");
            nextLevelButton.getStyleClass().add("popup-button");
            nextLevelButton.setOnAction(controller::handleNextLevelButtonAction);
            buttonContainer.getChildren().add(nextLevelButton);
        }

        Button restartButton = new Button("Restart");
        restartButton.getStyleClass().add("popup-button");
        restartButton.setOnAction(controller::handleRestartButtonAction);
        controller.setRestartButton(restartButton);

        Button levelSelectButton = new Button("Level Selection");
        levelSelectButton.getStyleClass().add("popup-button");
        levelSelectButton.setOnAction(controller::handleLevelSelectionButtonAction);
        buttonContainer.getChildren().addAll(restartButton, levelSelectButton);
        mainPanel.getChildren().addAll(congratsLabel, youWinLabel, starsContainer, buttonContainer);
        rootPane.getChildren().add(mainPanel);

        return rootPane;
    }

    /**
     * Gets the path to the CSS stylesheet for this screen.
     * @return the absolute path to the CSS resource file
     */
    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/winScreenStyle.css";
    }
}