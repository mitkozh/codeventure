package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.LossScreenController;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * LossScreen is a class that represents the game-over screen displayed 
 * when a player fails to complete a level.
 * This screen includes messages about the loss 
 * and navigation options to retry the level or return to the level selection menu.
 * 
 * @see AbstractScreen
 * @see LossScreenController
 * @see WinScreen for the corresponding victory screen
 */
public class LossScreen extends AbstractScreen {

    private LossScreenController controller;

    /**
     * Creates and initializes the screen content by setting up the controller
     * and constructing the UI components.
     * 
     * @return the root Parent node containing all screen elements
     * @see #createUI() for the actual UI construction
     */
    @Override
    protected Parent createContent() {
        controller = new LossScreenController();
        controller.initialize();
        return createUI();
    }

    /**
     * Constructs the complete user interface for the loss screen 
     * displayed when a player loses a level.
     * Includes a title and subtitle
     * and action buttons to navigate to other pages
     * such as a restart and a level selection button.
     * 
     * @return the root StackPane containing all UI elements
     */
    private Parent createUI() {
        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("popup-bg");

        VBox mainPanel = new VBox();
        mainPanel.setAlignment(Pos.CENTER);
        mainPanel.setSpacing(30);
        mainPanel.getStyleClass().add("popup-panel");
        Label titleLabel = new Label("You Lost!");
        titleLabel.getStyleClass().add("popup-title");

        Label subtitleLabel = new Label("Better luck next time!");
        subtitleLabel.getStyleClass().add("popup-subtitle");

        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);

        Button restartButton = new Button("Restart");
        restartButton.getStyleClass().add("popup-button");
        restartButton.setOnAction(controller::handleGoBack);

        Button levelSelectButton = new Button("Level Selection");
        levelSelectButton.getStyleClass().add("popup-button");
        levelSelectButton.setOnAction(controller::handleLevelSelectionButton);

        buttonContainer.getChildren().addAll(restartButton, levelSelectButton);
        mainPanel.getChildren().addAll(titleLabel, subtitleLabel, buttonContainer);
        rootPane.getChildren().add(mainPanel);

        return rootPane;
    }

    /**
     * Gets the path to the CSS stylesheet for the loss screen.
     * @return the absolute path to the CSS file for this screen's style.
     */
    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/lossScreenStyle.css";
    }
}