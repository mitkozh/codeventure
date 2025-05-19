package com.mycompany.irr00_group_project.view.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.mycompany.irr00_group_project.utils.StringUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * AbstractScreen is an abstract class that provides a template for creating JavaFX screens.
 * It handles loading FXML files, applying CSS styles, and setting up the stage.
 */
public abstract class AbstractScreen {
    protected Parent root;
    protected Scene scene;
    protected Stage primaryStage;
    protected FXMLLoader fxmlLoader;

    /**
     * Displays the screen by loading the FXML file, creating the scene, applying CSS styles,
     * and setting up the stage.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs while loading the FXML file or font
     */
    public void display(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.fxmlLoader = new FXMLLoader();

        loadFxml();
        createScene();
        applyCssSpecific();
        applyCssGlobal();
        applyFont();
        setupStage();
    }

    private void applyCssGlobal() throws FileNotFoundException {
        String cssMainPath = getCssMainPath();
        applyCssOnPath(cssMainPath);
    }

    private void setupStage() {
        primaryStage.setTitle(getTitle());
        primaryStage.setFullScreen(isFullScreen());
        primaryStage.setMinWidth(getMinStageWidth());
        primaryStage.setMinHeight(getMinStageHeight());
        primaryStage.setScene(scene);
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

    private void applyCssSpecific() throws FileNotFoundException {
        String cssPath = getCssPath();
        applyCssOnPath(cssPath);
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

    protected String getCssPath() {
        return "/com/mycompany/irr00_group_project/assets/css/styles.css";
    }

    private String getCssMainPath() {
        return "/com/mycompany/irr00_group_project/assets/css/styles.css";
    }

    private void loadFxml() throws IOException {
        String path = getFxmlPath();
        if (StringUtils.isNullOrWhiteSpace(path)) {
            throw new IllegalArgumentException("FXML path is null or empty");
        }
        URL fxmlUrl = getClass().getResource(path);
        if (fxmlUrl == null) {
            throw new FileNotFoundException("FXML file not found: " + path);
        }
        fxmlLoader.setLocation(fxmlUrl);
        this.root = fxmlLoader.load();
    }

    protected abstract String getFxmlPath();

    protected abstract String getTitle();

    protected double getMinStageWidth() {
        return 1024;
    }

    protected double getMinStageHeight() {
        return 768;
    }
    
    protected boolean isFullScreen() {
        return true;
    }

}
