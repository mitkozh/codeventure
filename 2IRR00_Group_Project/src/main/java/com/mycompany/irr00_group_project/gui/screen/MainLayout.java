package com.mycompany.irr00_group_project.gui.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import com.mycompany.irr00_group_project.service.navigator.MainLayoutNavigatorManager;
import com.mycompany.irr00_group_project.utils.ConstantsResources;
import com.mycompany.irr00_group_project.utils.StringUtils;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The MainLayout class is responsible for creating and managing the main GUI layout
 * of the application. It loads the global styles, applies the default font, 
 * and initializes the main navigation manager.
 * All other screens are displayed within this layout's content area.
 */
public class MainLayout extends AbstractScreen {

    private Stage primaryStage;
    private Scene scene;
    private AnchorPane contentArea;
    private MainLayoutNavigatorManager navigatorManager;

    @Override
    protected Parent createContent() {
        navigatorManager = new MainLayoutNavigatorManager();
        return createUI();
    }

    /**
     * Builds the primary UI layout consisting of a horizontally aligned container
     * and a content area. The navigation manager is linked to this content area.
     */
    private HBox createUI() {
        HBox rootLayout = new HBox();
        rootLayout.setId("rootLayout");
        rootLayout.setAlignment(Pos.CENTER);

        contentArea = new AnchorPane();
        contentArea.setId("contentArea");
        HBox.setHgrow(contentArea, javafx.scene.layout.Priority.ALWAYS);

        rootLayout.getChildren().add(contentArea);

        navigatorManager.setContentArea(contentArea);

        return rootLayout;
    }

    /**
     * This method is called to display the main layout of the application.
     * It initializes the FXML loader, sets up the stage, and applies the global CSS
     * style.
     *
     * @param primaryStage The primary stage of the application.
     * @throws IOException An error can occur while loading the FXML file or
     *                     applying CSS.
     */
    public void display(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent rootNode = getView();
        createScene();
        applyCss();
        applyFont();
        setupStage();
    }

    private void applyCss() throws FileNotFoundException {
        String cssPath = getCssPath();
        applyCssOnPath(cssPath);
    }

    private void setupStage() {
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(ConstantsResources.BACKGROUND_IMAGE))));
        primaryStage.setTitle(getTitle());
        primaryStage.setFullScreen(isFullScreen());
        primaryStage.setMinWidth(getMinStageWidth());
        primaryStage.setMinHeight(getMinStageHeight());
        primaryStage.setScene(scene);
        loadInitialContent();
        primaryStage.show();

        primaryStage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                primaryStage.setWidth(getMinStageWidth());
                primaryStage.setHeight(getMinStageHeight());
                primaryStage.centerOnScreen();
            }
        });
    }

    private void createScene() {
        this.scene = new Scene(root);
    }

    private void applyFont() {
        String fontPath = getFontPath();
        if (StringUtils.isNullOrWhiteSpace(fontPath)) {
            throw new IllegalArgumentException("Font path is null or empty");
        }
        try (InputStream stream = getClass().getResourceAsStream(fontPath)) {
            if (stream == null) {
                throw new FileNotFoundException("Font file not found: " + fontPath);
            }
            Font font = Font.loadFont(stream, 10);
            if (font == null) {
                throw new IOException("Failed to load font from: " + fontPath);
            }
            root.setStyle(
                    String.format("-fx-font-family: '%s'; -fx-font-size: %fpx;",
                            font.getFamily(), font.getSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene.getRoot().setStyle("-fx-font: 15 \"Pixelify Sans\";");
    }

    protected String getFontPath() {
        return "/com/mycompany/irr00_group_project/assets/fonts/PixelifySans_Regular.ttf";
    }

    private void applyCssOnPath(String cssPath) throws FileNotFoundException {
        if (StringUtils.isNullOrWhiteSpace(cssPath)) {
            throw new IllegalArgumentException("CSS path is null or empty");
        }
        URL cssUrl = getClass().getResource(cssPath);
        if (cssUrl == null) {
            throw new FileNotFoundException("CSS file not found: " + cssPath);
        }
        scene.getStylesheets().add(cssUrl.toExternalForm());
    }

    @Override
    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/styles.css";
    }

    private String getTitle() {
        return "Codeventure";
    }

    protected double getMinStageWidth() {
        return 1024;
    }

    protected double getMinStageHeight() {
        return 768;
    }

    protected boolean isFullScreen() {
        return true;
    }

    private void loadInitialContent() {
        navigatorManager.navigateToMainMenu();
    }
}
