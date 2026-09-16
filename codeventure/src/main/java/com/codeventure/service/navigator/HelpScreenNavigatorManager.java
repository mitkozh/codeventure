package com.codeventure.service.navigator;

/**
 * Manages navigation for the help screen.
 */
public class HelpScreenNavigatorManager {

    /**
     * Navigates back to the previous screen and notifies the game service.
     */
    public void navigateBackAndNotify() {
        NavigationManager navigationManager = NavigationManager.getInstance();
        NavigationService.getInstance().notifyReturnedToGame();
        navigationManager.navigateBack();
    }

}