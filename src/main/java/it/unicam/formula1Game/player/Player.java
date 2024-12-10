package it.unicam.formula1Game.player;

import it.unicam.formula1Game.cell.Coordinate;

/**
 * The {@code Player} interface represents the contract for a player in the game.
 */
public interface Player {
    /**
     * Moves the player to the position specified as an argument.
     *
     * @param move The position where the player will be moved.
     */
    void makeMove(Coordinate move);

    /**
     * Calculates the "principal point" according to the game's rules.
     *
     * @return The principal point as a {@link Coordinate} object.
     */
    Coordinate calculatePrincipalPoint();

    /**
     * Checks whether the player has crashed or not.
     *
     * @return <code>true</code> if the player has crashed, <code>false</code> otherwise.
     */
    boolean hasCrashed();

    /**
     * Gets the player's position on the {@link it.unicam.formula1Game.racetrack.RaceTrack}.
     *
     * @return a {@link Coordinate} object representing the player's position.
     */
    Coordinate getPosition();

    /**
     * Gets the player's unique identifier.
     *
     * @return the <code>Integer</code> value representing the player.
     */
    int getId();

}
