package com.mycompany.irr00_group_project.utils;

import javafx.scene.Parent;

/**
 * Represents a navigation entry with the screen and its type.
 */
public record NavigationEntry(Parent node, String screenType) {
}
