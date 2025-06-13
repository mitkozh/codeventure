package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.controller.GameScreenController;
import com.mycompany.irr00_group_project.gui.components.CodeEditorArea;
import com.mycompany.irr00_group_project.gui.components.ConsoleOutputArea;
import com.mycompany.irr00_group_project.gui.components.GameGridDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Class for implementing game screen.
 */
public class GameScreen extends AbstractScreen {

    private GameScreenController controller;

    private StackPane rootPane;
    private BorderPane gamePanel;
    private Label levelTitle;
    private Button gearButton;
    private Button runCodeButton;
    private Button stopExecutionButton;
    private Button resetLevelButton;
    private GameGridDisplay gameGridDisplay;
    private CodeEditorArea codeEditorArea;
    private ConsoleOutputArea consoleOutputArea;

    @Override
    protected Parent createContent() {
        controller = new GameScreenController(this);
        return createUI();
    }

    private Parent createUI() {
        rootPane = new StackPane();
        rootPane.getStyleClass().add("game-bg");

        gamePanel = new BorderPane();
        gamePanel.getStyleClass().add("game-panel");
        gamePanel.setMaxWidth(1400);
        gamePanel.setMaxHeight(900);
        gamePanel.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));

        // Top header
        HBox header = createHeader();
        gamePanel.setTop(header);

        // Center content
        HBox centerContent = createCenterContent();
        gamePanel.setCenter(centerContent);

        rootPane.getChildren().add(gamePanel);
        controller.initialize();
        return rootPane;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("gameScreen-header");
        header.setAlignment(Pos.TOP_RIGHT);

        gearButton = new Button("⚙");
        gearButton.setId("gearButton");
        gearButton.getStyleClass().addAll("custom-button");
        gearButton.setOnAction(controller::onSettingsClick);

        header.getChildren().add(gearButton);

        return header;
    }

    private HBox createCenterContent() {
        HBox centerContent = new HBox(20);
        VBox.setVgrow(centerContent, Priority.ALWAYS);

        VBox leftSide = new VBox();
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        leftSide.setMinWidth(600);

        gameGridDisplay = new GameGridDisplay();
        VBox.setVgrow(gameGridDisplay.getView(), Priority.ALWAYS);
        leftSide.getChildren().add(gameGridDisplay.getView());

        VBox rightSide = new VBox(15);
        HBox.setHgrow(rightSide, Priority.SOMETIMES);
        rightSide.setMinWidth(400);
        rightSide.setMaxWidth(500);

        levelTitle = new Label();
        levelTitle.getStyleClass().add("level-title");

        // Code editor area
        codeEditorArea = new CodeEditorArea();
        VBox.setVgrow(codeEditorArea.getView(), Priority.ALWAYS);

        // Console output area
        consoleOutputArea = new ConsoleOutputArea();
        VBox.setVgrow(consoleOutputArea.getView(), Priority.SOMETIMES);
        VBox buttonsArea = createButtonsArea();

        rightSide.getChildren().addAll(
                levelTitle,
                codeEditorArea.getView(),
                consoleOutputArea.getView(),
                buttonsArea);

        centerContent.getChildren().addAll(leftSide, rightSide);
        return centerContent;
    }

    private VBox createButtonsArea() {
        VBox buttonsArea = new VBox(10);
        buttonsArea.getStyleClass().add("right-panel-buttons-area");

        VBox innerVBox = new VBox(8);
        innerVBox.setAlignment(Pos.CENTER);
        HBox runStopHBox = new HBox(8);

        runCodeButton = new Button("RUN CODE");
        runCodeButton.getStyleClass().addAll("custom-button", "run-button");
        runCodeButton.setOnAction(controller::runCode);
        runCodeButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(runCodeButton, Priority.ALWAYS);

        stopExecutionButton = new Button("STOP EXECUTION");
        stopExecutionButton.getStyleClass().addAll("custom-button", "stop-button");
        stopExecutionButton.setOnAction(controller::stopExecutionOnClick);
        stopExecutionButton.setDisable(true);
        stopExecutionButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(stopExecutionButton, Priority.ALWAYS);

        runStopHBox.getChildren().addAll(runCodeButton, stopExecutionButton);

        HBox resetHBox = new HBox(8);
        resetHBox.setAlignment(Pos.CENTER);

        resetLevelButton = new Button("RESET LEVEL");
        resetLevelButton.getStyleClass().addAll("custom-button", "reset-button");
        resetLevelButton.setOnAction(controller::resetLevelOnClick);
        resetLevelButton.setMaxWidth(350);
        HBox.setHgrow(resetLevelButton, Priority.ALWAYS);

        resetHBox.getChildren().add(resetLevelButton);

        innerVBox.getChildren().addAll(runStopHBox, resetHBox);
        buttonsArea.getChildren().add(innerVBox);

        return buttonsArea;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

    public BorderPane getGamePanel() {
        return gamePanel;
    }

    public Label getLevelTitle() {
        return levelTitle;
    }

    public Button getGearButton() {
        return gearButton;
    }

    public Button getRunCodeButton() {
        return runCodeButton;
    }

    public Button getStopExecutionButton() {
        return stopExecutionButton;
    }

    public Button getResetLevelButton() {
        return resetLevelButton;
    }

    public GameGridDisplay getGameGridDisplay() {
        return gameGridDisplay;
    }

    public CodeEditorArea getCodeEditorArea() {
        return codeEditorArea;
    }

    public ConsoleOutputArea getConsoleOutputArea() {
        return consoleOutputArea;
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/gameStyle.css";
    }
}