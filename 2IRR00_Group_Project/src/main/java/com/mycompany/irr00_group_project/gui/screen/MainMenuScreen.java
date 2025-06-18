package com.mycompany.irr00_group_project.gui.screen;

import java.util.Objects;

import com.mycompany.irr00_group_project.controller.MainMenuController;
import com.mycompany.irr00_group_project.utils.ConstantsResources;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Represents the main menu screen of the application.
 * It initializes and lays out the UI elements such as the title, background images,
 * and navigation buttons (Play, Settings, Help, Exit).
 * Styles and fonts are applied via the specified CSS file.
 * The UI layout is created manually in code.
 */
public class MainMenuScreen extends AbstractScreen {
    private MainMenuController controller;
    private StackPane rootPane;
    private ImageView backgroundGeometricImage;
    private ImageView backgroundImage;
    private ImageView bezelImage;
    private VBox menuVBox;
    private Label titleLabel;
    private Button levelSelectButton;
    private Button settingsButton;
    private Button helpButton;
    private Button exitButton;

    @Override
    protected Parent createContent() {
        controller = new MainMenuController();
        return createUI();
    }

    private Parent createUI() {
        rootPane = new StackPane();
        rootPane.setPrefHeight(768.0);
        rootPane.setPrefWidth(1024.0);

        // background geometric image
        backgroundGeometricImage = new ImageView();
        backgroundGeometricImage.setImage(new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream(ConstantsResources.GEOMETRIC_BACKGROUND))));
        backgroundGeometricImage.fitHeightProperty().bind(rootPane.heightProperty());
        backgroundGeometricImage.fitWidthProperty().bind(rootPane.widthProperty());

        // background image
        backgroundImage = new ImageView();
        backgroundImage.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(ConstantsResources.BACKGROUND_IMAGE))));
        backgroundImage.setFitHeight(700);
        backgroundImage.setPickOnBounds(true);
        backgroundImage.setPreserveRatio(true);

        // bezel image
        bezelImage = new ImageView();
        bezelImage.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(ConstantsResources.GAME_BEZEL))));
        bezelImage.setFitHeight(800);
        bezelImage.setPickOnBounds(true);
        bezelImage.setPreserveRatio(true);

        // vbox for menu
        menuVBox = new VBox(18.0);
        menuVBox.setAlignment(Pos.CENTER);
        menuVBox.setMaxHeight(600.0);
        menuVBox.setMaxWidth(700.0);
        menuVBox.setPadding(new Insets(40.0, 40.0, 40.0, 40.0)); // top, right, bottom, left

        titleLabel = new Label("Codeventure");
        titleLabel.getStyleClass().add("title-label");

        levelSelectButton = new Button("PLAY");
        levelSelectButton.getStyleClass().add("menu-button");
        levelSelectButton.setOnAction(controller::onLevelSelectClick);

        settingsButton = new Button("SETTINGS");
        settingsButton.getStyleClass().add("menu-button");
        settingsButton.setOnAction(controller::onSettingsClick);

        helpButton = new Button("HELP");
        helpButton.getStyleClass().add("menu-button");
        helpButton.setOnAction(controller::onHelpClick);

        exitButton = new Button("EXIT GAME");
        exitButton.getStyleClass().add("menu-button");
        exitButton.setOnAction(controller::onExitGameClick);

        menuVBox.getChildren().addAll(
                titleLabel,
                levelSelectButton,
                settingsButton,
                helpButton,
                exitButton);

        rootPane.getChildren().addAll(
                backgroundGeometricImage,
                backgroundImage,
                bezelImage,
                menuVBox);

        return rootPane;
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/mainMenuStyle.css";
    }
}
