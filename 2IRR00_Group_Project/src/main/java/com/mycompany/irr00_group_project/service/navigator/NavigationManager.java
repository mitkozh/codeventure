package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.gui.screen.AbstractScreen;
import com.mycompany.irr00_group_project.utils.NavigationEntry;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Stack;

/**
 * NavigationManager is a singleton class, responsible for managing the
 * navigation between different screens in the application.
 * It provides methods to set the content area of the main layout
 * and navigate to different screens.
 */
class NavigationManager {
    private static NavigationManager instance;
    private AnchorPane contentArea;
    private final Stack<NavigationEntry> navigationStack = new Stack<>();
    private NavigationEntry currentEntry;

    /**
     * Singleton instance of NavigationManager.
     *
     * @return the singleton instance
     */
    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setContentArea(AnchorPane contentArea) {
        this.contentArea = contentArea;
    }

    /**
     * Navigates to the specified screen and adds the current screen
     * to the navigation stack.
     *
     * @param screen the screen to navigate to
     */
    public void navigateTo(AbstractScreen screen) throws IOException {
        if (currentEntry != null) {
            navigationStack.push(currentEntry);
        }
        String screenType = determineScreenType(screen);
        Parent node = screen.getView();
        currentEntry = new NavigationEntry(node, screenType);
        displayScreen(node);
    }

    /**
     * Navigates to the specified screen without adding current screen to stack.
     *
     * @param screen the screen to navigate to
     */
    public void navigateToRoot(AbstractScreen screen) throws IOException {
        clearNavigationStack();
        String screenType = determineScreenType(screen);
        Parent node = screen.getView();
        currentEntry = new NavigationEntry(node, screenType);
        displayScreen(node);
    }

    /**
     * Navigates back to the previous screen in the stack if possible.
     */
    public void navigateBack() {
        if (canNavigateBack()) {
            NavigationEntry previousEntry = navigationStack.pop();
            currentEntry = previousEntry;
            displayScreen(previousEntry.node());
        }
    }

    /**
     * Checks if there's a previous screen to navigate back to.
     *
     * @return true if navigation back is possible
     */
    public boolean canNavigateBack() {
        return !navigationStack.isEmpty();
    }

    /**
     * Clears the navigation stack.
     */
    public void clearNavigationStack() {
        navigationStack.clear();
        currentEntry = null;
    }

    /**
     * Gets the type of the current screen.
     *
     * @return the current screen type, or null if no current screen
     */
    public String getCurrentScreenType() {
        return currentEntry != null ? currentEntry.screenType() : null;
    }

    /**
     * Gets the type of the previous screen.
     *
     * @return the previous screen type.
     * @throws IllegalStateException if there is no previous screen
     */
    public String getPreviousScreenType() {
        if (navigationStack.isEmpty()) {
            throw new IllegalStateException("There is no previous screen");
        }
        return navigationStack.peek().screenType();
    }

    /**
     * Determines the screen type based on the class name.
     *
     * @param screenClass the class of the screen node
     * @return the screen type string
     * @throws IllegalArgumentException if the screen class is null
     */
    private String determineScreenType(AbstractScreen screenClass) {
        if (screenClass == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        return screenClass.getClass().getSimpleName();
    }

    private void displayScreen(Parent node) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(node);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }

    /**
     * Gets the current navigation stack size.
     *
     * @return the size of the navigation stack
     */
    public int getNavigationStackSize() {
        return navigationStack.size();
    }
}