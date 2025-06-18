package com.mycompany.irr00_group_project.model.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Point Class.
 * Verifies coordinate storage, equality comparison, and hash code generation.
 */
public class PointTest {

    /**
     * Tests constructor and field access.
     * Verifies coordinates are stored correctly.
     */
    @Test
    void testConstructorAndFields() {
        Point point = new Point(3, 5);
        assertEquals(3, point.row);
        assertEquals(5, point.col);
    }

    /**
     * Tests equality comparison.
     * Verifies same coordinates produce equal points.
     */
    @Test
    void testEquals() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);
        Point p3 = new Point(3, 2);
        
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(null, p1);
        assertNotEquals("not a point", p1);
    }

    /**
     * Tests hash code generation.
     * Verifies equal points produce same hash codes.
     */
    @Test
    void testHashCode() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);
        
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /**
     * Tests string representation.
     * Verifies toString() contains coordinate information.
     */
    @Test
    void testToString() {
        Point point = new Point(1, 2);
        String str = point.toString();
        
        assertTrue(str.contains("row=1"));
        assertTrue(str.contains("col=2"));
    }

    /**
     * Tests negative coordinates.
     * Verifies constructor handles negative values.
     */
    @Test
    void testNegativeCoordinates() {
        Point point = new Point(-1, -2);
        assertEquals(-1, point.row);
        assertEquals(-2, point.col);
    }

    /**
     * Tests edge case coordinates.
     * Verifies maximum integer values are handled.
     */
    @Test
    void testEdgeCaseCoordinates() {
        Point point = new Point(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertEquals(Integer.MAX_VALUE, point.row);
        assertEquals(Integer.MIN_VALUE, point.col);
    }

    /**
     * Tests reflexivity of equals.
     * A point should be equal to itself.
     */
    @Test
    void testEqualsReflexive() {
        Point point = new Point(4, 7);
        assertEquals(point, point);
    }

    /**
     * Tests symmetry of equals.
     * If p1.equals(p2) then p2.equals(p1).
     */
    @Test
    void testEqualsSymmetric() {
        Point p1 = new Point(5, 6);
        Point p2 = new Point(5, 6);
        assertEquals(p1, p2);
        assertEquals(p2, p1);
    }

    /**
     * Tests transitivity of equals.
     * If p1.equals(p2) and p2.equals(p3), then p1.equals(p3).
     */
    @Test
    void testEqualsTransitive() {
        Point p1 = new Point(8, 9);
        Point p2 = new Point(8, 9);
        Point p3 = new Point(8, 9);
        assertEquals(p1, p2);
        assertEquals(p2, p3);
        assertEquals(p1, p3);
    }

    /**
     * Tests consistency of equals.
     * Multiple invocations should return the same result.
     */
    @Test
    void testEqualsConsistent() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        for (int i = 0; i < 10; i++) {
            assertEquals(p1, p2);
        }
    }

    /**
     * Tests hashCode consistency.
     * Multiple invocations should return the same result.
     */
    @Test
    void testHashCodeConsistent() {
        Point point = new Point(10, 20);
        int hash1 = point.hashCode();
        int hash2 = point.hashCode();
        assertEquals(hash1, hash2);
    }

    /**
     * Tests that different points have different hash codes (not guaranteed, but likely).
     */
    @Test
    void testDifferentPointsDifferentHashCodes() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 1);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    /**
     * Tests toString format.
     * Verifies the output matches the expected format.
     */
    @Test
    void testToStringFormat() {
        Point point = new Point(7, 8);
        assertEquals("Point{row=7, col=8}", point.toString());
    }
}