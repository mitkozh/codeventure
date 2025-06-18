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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The main game screen implementation that combines all gameplay components.
 * Manages the layout and interaction between:
 * - Game grid display
 * - Code editor
 * - Console output
 * - Control buttons
 */
public class GameScreen extends AbstractScreen {

    private GameScreenController controller;

    private StackPane rootPane;
    private BorderPane gamePanel;
    private Label levelTitle;
    private Button gearButton;
    private Button infoButton;
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

    /**
     * Constructs and configures the complete game screen UI.
     * Creates the main layout structure including:
     * - Header with control buttons
     * - Center content with game grid and code editor
     * - Button controls area
     * 
     * @return The fully constructed UI root node
     */
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

    /**
     * Creates and configures the header section containing:
     * - Info button (for help & instructions)
     * - Settings button
     * 
     * @return Configured HBox containing header controls
     */
    private HBox createHeader() {
        HBox header = new HBox(10);
        header.getStyleClass().add("gameScreen-header");
        header.setAlignment(Pos.TOP_RIGHT);

        infoButton = new Button("ℹ");
        infoButton.setId("infoButton");
        infoButton.getStyleClass().addAll("custom-button");
        infoButton.setOnAction(controller::onHelpClick);

        gearButton = new Button("⚙");
        gearButton.setId("gearButton");
        gearButton.getStyleClass().addAll("custom-button");
        gearButton.setOnAction(controller::onSettingsClick);

        header.getChildren().addAll(infoButton, gearButton);

        return header;
    }

    /**
     * Creates the main content area containing:
     * - Left side: Game grid display
     * - Right side: Code editor, console output, and control buttons
     * 
     * @return Configured HBox containing the main game content
     */
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

    /**
     * Creates and configures the button control area containing:
     * - Run Code button
     * - Stop Execution button
     * - Reset Level button
     * 
     * @return Configured VBox containing the control buttons
     */
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