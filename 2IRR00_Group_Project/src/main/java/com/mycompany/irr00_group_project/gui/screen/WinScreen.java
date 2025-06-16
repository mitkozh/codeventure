package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.WinScreenController;
import com.mycompany.irr00_group_project.utils.Constants;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * This class represents the Win Screen of the game.
 */
public class WinScreen extends AbstractScreen {

    private WinScreenController controller;

    @Override
    protected Parent createContent() {
        controller = new WinScreenController();
        controller.initialize(); 
        Parent ui = createUI();
        return ui;
    }

    private Parent createUI() {
        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("popup-bg");

        VBox mainPanel = new VBox();
        mainPanel.setAlignment(Pos.CENTER);
        mainPanel.setSpacing(30);
        mainPanel.getStyleClass().add("popup-panel");

        Label titleLabel = new Label("You Win!");
        titleLabel.getStyleClass().add("popup-title");

        Label starsLabel = new Label();
        starsLabel.getStyleClass().add("star-label");
        controller.setStarsLabel(starsLabel);
        controller.updateStarsDisplay();

        Label congratsLabel = new Label("Well done!");
        congratsLabel.getStyleClass().add("popup-subtitle");

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
        mainPanel.getChildren().addAll(titleLabel, starsLabel, congratsLabel, buttonContainer);
        rootPane.getChildren().add(mainPanel);

        return rootPane;
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/popupScreenStyle.css";
    }
}