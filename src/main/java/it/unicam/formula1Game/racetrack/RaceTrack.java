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
    //TODO valutare se inserire o meno i campi width, height e numPlayers
    // perchè sono ricavabili dalla grid

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
     * The players that take part in the race
     */
    private final Player[] players;

    /**
     * Creates a new racetrack and initializes the players info
     * @param width
     * @param height
     * @param grid
     */

    public RaceTrack(int width, int height, Cell[][] grid, int numberOfPlayers) throws InvalidConfigurationException {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[numberOfPlayers];
        placePlayers();
    }

    /**
     *
     * @param position
     * @return
     */
    public Cell getCellAt(Coordinate position){
        int row=position.getRow();
        int column=position.getColumn();
        if(row < 0 || row >= this.height || column < 0 || column >= this.width){
            throw new IllegalArgumentException("Position out of track boundaries");
        }
        return grid[row][column];
    }

    /**
     * Initializes and places the players on the start line.
     * Each player is assigned a random ID between 1 and 10, and players are evenly placed across the start line.
     */
    private void placePlayers() throws InvalidConfigurationException {
        // Generate unique IDs for the players
        List<Integer> playerIds = generateUniquePlayerIds();
        // Get all START cells from the track
        List<Coordinate> startLine = getStartCoordinates();
        // Assign players to START cells in a round-robin fashion
        for (int i = 0; i < this.numberOfPlayers; i++) {
            int playerId = playerIds.get(i);
            Coordinate startPosition = startLine.get(i % startLine.size()); // Cycle through start line positions
            // Create a player and place it on the track
            this.players[i] = new CpuPlayer(playerId, new Coordinate(startPosition.getRow(), startPosition.getColumn()));

        }

    }

    /**
     * Generates a list of unique random IDs between 1 and 10 for the given number of players.
     *
     * @return a list of unique player IDs
     */
    private List<Integer> generateUniquePlayerIds() {
        List<Integer> availableIds = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            availableIds.add(i);  // Add IDs from 1 to 10
        }

        Collections.shuffle(availableIds);  // Shuffle the list to randomize IDs

        return availableIds.subList(0, this.numberOfPlayers);  // Return only the number of IDs needed for the players
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
     * @return A {@link List} of {@link Coordinate} of the finish cells.
     */
    public List<Coordinate> getFinishCoordinates(){
        List<Coordinate> finishCoordinates=new ArrayList<>();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if(grid[i][j].cellType()==CellType.FINISH){
                    finishCoordinates.add(new Coordinate(i,j));
                }
            }
        }
        return finishCoordinates;
    }

    /**
     * Checks whether a certain position is within the track's boundaries.
     * @param position The {@link Coordinate} object to check.
     * @return <code>true</code> if it is within the boundaries, <code>false</code> otherwise.
     */
    public boolean isWithinBoundaries(Coordinate position) {
        return position.getRow() <= this.height && position.getRow() >= 0
                && position.getColumn() <= this.width && position.getColumn() >= 0;
    }
}
