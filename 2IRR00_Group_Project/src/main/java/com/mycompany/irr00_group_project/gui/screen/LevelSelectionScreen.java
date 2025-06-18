package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.LevelSelectionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The LevelSelectionScreen class is responsible for displaying the level
 * selection screen of the application. It provides a paginated interface
 * for users to browse and select different game levels, along with navigation
 * options to return to the main menu.
 */
public class LevelSelectionScreen extends AbstractScreen {

    private LevelSelectionController controller;

    @Override
    protected Parent createContent() {
        controller = new LevelSelectionController();
        Parent ui = createUI();
        controller.initialize();
        return ui;
    }

    private Parent createUI() {
        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("levelSelection-bg");

        VBox levelSelectionPanel = new VBox();
        levelSelectionPanel.getStyleClass().add("levelSelection-panel");
        levelSelectionPanel.setMaxWidth(1150);
        levelSelectionPanel.setMaxHeight(850);
        levelSelectionPanel.setAlignment(Pos.TOP_CENTER);
        levelSelectionPanel.setSpacing(20.0);
        levelSelectionPanel.setPadding(new Insets(20.0, 20.0, 30.0, 20.0));

        HBox header = createHeader();

        VBox contentInnerBox = new VBox();
        contentInnerBox.getStyleClass().add("content-inner-box");
        VBox.setVgrow(contentInnerBox, javafx.scene.layout.Priority.ALWAYS);
        contentInnerBox.setAlignment(Pos.TOP_CENTER);
        contentInnerBox.setSpacing(10);
        contentInnerBox.setPadding(new Insets(20, 20, 20, 20));
        Pagination pagination = new Pagination();
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageCount(1);
        pagination.setCurrentPageIndex(0);
        VBox.setVgrow(pagination, javafx.scene.layout.Priority.ALWAYS);
        controller.setPagination(pagination);

        Button backToMenuButton = new Button("MAIN MENU");
        backToMenuButton.getStyleClass().addAll("custom-button", "back-to-menu-button");
        backToMenuButton.setOnAction(controller::backToMenu);

        contentInnerBox.getChildren().addAll(pagination, backToMenuButton);
        levelSelectionPanel.getChildren().addAll(header, contentInnerBox);
        rootPane.getChildren().add(levelSelectionPanel);

        return rootPane;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setSpacing(10);
        header.getStyleClass().add("levelSelection-header");

        HBox titleContainer = new HBox();
        HBox.setHgrow(titleContainer, javafx.scene.layout.Priority.ALWAYS);
        titleContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("LEVEL SELECTION");
        titleLabel.getStyleClass().add("levelSelection-title");
        titleContainer.getChildren().add(titleLabel);

        header.getChildren().add(titleContainer);
        return header;
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/levelSelectionStyle.css";
    }
}