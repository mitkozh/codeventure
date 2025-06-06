package com.mycompany.irr00_group_project.model.core;

/**
 * Represents a point in a 2D grid with row and column coordinates.
 * This class is used to represent positions in the game grid.
 */
public class Point {
    public final int row;
    public final int col;

    /**
     * Constructs a Point with the specified row and column.
     *
     * @param row The row index of the point.
     * @param col The column index of the point.
     */
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point other)) {
            return false;
        }
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return "Point{"
                + "row=" + row
                + ", col=" + col
                + '}';
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(row);
        result = 31 * result + Integer.hashCode(col);
        return result;
    }
}