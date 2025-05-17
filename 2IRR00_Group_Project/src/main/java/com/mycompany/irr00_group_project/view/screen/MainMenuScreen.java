package com.mycompany.irr00_group_project.view.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The MainMenuScreen class is responsible for displaying the main menu screen of the application. 
 * It loads the FXML file, applies the CSS styles, and sets the font for the UI elements.
 */
public class MainMenuScreen {
    /**
     * Displays the main menu screen.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs while loading the FXML file or font
     */
    //todo: abstract this class to a generic screen class and make it extendable
    public void display(Stage primaryStage) throws IOException {
        String path = "/com/mycompany/irr00_group_project/view/screen/MainMenuScreen.fxml";
        URL fxmlUrl = getClass().getResource(path);
        Parent root;
        if (fxmlUrl != null) {
            root = FXMLLoader.load(fxmlUrl);
        } else {
            throw new FileNotFoundException(String.format("Resource not found: %s", path));
        }
        try (InputStream fontStream = getClass().getResourceAsStream(
            "/com/mycompany/irr00_group_project/assets/fonts/PixelifySans_Regular.ttf")) {
            if (fontStream == null) {
                throw new FileNotFoundException("Font file not found");
            }
            Font font = Font.loadFont(fontStream, 10);
            String fontFamily = font.getFamily();
            String s = "-fx-font-family: " + fontFamily + "; " 
                            + "-fx-font-size: " + font.getSize() + "px";
            root.setStyle(s + ";");
            root.setStyle("-fx-font: 15 \"Pixelify Sans\";");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load font", e);
        }
        Scene scene = new Scene(root);
        String cssPath = "/com/mycompany/irr00_group_project/assets/css/mainMenuStyle.css";
        URL cssUrl = getClass().getResource(cssPath);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            throw new FileNotFoundException(String.format("Resource not found: %s", path));
        }

        primaryStage.setTitle("Codeventure");
        primaryStage.setFullScreen(true);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(768);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                primaryStage.centerOnScreen();
                primaryStage.setWidth(1024);
                primaryStage.setHeight(768);
            }
        });
    }
}
