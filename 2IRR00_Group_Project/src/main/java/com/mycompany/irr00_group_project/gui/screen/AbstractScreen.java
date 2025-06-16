package com.mycompany.irr00_group_project.gui.screen;

import com.mycompany.irr00_group_project.utils.StringUtils;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * AbstractScreen is an abstract class that provides a template for creating
 * JavaFX screens.
 * It handles loading FXML files, applying CSS styles, and managing the root
 */
public abstract class AbstractScreen {
    protected Parent root;

    /**
     * This method is called to get the root view of the screen.
     * It initializes the root node by creating content and applying styling.
     *
     * @return The root node of the screen.
     * @throws IOException An error can occur while applying CSS.
     */
    public Parent getView() throws IOException {
        if (root == null) {
            this.root = createContent();
        }
        applyCssToRoot();
        return root;
    }

    /**
     * Creates the content of the screen.
     * This method can be overridden by subclasses to provide custom content.
     *
     * @return The root node of the screen
     */
    protected abstract Parent createContent();

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

    protected abstract String getCssPath();

}
