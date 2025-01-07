package it.unicam.formula1Game.racetrack;

import it.unicam.formula1Game.cell.Cell;
import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a racetrack in the game, including its grid, size, number of players, and race direction.
 * Provides methods to access and manipulate the track's structure, including the start and finish lines.
 */
public class RaceTrack {
    /**
     * The track's total width
     */
    private final int width;
    /**
     * The track's total height
     */
    private final int height;
    /**
     * Represents the track as a 2D grid of {@link Cell} objects.
     */
    private final Cell[][] grid;
    /**
     * The number of players taking part in the race
     */
    private final int numberOfPlayers;
    /**
     * The direction of the race, either clockwise ("cw") or counter-clockwise ("ccw").
     */
    private final String direction;
    /**
     * The track's visual representation
     */
    private final String[][] visualGridRepresentation;

    /**
     * Creates a new racetrack with the specified dimensions, grid, number of players, and race direction.
     *
     * @param width           The width of the racetrack.
     * @param height          The height of the racetrack.
     * @param grid            The 2D grid of cells representing the track.
     * @param numberOfPlayers The number of players participating in the race.
     * @param direction       The direction of the race ("cw" for clockwise or "ccw" for counter-clockwise).
     * @throws InvalidConfigurationException if the track's configuration is invalid.
     */
    public RaceTrack(int width, int height, Cell[][] grid, int numberOfPlayers, String direction) throws InvalidConfigurationException {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.numberOfPlayers = numberOfPlayers;
        this.direction = direction;
        this.visualGridRepresentation = buildTrackRepresentation();
    }

    /**
     * Retrieves the {@link Cell} at the specified position on the racetrack grid.
     *
     * @param position The {@link Coordinate} object representing the position of the cell to retrieve.
     * @return The {@link Cell} located at the specified position on the racetrack.
     * @throws IllegalArgumentException If the specified position is out of the track boundaries.
     */
    public Cell getCellAt(Coordinate position) {
        int row = position.getRow();
        int column = position.getColumn();
        if (row < 0 || row >= this.height || column < 0 || column >= this.width) {
            throw new IllegalArgumentException("Position out of track boundaries");
        }
        return grid[row][column];
    }

    /**
     * Gets all the <code>START</code> cell positions in the track.
     *
     * @return A {@link List} of {@link Coordinate} objects representing the start cells.
     * @throws InvalidConfigurationException If no start cells are found on the track.
     */
    public List<Coordinate> getStartCoordinates() throws InvalidConfigurationException {
        List<Coordinate> startCoordinates = new ArrayList<>();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (grid[i][j].cellType() == CellType.START) {
                    startCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        if (startCoordinates.isEmpty()) {
            throw new InvalidConfigurationException("No start cells found for this track");
        }
        return startCoordinates;
    }

    /**
     * Gets all the <code>FINISH</code> cell positions in the track.
     *
     * @return A {@link List} of {@link Coordinate} objects representing the finish cells.
     */
    public List<Coordinate> getFinishCoordinates() {
        List<Coordinate> finishCoordinates = new ArrayList<>();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (grid[i][j].cellType() == CellType.FINISH) {
                    finishCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        return finishCoordinates;
    }

    /**
     * Checks whether a certain position is within the track's boundaries.
     *
     * @param position The {@link Coordinate} object to check.
     * @return <code>true</code> if it is within the boundaries, <code>false</code> otherwise.
     */
    public boolean isWithinBoundaries(Coordinate position) {
        return position.getRow() < this.height && position.getRow() >= 0
                && position.getColumn() < this.width && position.getColumn() >= 0;
    }

    /**
     * Gets the width of the track.
     *
     * @return The width of the track.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the track.
     *
     * @return The height of the track.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the 2D grid representation of the track.
     *
     * @return The 2D array of {@link Cell} objects representing the grid.
     */
    public Cell[][] getGrid() {
        return this.grid;
    }

    /**
     * Gets the direction of the race (either "cw" for clockwise or "ccw" for counter-clockwise).
     *
     * @return The race direction.
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     * Gets the number of players taking part in the race.
     *
     * @return The number of players.
     */
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    /**
     * Returns the visual 2D grid representation of the track (without players).
     *
     * @return The 2D array of strings representing the visual grid of the racetrack.
     */
    public String[][] getVisualGridRepresentation() {
        return this.visualGridRepresentation;
    }

    /**
     * Builds the visual 2D representation of the track, excluding players.
     * This representation is a static view of the track.
     *
     * @return A 2D array of strings representing the static grid of the track.
     */
    public String[][] buildTrackRepresentation() {
        String[][] trackRepresentation = new String[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                CellType cell = grid[row][column].cellType();
                switch (cell) {
                    case WALL:
                        trackRepresentation[row][column] = "*"; // Outer Wall
                        break;
                    case START:
                        trackRepresentation[row][column] = "+";  // Start line
                        break;
                    case FINISH:
                        trackRepresentation[row][column] = "-";  // Finish line
                        break;
                    case TRACK:
                        trackRepresentation[row][column] = " ";  // Track (empty space)
                        break;
                }
            }
        }
        return trackRepresentation;
    }
}
