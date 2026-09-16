package com.codeventure.utils;

import javafx.scene.Parent;

/**
 * Represents a navigation entry with the screen and its type.
 */
public record NavigationEntry(Parent node, String screenType) {
}
