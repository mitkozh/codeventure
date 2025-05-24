package com.mycompany.irr00_group_project.view.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.mycompany.irr00_group_project.utils.StringUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * AbstractScreen is an abstract class that provides a template for creating
 * JavaFX screens.
 * It handles loading FXML files, applying CSS styles, and managing the root
 */
public abstract class AbstractScreen {
    protected Parent root;
    protected FXMLLoader fxmlLoader;

    /**
     * This method is called to get the root view of the screen.
     * It loads the FXML file and applies the CSS styles.
     *
     * @return The root node of the screen.
     * @throws IOException An error can occur while loading the FXML file or
     *                     applying CSS.
     */
    public Parent getView() throws IOException {
        loadFxml();
        applyCssToRoot();
        return root;
    }

    /**
     * Applies CSS styles to the root node of the screen.
     * The CSS file is loaded from the path returned by getCssPath().
     */
    protected void applyCssToRoot() {
        String cssPath = getCssPath();
        if (!StringUtils.isNullOrWhiteSpace(cssPath)) {
            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                root.getStylesheets().add(cssUrl.toExternalForm());
            }
        }
    }

    /**
     * Loads the FXML file and initializes the root node.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    protected void loadFxml() throws IOException {
        String path = getFxmlPath();
        if (StringUtils.isNullOrWhiteSpace(path)) {
            throw new IllegalArgumentException("FXML path is null or empty");
        }
        URL fxmlUrl = getClass().getResource(path);
        if (fxmlUrl == null) {
            throw new FileNotFoundException("FXML file not found: " + path);
        }
        fxmlLoader = new FXMLLoader(fxmlUrl);
        this.root = fxmlLoader.load();
    }

    protected abstract String getFxmlPath();

    protected abstract String getCssPath();

}
