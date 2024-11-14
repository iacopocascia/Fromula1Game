package racetrack;
import cell.Cell;
import players.Player;

import java.util.ArrayList;
import java.util.List;

public class RaceTrack {
    //TODO valutare se inserire o meno i campi width e height perchè sono ricavabili dalla grid
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
    private final Player[] players;
    /**
     *
     * @param width
     * @param height
     * @param grid
     */

    public RaceTrack(int width, int height, Cell[][] grid, Player[] players) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.players = players;
    }
}
