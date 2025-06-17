package com.mycompany.irr00_group_project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.core.Point;

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
}