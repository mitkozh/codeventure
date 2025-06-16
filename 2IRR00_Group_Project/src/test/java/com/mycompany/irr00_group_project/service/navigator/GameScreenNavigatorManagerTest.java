package com.mycompany.irr00_group_project.service.navigator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
class GameScreenNavigatorManagerTest {

    private GameScreenNavigatorManager navigatorManager;
    private NavigationManager navigationManager;

    @BeforeAll
    static void initJfxRuntime() throws Exception {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (Exception e) {
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = NavigationManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        navigationManager = NavigationManager.getInstance();
        navigationManager.setContentArea(new javafx.scene.layout.AnchorPane());
        navigatorManager = new GameScreenNavigatorManager();
    }

    @Test
    void testNavigateToSettingsNavigatesToSettingsScreen() throws IOException {
        navigatorManager.navigateToSettings();

        assertEquals("SettingsScreen", navigationManager.getCurrentScreenType(),
                "Should navigate to SettingsScreen");
    }

    @Test
    void testNavigateToLossScreenNavigatesToLossScreen() throws IOException {
        navigatorManager.navigateToLossScreen();

        assertEquals("LossScreen", navigationManager.getCurrentScreenType(),
                "Should navigate to LossScreen");
    }

    @Test
    void testNavigateBackNavigatesToPreviousScreen() throws Exception {
        java.lang.reflect.Method navigateBackMethod = GameScreenNavigatorManager.class.getDeclaredMethod("navigateBack");
        navigateBackMethod.setAccessible(true);

        navigatorManager.navigateToSettings();
        navigatorManager.navigateToHelp();

        assertEquals("HelpScreen", navigationManager.getCurrentScreenType());

        navigateBackMethod.invoke(navigatorManager);

        assertEquals("SettingsScreen", navigationManager.getCurrentScreenType());
    }

    @Test
    void testNavigateToHelpNavigatesToHelpScreen() throws IOException {
        navigatorManager.navigateToHelp();

        assertEquals("HelpScreen", navigationManager.getCurrentScreenType(),
                "Should navigate to HelpScreen");
    }

    @Test
    void testNavigateBackOnInitialScreenDoesNotThrow() throws Exception {
        java.lang.reflect.Method navigateBackMethod = GameScreenNavigatorManager.class.getDeclaredMethod("navigateBack");
        navigateBackMethod.setAccessible(true);
        assertDoesNotThrow(() -> navigateBackMethod.invoke(navigatorManager));
    }
}