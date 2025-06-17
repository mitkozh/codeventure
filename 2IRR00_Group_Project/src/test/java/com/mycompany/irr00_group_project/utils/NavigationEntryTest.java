package com.mycompany.irr00_group_project.utils;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the NavigationEntry class.
 */
public class NavigationEntryTest {

    @Test
    void testNavigationEntryCreation() {
        Parent node = new Pane();
        String screenType = "MENU";
        NavigationEntry entry = new NavigationEntry(node, screenType);

        assertEquals(node, entry.node(), "Node should match the provided Parent");
        assertEquals(screenType, entry.screenType(),
            "Screen type should match the provided string");
    }

    @Test
    void testNavigationEntryNodeNull() {
        NavigationEntry entry = new NavigationEntry(null, "GAME");
        assertNull(entry.node(), "Node should be null");
        assertEquals("GAME", entry.screenType(), "Screen type should be GAME");
    }

    @Test
    void testNavigationEntryScreenTypeNull() {
        Parent node = new Pane();
        NavigationEntry entry = new NavigationEntry(node, null);
        assertEquals(node, entry.node(), "Node should match the provided Parent");
        assertNull(entry.screenType(), "Screen type should be null");
    }

    @Test
    void testNavigationEntryEquality() {
        Parent node1 = new Pane();
        NavigationEntry entry1 = new NavigationEntry(node1, "MENU");
        NavigationEntry entry2 = new NavigationEntry(node1, "MENU");
        assertEquals(entry1, entry2, "Entries with same node and screen type should be equal");
    }

    @Test
    void testNavigationEntryInequality() {
        Parent node1 = new Pane();
        Parent node2 = new Pane();
        NavigationEntry entry1 = new NavigationEntry(node1, "MENU");
        NavigationEntry entry2 = new NavigationEntry(node2, "GAME");
        assertNotEquals(entry1, entry2,
            "Entries with different node and screen type should not be equal");
    }
}