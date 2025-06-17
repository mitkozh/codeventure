package com.mycompany.irr00_group_project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  Unit tests for the StringUtils class..
 */
public class StringUtilsTest {

    @Test
    void testIsNullOrEmptyWithNull() {
        assertTrue(StringUtils.isNullOrEmpty(null), "Null string should return true");
    }

    @Test
    void testIsNullOrEmptyWithEmpty() {
        assertTrue(StringUtils.isNullOrEmpty(""), "Empty string should return true");
    }

    @Test
    void testIsNullOrEmptyWithNonEmpty() {
        assertFalse(StringUtils.isNullOrEmpty("test"), "Non-empty string should return false");
    }

    @Test
    void testIsNullOrWhiteSpaceWithWhitespace() {
        assertTrue(StringUtils.isNullOrWhiteSpace("   "), "Whitespace string should return true");
    }

    @Test
    void testIsNullOrWhiteSpaceWithNonWhitespace() {
        assertFalse(StringUtils.isNullOrWhiteSpace("test "), 
            "Non-whitespace string should return false");
    }

    @Test
    void testIsNullOrWhiteSpaceWithNull() {
        assertTrue(StringUtils.isNullOrWhiteSpace(null), "Null string should return true");
    }
}