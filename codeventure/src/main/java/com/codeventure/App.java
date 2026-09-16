/**
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license.
 */

package com.codeventure;

import com.codeventure.service.core.impl.AudioManagerServiceImpl;
import com.codeventure.gui.screen.MainLayout;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The App class is the actual entry point of the JavaFX application.
 * It initializes the application and displays the main menu screen.
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AudioManagerServiceImpl.getInstance();
        MainLayout mainLayout = new MainLayout();
        mainLayout.display(stage);
    }
}

