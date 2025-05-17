/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license
 */

package com.mycompany.irr00_group_project;

import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The App class is the main entry point of the JavaFX application.
 * It initializes the application and displays the main menu screen.
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuScreen mainMenu = new MainMenuScreen();
        try {
            mainMenu.display(stage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Main Menu Screen");
            Platform.exit();
        }
    }
}
