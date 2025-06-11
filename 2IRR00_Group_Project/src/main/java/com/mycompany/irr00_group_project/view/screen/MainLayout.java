package com.mycompany.irr00_group_project.view.screen;

import com.mycompany.irr00_group_project.service.navigator.NavigationManager;
import com.mycompany.irr00_group_project.utils.StringUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The MainLayout class is responsible for setting up the main layout of the
 * application.
 * All other screens will be displayed within this layout.
 */
public class MainLayout extends AbstractScreen {

    private Stage primaryStage;
    private Scene scene;

    /**
     * This method is called to display the main layout of the application.
     * It initializes the FXML loader, sets up the stage, and applies the global CSS
     * style.
     * @param primaryStage The primary stage of the application.
     * @throws IOException An error can occur while loading the FXML file or
     *                     applying CSS.
     */
    public void display(Stage primaryStage) throws IOException {
        this.fxmlLoader = new FXMLLoader();
        this.primaryStage = primaryStage;
        loadFxml();
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
        try {
            MainMenuScreen mainMenu = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(mainMenu.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getFxmlPath() {
        return "/com/mycompany/irr00_group_project/view/screen/MainLayout.fxml";
    }
}
