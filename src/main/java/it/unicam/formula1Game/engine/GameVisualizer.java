package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.player.Player;
import it.unicam.formula1Game.racetrack.RaceTrack;

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
     * Appends the status of players to the <code>StringBuilder</code>, indicating their positions,
     * crash statuses, or a summary of all players at the end of the game.
     *
     * @param sb        the <code>StringBuilder</code> used to build the final output.
     * @param raceTrack the {@link RaceTrack} object representing the game grid.
     * @param players   the list of {@link Player} objects to include in the status output.
     */
    private static void appendPlayerStatus(StringBuilder sb, RaceTrack raceTrack, List<? extends Player> players) {
        appendPlayersOnStartMessage(sb, raceTrack, players);
        appendCrashedPlayers(sb, players);
        appendPlayerSummaries(sb, players);
    }
    /**
     * Appends a message to the <code>StringBuilder</code> if all players are still on the start line.
     *
     * @param sb        the <code>StringBuilder</code> used to build the final output.
     * @param raceTrack the {@link RaceTrack} object representing the game grid.
     * @param players   the list of {@link Player} objects to check.
     */
    private static void appendPlayersOnStartMessage(StringBuilder sb, RaceTrack raceTrack, List<? extends Player> players) {
        boolean allPlayersOnStart = players.stream().allMatch(player ->
                !player.hasCrashed() &&
                        raceTrack.getGrid()[player.getPosition().getRow()][player.getPosition().getColumn()]
                                .cellType()
                                .equals(CellType.START)
        );
        if (allPlayersOnStart) {
            sb.append("**************PLAYERS ON THEIR MARKS**************\n");
        }
    }
    /**
     * Appends a list of crashed players to the <code>StringBuilder</code>.
     *
     * @param sb      the <code>StringBuilder</code> used to build the final output.
     * @param players the list of {@link Player} objects to check for crashes.
     */
    private static void appendCrashedPlayers(StringBuilder sb, List<? extends Player> players) {
        List<Integer> crashedPlayers = players.stream()
                .filter(Player::hasCrashed)
                .map(Player::getId)
                .toList();
        if (!crashedPlayers.isEmpty()) {
            sb.append("CRASHED PLAYERS: ").append(String.join(" ", crashedPlayers.stream()
                    .map(String::valueOf)
                    .toArray(String[]::new))).append("\n");
        }
    }
    /**
     * Appends a summary of all players' statuses using their <code>toString</code> representation,
     * but only includes players who have not crashed.
     *
     * @param sb      the <code>StringBuilder</code> used to build the final output.
     * @param players the list of {@link Player} objects to summarize.
     */
    private static void appendPlayerSummaries(StringBuilder sb, List<? extends Player> players) {
        sb.append("\n**************PLAYERS' STATUS**************\n");
        players.stream()
                .filter(player -> !player.hasCrashed()) // Include only players who have not crashed
                .forEach(player -> sb.append(player).append("\n"));
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
     * if they haven't crashed. Resets the grid to its initial state before updating.
     *
     * @param raceTrack the {@link RaceTrack} to update.
     * @param players   the list of {@link Player} objects to place on the grid.
     */
    private static void placePlayersOnTrack(RaceTrack raceTrack, List<? extends Player> players) {
        // Reset the visual grid to its initial state
        resetTrackVisualRepresentation(raceTrack);
        for (Player player : players) {
            if (!player.hasCrashed()) {
                int row = player.getPosition().getRow();
                int column = player.getPosition().getColumn();
                raceTrack.getVisualGridRepresentation()[row][column] = String.valueOf(player.getId());  // Place player ID on track
            }
        }
    }

    /**
     * Resets the track's visual representation to its initial state (without player positions).
     *
     * @param raceTrack the {@link RaceTrack} whose visual grid is being reset.
     */
    private static void resetTrackVisualRepresentation(RaceTrack raceTrack) {
        String[][] initialRepresentation = raceTrack.buildTrackRepresentation();
        for (int row = 0; row < raceTrack.getHeight(); row++) {
            System.arraycopy(initialRepresentation[row], 0, raceTrack.getVisualGridRepresentation()[row], 0, raceTrack.getWidth());
        }
    }
}
