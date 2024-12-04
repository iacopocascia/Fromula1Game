package it.unicam.formula1Game.racetrack;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RaceTrackVisualizer {
    /**
     Generates a visual representation of the {@link RaceTrack} object at the present state.
     * @param raceTrack the {@link RaceTrack} object to visualize.
     * @return a <code>String</code> value representing {@link RaceTrack} object and the players.
     */
    public static String visualizeRacetrack(RaceTrack raceTrack) {
        StringBuilder sb = new StringBuilder();
        // Place the players on the grid (if they haven't crashed)
        placePlayersOnTrack(raceTrack);
        // Build the grid output and add it to the StringBuilder
        appendGridToStringBuilder(sb, raceTrack);
        // Add players' status (whether they are still at the start or have crashed)
        appendPlayerStatus(sb, raceTrack);
        return sb.toString();
    }

    /**
     * Appends the status of players to the <code>StringBuilder</code>, indicating whether players are still at the start
     * or if there are any crashed players.
     *
     * @param sb the <code>StringBuilder</code> used to build the final output
     */
    private static void appendPlayerStatus(StringBuilder sb, RaceTrack raceTrack) {
        boolean allPlayersOnStart = true;
        List<Integer> crashedPlayers = new ArrayList<>();
        for (Player player : raceTrack.getPlayers()) {
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
     * Appends the grid representation to the StringBuilder.
     *
     * @param sb the StringBuilder used to build the final output.
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
     * Places the players on the visual track grid representation, replacing the cell with the player's ID
     * if they haven't crashed.
     */
    private static void placePlayersOnTrack(RaceTrack raceTrack) {
        for (Player player : raceTrack.getPlayers()) {
            if (!player.hasCrashed()) {
                int row = player.getPosition().getRow();
                int column = player.getPosition().getColumn();
                raceTrack.getVisualGridRepresentation()[row][column] = String.valueOf(player.getId());  // Place player ID on track
            }
        }

    }
}
