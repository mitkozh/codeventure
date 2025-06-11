package com.mycompany.irr00_group_project.utils;

/**
 * This class provides a simple cache for the code editor's content.
 */
public class CodeEditorCache {
 private static String cachedCode = "";

    public static void setCode(String code) {
        cachedCode = code;
    }

    public static String getCode() {
        return cachedCode;
    }
}