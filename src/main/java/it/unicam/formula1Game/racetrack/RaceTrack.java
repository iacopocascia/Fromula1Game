package it.unicam.formula1Game.racetrack;

import it.unicam.formula1Game.cell.Cell;
import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * Represents the track as a 2D grid of {@link Cell}
     */
    private final Cell[][] grid;
    /**
     * The number of players taking part in the race
     */
    private final int numberOfPlayers;
    /**
     * The direction of the race: clockwise or counter-clockwise
     */
    private final String direction;
    /**
     * The track's visual representation
     */
    private final String[][] visualGridRepresentation;

    /**
     * Creates a new racetrack
     */

    public RaceTrack(int width, int height, Cell[][] grid, int numberOfPlayers, String direction) throws InvalidConfigurationException {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.numberOfPlayers = numberOfPlayers;
        //this.players = new Player[numberOfPlayers];
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
     * Gets all the <code>START</code> cells positions in the track.
     *
     * @return A {@link List} of {@link Coordinate} of the starting cells.
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
     * Gets all the <code>FINISH</code> cells positions in the track.
     *
     * @return A {@link List} of {@link Coordinate} of the finish cells.
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
        return position.getRow() <= this.height && position.getRow() >= 0
                && position.getColumn() <= this.width && position.getColumn() >= 0;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Cell[][] getGrid() {
        return this.grid;
    }

    public String getDirection() {
        return this.direction;
    }

    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public String[][] getVisualGridRepresentation() {
        return visualGridRepresentation;
    }

    /**
     * Calls the static method <code>visualizeRaceTrack()</code> of the {@link RaceTrackVisualizer} class.
     */
    @Override
    public String toString() {
        return RaceTrackVisualizer.visualizeRacetrack(this);
    }

    /**
     * Builds a 2D array of strings representing the static part of the track grid
     * without players.
     *
     * @return a 2D array of strings representing the grid
     */
    private String[][] buildTrackRepresentation() {
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
