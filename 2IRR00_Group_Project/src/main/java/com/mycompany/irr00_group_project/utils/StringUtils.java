package com.mycompany.irr00_group_project.utils;

/**
 * Utility class for string operations.
 */
public class StringUtils {

    /**
     * Checks if a string is null or empty.
     * @param s the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a string is null or contains only whitespace characters.
     * @param s the string to check
     * @return true if the string is null or contains only whitespace, false otherwise
     */
    public static boolean isNullOrWhiteSpace(String s) {
        return s == null || s.trim().isEmpty();
    }
}
