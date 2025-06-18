package com.mycompany.irr00_group_project.gui.screen;

import java.util.Objects;

import com.mycompany.irr00_group_project.controller.HelpScreenController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Implements the application's help screen which provides
 * detailed game instructions, code examples, and visual references.
 * 
 * The screen is organized into two main columns:
 *   Left column: Game mechanics and tile type explanations</li>
 *   Right column: Code examples and feedback system information</li>
 */
public class HelpScreen extends AbstractScreen {

    private HelpScreenController controller;

    @Override
    protected Parent createContent() {
        controller = new HelpScreenController();
        controller.initialize();
        return createUI();
    }

    /**
     * Constructs and configures the complete help screen UI.
     * Creates the main layout structure including:
     * - Header with title and close button
     * - Scrollable content area with two columns
     * - Back to menu button
     */
    private Parent createUI() {
        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("helpScreen-bg");

        VBox helpPanel = new VBox();
        helpPanel.getStyleClass().add("helpScreen-panel");
        helpPanel.setMaxWidth(1100);
        helpPanel.setMaxHeight(750);
        helpPanel.setAlignment(Pos.TOP_CENTER);
        helpPanel.setSpacing(20);
        helpPanel.setPadding(new Insets(20.0, 20.0, 30.0, 20.0));
        HBox header = createHeader();

        VBox contentInnerBox = new VBox();
        contentInnerBox.getStyleClass().add("content-inner-box");
        VBox.setVgrow(contentInnerBox, javafx.scene.layout.Priority.ALWAYS);
        contentInnerBox.setAlignment(Pos.TOP_CENTER);
        contentInnerBox.setSpacing(15);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        HBox columnsContainer = createColumnsContainer();
        scrollPane.setContent(columnsContainer);

        Button backToMenuButton = new Button("GO BACK");
        backToMenuButton.getStyleClass().addAll("custom-button", "back-to-menu-button");
        backToMenuButton.setOnAction(controller::backToMenu);

        contentInnerBox.getChildren().addAll(scrollPane, backToMenuButton);
        helpPanel.getChildren().addAll(header, contentInnerBox);
        rootPane.getChildren().add(helpPanel);

        return rootPane;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setSpacing(10);
        header.getStyleClass().add("helpScreen-header");

        HBox titleContainer = new HBox();
        HBox.setHgrow(titleContainer, javafx.scene.layout.Priority.ALWAYS);
        titleContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("HELP");
        titleLabel.getStyleClass().add("helpScreen-title");
        titleContainer.getChildren().add(titleLabel);

        Button closeButton = new Button("X");
        closeButton.setId("closeButton");
        closeButton.getStyleClass().addAll("custom-button", "close-button");
        closeButton.setOnAction(controller::backToMenu);

        header.getChildren().addAll(titleContainer, closeButton);
        return header;
    }

    private HBox createColumnsContainer() {
        HBox columnsContainer = new HBox();
        columnsContainer.setSpacing(40);
        columnsContainer.setAlignment(Pos.TOP_CENTER);
        columnsContainer.getStyleClass().add("helpScreen-columns");
        columnsContainer.setPadding(new Insets(20, 30, 20, 30));

        VBox leftColumn = createLeftColumn();
        VBox rightColumn = createRightColumn();

        columnsContainer.getChildren().addAll(leftColumn, rightColumn);
        return columnsContainer;
    }

    private VBox createLeftColumn() {
        VBox leftColumn = new VBox();
        leftColumn.setAlignment(Pos.TOP_LEFT);
        leftColumn.setSpacing(15);
        leftColumn.setPrefWidth(450);

        Label gameMechanicsTitle = new Label("GAME MECHANICS");
        gameMechanicsTitle.getStyleClass().add("section-title");

        VBox codeEditorSection = createCodeEditorSection();

        VBox tileTypesSection = createTileTypesSection();

        leftColumn.getChildren().addAll(gameMechanicsTitle, codeEditorSection, tileTypesSection);
        return leftColumn;
    }

    private VBox createCodeEditorSection() {
        VBox codeEditorSection = new VBox();
        codeEditorSection.setSpacing(8);

        Label codeEditorTitle = new Label("CODE EDITOR");
        codeEditorTitle.getStyleClass().add("subsection-title");

        ImageView codeEditorImage = new ImageView();
        codeEditorImage.setFitWidth(350);
        codeEditorImage.setFitHeight(250);
        try {
            codeEditorImage.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(
                            "/com/mycompany/irr00_group_project/assets/images/code_editor_pic.png"))));
        } catch (Exception e) {
            System.err.println("Error loading code editor image: " + e.getMessage());
        }

        Label codeEditorText1 = new Label("• Write code here to move the Robot");
        codeEditorText1.getStyleClass().add("help-text");

        Label codeEditorText2 = new Label("• Use commands to guide the robot to the goal");
        codeEditorText2.getStyleClass().add("help-text");

        Label codeEditorText3 = new Label("• The goal is to reach the Terminal");
        codeEditorText3.getStyleClass().add("help-text");

        Label codeEditorText4 = new Label("• Use Java syntax for the code");
        codeEditorText4.getStyleClass().add("help-text");

        codeEditorSection.getChildren().addAll(codeEditorTitle, codeEditorImage,
                codeEditorText1, codeEditorText2, codeEditorText3, codeEditorText4);
        return codeEditorSection;
    }

    private VBox createTileTypesSection() {
        VBox tileTypesSection = new VBox();
        tileTypesSection.setSpacing(8);

        Label tileTypesTitle = new Label("TILE TYPES");
        tileTypesTitle.getStyleClass().add("subsection-title");

        Label floorLabel = new Label("FLOOR: Safe walking area");
        floorLabel.getStyleClass().add("help-text");
        ImageView floorImage = createTileImage(
                "/com/mycompany/irr00_group_project/assets/images/tiles/normal_tile.png");

        Label hazardLabel = new Label("HAZARD: Dangerous area to avoid");
        hazardLabel.getStyleClass().add("help-text");
        ImageView obstacleImage = createTileImage(
                "/com/mycompany/irr00_group_project/assets/images/tiles/obstacle_tile.png");

        Label terminalLabel = new Label("TERMINAL: Goal destination");
        terminalLabel.getStyleClass().add("help-text");
        ImageView endImage = createTileImage("/com/mycompany/irr00_group_project/assets/images/tiles/end_tile.png");

        Label keyLabel = new Label("KEY: Objective");
        keyLabel.getStyleClass().add("help-text");
        ImageView keyImage = createTileImage(
                "/com/mycompany/irr00_group_project/assets/images/tiles/key-animation.gif");

        Label doorOpenedLabel = new Label("DOOR: Opened");
        doorOpenedLabel.getStyleClass().add("help-text");
        ImageView doorOpenedImage = createTileImage(
                "/com/mycompany/irr00_group_project/assets/images/tiles/door_opened.png");

        Label doorClosedLabel = new Label("DOOR: Closed");
        doorClosedLabel.getStyleClass().add("help-text");
        ImageView doorClosedImage = createTileImage(
                "/com/mycompany/irr00_group_project/assets/images/tiles/door_closed.png");

        Label doorText1 = new Label("• The keys are needed to unlock the doors");
        doorText1.getStyleClass().add("help-text");
        Label doorText2 = new Label("• You can't move through a door unless you have a key");
        doorText2.getStyleClass().add("help-text");

        tileTypesSection.getChildren().addAll(tileTypesTitle,
                floorLabel, floorImage,
                hazardLabel, obstacleImage,
                terminalLabel, endImage,
                keyLabel, keyImage,
                doorOpenedLabel, doorOpenedImage,
                doorClosedLabel, doorClosedImage,
                doorText1, doorText2);

        return tileTypesSection;
    }

    private ImageView createTileImage(String imagePath) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        try {
            imageView.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(imagePath))));
        } catch (Exception e) {
            System.err.println("Error loading tile image: " + e.getMessage());
        }
        return imageView;
    }

    private VBox createRightColumn() {
        VBox rightColumn = new VBox();
        rightColumn.setAlignment(Pos.TOP_LEFT);
        rightColumn.setSpacing(15);
        rightColumn.setPrefWidth(450);

        Label codeExamplesTitle = new Label("CODE EXAMPLES");
        codeExamplesTitle.getStyleClass().add("section-title");

        VBox basicMovement = createCodeExample("Basic movement command", "character.moveForward();");

        VBox turnLeft = createCodeExample("Command for changing direction to left", "character.turnLeft();");

        VBox turnRight = createCodeExample("Command for changing direction to right", "character.turnRight();");

        VBox whileLoop = createCodeExample("While-Loop movement", "while (true) {\n    character.moveForward();\n}");

        VBox forLoop = createCodeExample("For-loop movement",
                "for (int i = 0; i < 5; i++) {\n    character.moveForward();\n}");

        VBox feedbackSection = createFeedbackSection();

        rightColumn.getChildren().addAll(codeExamplesTitle, basicMovement, turnLeft, turnRight,
                whileLoop, forLoop, feedbackSection);
        return rightColumn;
    }

    private VBox createCodeExample(String description, String code) {
        VBox codeExample = new VBox();
        codeExample.setSpacing(10);

        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("code-description");

        Label codeLabel = new Label(code);
        codeLabel.getStyleClass().add("code-text");

        codeExample.getChildren().addAll(descriptionLabel, codeLabel);
        return codeExample;
    }

    private VBox createFeedbackSection() {
        VBox feedbackSection = new VBox();
        feedbackSection.setSpacing(8);

        Label feedbackTitle = new Label("FEEDBACK SCREEN");
        feedbackTitle.getStyleClass().add("section-title");

        Label positiveFeedbackLabel = new Label("Positive feedback");
        positiveFeedbackLabel.getStyleClass().add("help-text");

        ImageView positiveFeedbackImage = new ImageView();
        positiveFeedbackImage.setFitWidth(300);
        positiveFeedbackImage.setFitHeight(75);
        try {
            positiveFeedbackImage.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(
                            "/com/mycompany/irr00_group_project/assets/images/sucess_feedback_screen.png"))));
        } catch (Exception e) {
            System.err.println("Error loading positive feedback image: " + e.getMessage());
        }

        Label negativeFeedbackLabel = new Label("Negative feedback");
        negativeFeedbackLabel.getStyleClass().add("help-text");

        ImageView negativeFeedbackImage = new ImageView();
        negativeFeedbackImage.setFitWidth(300);
        negativeFeedbackImage.setFitHeight(75);
        try {
            negativeFeedbackImage.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(
                            "/com/mycompany/irr00_group_project/assets/images/non-sucess_feedback_screen.png"))));
        } catch (Exception e) {
            System.err.println("Error loading negative feedback image: " + e.getMessage());
        }

        Label feedbackTextFirst = new Label("• Feedback shows step count and stars earned");
        feedbackTextFirst.getStyleClass().add("help-text");
        Label feedbackTextSecond = new Label("• Fewer steps earn higher star ratings");
        feedbackTextSecond.getStyleClass().add("help-text");

        feedbackSection.getChildren().addAll(feedbackTitle,
                positiveFeedbackLabel, positiveFeedbackImage,
                negativeFeedbackLabel, negativeFeedbackImage,
                feedbackTextFirst, feedbackTextSecond);

        return feedbackSection;
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/helpStyle.css";
    }
}