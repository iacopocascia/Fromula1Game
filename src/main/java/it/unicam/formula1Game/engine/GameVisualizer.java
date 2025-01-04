package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.Player;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality to visualize the current state of the game, including the {@link RaceTrack}
 * and the players' statuses.
 */
public class GameVisualizer {
    /**
     * Generates a visual representation of the game's current state, including the {@link RaceTrack} grid
     * and players' positions.
     *
     * @param raceTrack the {@link RaceTrack} object to visualize.
     * @param players   the list of {@link Player} objects to include in the visualization.
     * @return a <code>String</code> representation of the game's state.
     */
    public static String visualizeGame(RaceTrack raceTrack, List<? extends Player> players) {
        StringBuilder sb = new StringBuilder();
        // Place the players on the grid (if they haven't crashed)
        placePlayersOnTrack(raceTrack, players);
        // Build the grid output and add it to the StringBuilder
        appendGridToStringBuilder(sb, raceTrack);
        // Add players' status (whether they are still at the start or have crashed)
        appendPlayerStatus(sb, raceTrack, players);
        return sb.toString();
    }

    /**
     * Appends the status of players to the <code>StringBuilder</code>, indicating their positions or crash statuses.
     *
     * @param sb      the <code>StringBuilder</code> used to build the final output.
     * @param players the list of {@link Player} objects to include in the status output.
     */
    private static void appendPlayerStatus(StringBuilder sb, RaceTrack raceTrack, List<? extends Player> players) {
        boolean allPlayersOnStart = true;
        List<Integer> crashedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.hasCrashed()) {
                crashedPlayers.add(player.getId());
            } else {
                // If the player has moved from the start, they are no longer all on the start line
                Coordinate position = player.getPosition();
                if (!raceTrack.getGrid()[position.getRow()][position.getColumn()].cellType().equals(CellType.START)) {
                    allPlayersOnStart = false;
                }
            }
        }
        // If all players are still at the start, add a message
        if (allPlayersOnStart) {
            sb.append("Players on their marks\n");
        }
        // If there are crashed players, add a list of crashed players
        if (!crashedPlayers.isEmpty()) {
            sb.append("Crashed players: ");
            for (int id : crashedPlayers) {
                sb.append(id).append(" ");
            }
            sb.append("\n");
        }
    }


    /**
     * Appends the visual representation of the track grid to the <code>StringBuilder</code>.
     *
     * @param sb        the <code>StringBuilder</code> used to build the final output.
     * @param raceTrack the {@link RaceTrack} to visualize.
     */
    private static void appendGridToStringBuilder(StringBuilder sb, RaceTrack raceTrack) {
        for (int row = 0; row < raceTrack.getHeight(); row++) {
            for (int col = 0; col < raceTrack.getWidth(); col++) {
                sb.append(raceTrack.getVisualGridRepresentation()[row][col]);  // Append each cell
            }
            sb.append("\n");  // Newline after each row
        }
    }

    /**
     * Places the players on the track's visual grid representation, replacing the cell with the player's ID
     * if they haven't crashed.
     *
     * @param raceTrack the {@link RaceTrack} to update.
     * @param players   the list of {@link Player} objects to place on the grid.
     */
    private static void placePlayersOnTrack(RaceTrack raceTrack, List<? extends Player> players) {
        for (Player player : players) {
            if (!player.hasCrashed()) {
                int row = player.getPosition().getRow();
                int column = player.getPosition().getColumn();
                raceTrack.getVisualGridRepresentation()[row][column] = String.valueOf(player.getId());  // Place player ID on track
            }
        }

    }
}
