package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.SettingsController;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The SettingsScreen class constructs and displays the settings menu
 * of the application, allowing the user to adjust audio levels and choose a character.
 */
public class SettingsScreen extends AbstractScreen {

    private SettingsController controller;

    @Override
    protected Parent createContent() {
        controller = new SettingsController();
        Parent ui = createUI();
        controller.initialize();
        return ui;
    }

    private Parent createUI() {
        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("settings-bg");

        VBox settingsPanel = new VBox();
        settingsPanel.getStyleClass().add("settings-panel");
        settingsPanel.setMaxWidth(600.0);
        settingsPanel.setMaxHeight(600);
        settingsPanel.setAlignment(Pos.TOP_CENTER);
        settingsPanel.setSpacing(20.0);
        settingsPanel.setPadding(new Insets(20.0, 20.0, 30.0, 20.0));

        HBox header = createHeader();

        VBox contentInnerBox = new VBox();
        contentInnerBox.getStyleClass().add("content-inner-box");
        VBox.setVgrow(contentInnerBox, javafx.scene.layout.Priority.ALWAYS);
        contentInnerBox.setAlignment(Pos.TOP_CENTER);
        contentInnerBox.setSpacing(10);
        contentInnerBox.setPadding(new Insets(20, 20, 20, 20));

        HBox contentArea = createContentArea();
        VBox.setVgrow(contentArea, javafx.scene.layout.Priority.ALWAYS);

        Separator bottomSeparator = new Separator();

        Button backToMenuButton = new Button("GO TO MENU");
        backToMenuButton.getStyleClass().addAll("custom-button", "back-to-menu-button");
        backToMenuButton.setOnAction(controller::onBackToScreenAction);
        controller.setBackToMenuButton(backToMenuButton);


        contentInnerBox.getChildren().addAll(contentArea, bottomSeparator, backToMenuButton);
        settingsPanel.getChildren().addAll(header, contentInnerBox);
        rootPane.getChildren().add(settingsPanel);

        return rootPane;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setSpacing(10);
        header.getStyleClass().add("settings-header");

        HBox titleContainer = new HBox();
        HBox.setHgrow(titleContainer, javafx.scene.layout.Priority.ALWAYS);
        titleContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("SETTINGS");
        titleLabel.getStyleClass().add("settings-title");
        titleContainer.getChildren().add(titleLabel);

        Button closeButton = new Button("X");
        closeButton.setId("closeButton");
        closeButton.getStyleClass().addAll("custom-button", "close-button");
        closeButton.setOnAction(controller::handleClose);

        header.getChildren().addAll(titleContainer, closeButton);
        return header;
    }

    private HBox createContentArea() {
        HBox contentArea = new HBox();
        contentArea.setSpacing(40);
        contentArea.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(contentArea, javafx.scene.layout.Priority.ALWAYS);

        VBox audioSection = createAudioSection();
        HBox.setHgrow(audioSection, javafx.scene.layout.Priority.ALWAYS);

        Separator verticalSeparator = new Separator();
        verticalSeparator.setOrientation(Orientation.VERTICAL);

        VBox graphicsSection = createGraphicsSection();
        HBox.setHgrow(graphicsSection, javafx.scene.layout.Priority.ALWAYS);

        contentArea.getChildren().addAll(audioSection, verticalSeparator, graphicsSection);
        return contentArea;
    }

    private VBox createAudioSection() {
        VBox audioSection = new VBox();
        audioSection.setSpacing(12);
        audioSection.getStyleClass().add("section-pane");
        audioSection.setMinWidth(280);
        HBox.setHgrow(audioSection, javafx.scene.layout.Priority.ALWAYS);

        Label audioTitle = new Label("AUDIO");
        audioTitle.getStyleClass().add("section-title");

        Label masterVolumeLabel = new Label("MASTER VOLUME");
        masterVolumeLabel.getStyleClass().add("setting-label");

        Slider masterVolumeSlider = new Slider();
        masterVolumeSlider.setMin(0);
        masterVolumeSlider.setMax(100);
        masterVolumeSlider.getStyleClass().add("volume-slider");
        controller.setMasterVolumeSlider(masterVolumeSlider);

        Label musicLabel = new Label("MUSIC");
        musicLabel.getStyleClass().add("setting-label");

        Slider musicSlider = new Slider();
        musicSlider.setMin(0);
        musicSlider.setMax(100);
        musicSlider.getStyleClass().add("volume-slider");
        controller.setMusicSlider(musicSlider);
        Label sfxLabel = new Label("SFX");
        sfxLabel.getStyleClass().add("setting-label");

        Slider sfxSlider = new Slider();
        sfxSlider.setMin(0);
        sfxSlider.setMax(100);
        sfxSlider.getStyleClass().add("volume-slider");
        controller.setSfxSlider(sfxSlider);

        audioSection.getChildren().addAll(audioTitle, masterVolumeLabel, masterVolumeSlider,
                musicLabel, musicSlider, sfxLabel, sfxSlider);
        return audioSection;
    }

    private VBox createGraphicsSection() {
        VBox graphicsSection = new VBox();
        graphicsSection.setSpacing(12);
        graphicsSection.getStyleClass().add("section-pane");
        graphicsSection.setMinWidth(280);
        HBox.setHgrow(graphicsSection, javafx.scene.layout.Priority.ALWAYS);

        Label graphicsTitle = new Label("GRAPHICS");
        graphicsTitle.getStyleClass().add("section-title");
        Label characterLabel = new Label("CHARACTER");
        characterLabel.getStyleClass().add("setting-label");

        ComboBox<String> characterComboBox = new ComboBox<>();
        characterComboBox.getStyleClass().addAll("character-combobox", "combo-box");
        characterComboBox.setItems(FXCollections.observableArrayList(
                "Robot", "Robot kid", "Alien", "Cool alien"));
        controller.setCharacterComboBox(characterComboBox);

        graphicsSection.getChildren().addAll(graphicsTitle, characterLabel, characterComboBox);
        return graphicsSection;
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/settingsMenuStyle.css";
    }
}