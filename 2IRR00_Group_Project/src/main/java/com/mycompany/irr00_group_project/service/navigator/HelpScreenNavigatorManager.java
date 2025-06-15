package com.mycompany.irr00_group_project.service.navigator;


import com.mycompany.irr00_group_project.gui.screen.GameScreen;

/**
 * Manages navigation for the help screen.
 */
public class HelpScreenNavigatorManager {

    /**
     * Navigates back to the previous screen and notifies the game service.
     */
    public void navigateBackAndNotify() {
        NavigationManager navigationManager = NavigationManager.getInstance();
        if (inGame(navigationManager)) {
            NavigationService.getInstance().notifyReturnedToGame();
        }
        navigationManager.navigateBack();
    }

    private boolean inGame(NavigationManager navigationManager) {
        return getScreenType(GameScreen.class)
                .equals(navigationManager.getPreviousScreenType());
    }

    private String getScreenType(Class<?> screenClass) {
        return screenClass.getSimpleName();
    }
}