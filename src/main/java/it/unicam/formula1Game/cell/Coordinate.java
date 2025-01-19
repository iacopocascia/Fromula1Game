package it.unicam.formula1Game.cell;

import java.util.Objects;

/**
 * Represents a coordinate in a 2D grid.
 * This class encapsulates a pair of integers representing a row and column.
 */
public class Coordinate {
    /**
     * The row component of the coordinate.
     */
    private int row;
    /**
     * The column component of the coordinate.
     */
    private int column;

    /**
     * Constructs a {@code Coordinate} with the specified row and column values.
     *
     * @param row    the row value of the coordinate.
     * @param column the column value of the coordinate.
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row value of this coordinate.
     *
     * @return the row value.
     */
    public int getRow() {
        return row;
    }

    /**
     * Updates the row value of this coordinate.
     *
     * @param row the new row value.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column value of this coordinate.
     *
     * @return the column value.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Updates the column value of this coordinate.
     *
     * @param column the new column value.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Determines whether the specified object is equal to this coordinate.
     * Two coordinates are considered equal if their row and column values are the same.
     *
     * @param o the object to compare for equality.
     * @return {@code true} if the specified object is equal to this coordinate, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate coordinate)) return false;
        return row == coordinate.row && column == coordinate.column;
    }

    /**
     * Returns a hash code value for this coordinate.
     * The hash code is computed based on the row and column values.
     *
     * @return the hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Returns a string representation of this coordinate.
     * The format is "(row;column)".
     *
     * @return a string representation of this coordinate.
     */
    @Override
    public String toString() {
        return "(" + row + ";" + column + ")";
    }
}
