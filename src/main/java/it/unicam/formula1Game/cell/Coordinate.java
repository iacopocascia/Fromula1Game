package it.unicam.formula1Game.cell;

import java.util.Objects;
/**
 * Represents a coordinate.
 *
 */
public class Coordinate {
    /**
     * The row component
     */
    private int row;
    /**
     * The column component
     */
    private int column;
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate coordinate)) return false;
        return row == coordinate.row && column == coordinate.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Calculates the Manhattan distance between two coordinates.
     *
     * @param a The first coordinate.
     * @param b The second coordinate.
     * @return The distance value between the two coordinates.
     */
    public static int calculateDistance(Coordinate a, Coordinate b){
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getColumn() - b.getColumn());
    }
}
