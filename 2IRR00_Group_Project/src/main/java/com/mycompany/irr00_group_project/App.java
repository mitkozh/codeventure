/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license
 */

package com.mycompany.irr00_group_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App javadoc.
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
            .getResource("/com/mycompany/irr00_group_project/view/screen/MainMenuScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
