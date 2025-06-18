package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.gui.screen.AbstractScreen;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NavigationManagerTest {

    private NavigationManager navigationManager;

    private static class TestScreen extends AbstractScreen {
        private final String name;

        TestScreen(String name) {
            this.name = name;
        }

        @Override
        public Parent getView() throws IOException {
            return new AnchorPane(); 
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        protected Parent createContent() {
            throw new UnsupportedOperationException("Unimplemented method 'createContent'");
        }

        @Override
        protected String getCssPath() {
            throw new UnsupportedOperationException("Unimplemented method 'getCssPath'");
        }
    }
    
    /**
     *  Sets up the NavigationManager instance before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        Field instanceField = NavigationManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        navigationManager = NavigationManager.getInstance();
        navigationManager.setContentArea(new AnchorPane());
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        NavigationManager instance1 = NavigationManager.getInstance();
        NavigationManager instance2 = NavigationManager.getInstance();
        assertSame(instance1, instance2, "Should return the same singleton instance");
    }

    @Test
    void testNavigateToSetsCurrentEntry() throws IOException {
        TestScreen screen = new TestScreen("TestScreen1");

        navigationManager.navigateTo(screen);

        assertEquals("TestScreen", navigationManager.getCurrentScreenType(),
            "Current screen type should match");
        assertEquals(0, navigationManager.getNavigationStackSize(),
            "Stack should be empty after first navigation");
    }

    @Test
    void testNavigateToPushesCurrentEntryToStack() throws IOException {
        TestScreen screen1 = new TestScreen("TestScreen1");
        TestScreen screen2 = new TestScreen("TestScreen2");

        navigationManager.navigateTo(screen1);
        navigationManager.navigateTo(screen2);

        assertEquals(1, navigationManager.getNavigationStackSize(),
            "Stack should contain one entry");
        assertEquals("TestScreen", navigationManager.getCurrentScreenType(),
            "Current screen type should match screen2");
    }

    @Test
    void testNavigateBackRestoresPreviousScreen() throws IOException {
        TestScreen screen1 = new TestScreen("TestScreen1");
        TestScreen screen2 = new TestScreen("TestScreen2");

        navigationManager.navigateTo(screen1);
        navigationManager.navigateTo(screen2);
        navigationManager.navigateBack();

        assertEquals(0, navigationManager.getNavigationStackSize(),
            "Stack should be empty after navigating back");
        assertEquals("TestScreen",
            navigationManager.getCurrentScreenType(), "Current screen should be screen1");
    }

    @Test
    void testNavigateBackDoesNothingWhenStackEmpty() {
        navigationManager.navigateBack();

        assertEquals(0, navigationManager.getNavigationStackSize(), "Stack should remain empty");
        assertNull(navigationManager.getCurrentScreenType(), "Current screen should remain null");
    }

    @Test
    void testNavigateToRootClearsStack() throws IOException {
        TestScreen screen1 = new TestScreen("TestScreen1");
        TestScreen screen2 = new TestScreen("TestScreen2");

        navigationManager.navigateTo(screen1);
        navigationManager.navigateToRoot(screen2);

        assertEquals(0, navigationManager.getNavigationStackSize(), "Stack should be cleared");
        assertEquals("TestScreen",
            navigationManager.getCurrentScreenType(), "Current screen should be screen2");
    }

    @Test
    void testGetPreviousScreenTypeThrowsExceptionWhenStackEmpty() {
        assertThrows(IllegalStateException.class, () -> navigationManager.getPreviousScreenType(),
                "Should throw IllegalStateException when stack is empty");
    }

    @Test
    void testCanNavigateBackReturnsCorrectStatus() throws IOException {
        TestScreen screen = new TestScreen("TestScreen");

        assertFalse(navigationManager.canNavigateBack(), "Should return false when stack is empty");
        navigationManager.navigateTo(screen);
        navigationManager.navigateTo(new TestScreen("TestScreen2"));
        assertTrue(navigationManager.canNavigateBack(),
            "Should return true when stack has entries");
    }

    @Test
    void testNavigateToThrowsIllegalArgumentExceptionForNullScreen() {
        assertThrows(IllegalArgumentException.class, () -> navigationManager.navigateTo(null),
                "Should throw IllegalArgumentException for null screen");
    }
}