package com.mycompany.irr00_group_project;

/**
 * This is the main class that launches the application.
 * We need this class to run the application because
 * NetBeans immediately checks for the presence of the JavaFX on runtime
 * and we can't run the application directly from the App class.
 * This class is necessary to avoid the JavaFX runtime errors.
 */
public class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}
