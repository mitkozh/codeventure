package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.LossScreenController;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 
 * LossScreen is a class that represents the loss screen in the game.
 */
public class LossScreen extends AbstractScreen {

    private LossScreenController controller;

    @Override
    protected Parent createContent() {
        controller = new LossScreenController();
        controller.initialize();
        return createUI();
    }

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

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/lossScreenStyle.css";
    }
}