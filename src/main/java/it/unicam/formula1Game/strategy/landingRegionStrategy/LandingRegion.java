package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;

import java.util.Set;

public class LandingRegion {
    private final Set<Coordinate> cells;
    private int width;
    private int height;

    public LandingRegion(Set<Coordinate> cells) {
        this.cells = cells;
        this.width=0;
        this.height=0;
    }

    public void addNewCell(Coordinate coordinate) {
        this.cells.add(coordinate);
    }
    /**
     * Calculates and caches the width of the landing region (difference in columns).
     *
     * @return The width of the landing region.
     */
    public int getWidth() {
        if(width==0){
            calculateDimensions();
        }
        return width;
    }
    /**
     * Calculates and caches the height of the landing region (difference in rows).
     *
     * @return The height of the landing region.
     */
    public int getHeight() {
        if(height==0){
            calculateDimensions();
        }
        return height;
    }

    public Set<Coordinate> getCells() {
        return cells;
    }

    /**
     * Calculates the width and height of the landing region based on its cells
     * and caches the results.
     */
    private void calculateDimensions() {
        int minRow = cells.stream().mapToInt(Coordinate::getRow).min().orElse(0);
        int maxRow = cells.stream().mapToInt(Coordinate::getRow).max().orElse(0);
        int minCol = cells.stream().mapToInt(Coordinate::getColumn).min().orElse(0);
        int maxCol = cells.stream().mapToInt(Coordinate::getColumn).max().orElse(0);

        this.width = maxCol - minCol + 1;
        this.height = maxRow - minRow + 1;
    }

}
