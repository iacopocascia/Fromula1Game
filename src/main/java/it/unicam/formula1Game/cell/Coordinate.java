package it.unicam.formula1Game.cell;

import it.unicam.formula1Game.racetrack.RaceTrack;

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
}
