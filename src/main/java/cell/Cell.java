package cell;
/**
 * Represents a single cell in the track.
 */
public class Cell {
    /**
     * The type of the cell, represented as a {@link CellType}.
     */
    private final CellType cellType;
    /**
     * The cell's position in the {@link racetrack.RaceTrack} as a {@link Coordinate}.
     */
    private final Coordinate position;

    public Cell(CellType cellType, Coordinate position) {
        this.cellType = cellType;
        this.position = position;
    }

    public CellType getCellType() {
        return cellType;
    }

    public Coordinate getPosition() {
        return position;
    }
}
