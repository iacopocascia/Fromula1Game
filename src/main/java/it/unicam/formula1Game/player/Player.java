package it.unicam.formula1Game.player;

import it.unicam.formula1Game.cell.Coordinate;

/**
 * The {@code Player} interface represents the contract for a player in the game.
 */
public interface Player {
    /**
     * Moves the player to a new position based on the provided shifts.
     * @param horizontalShift Specifies the horizontal shift from the "principal point"
     * @param verticalShift Specifies the vertical shift from the "principal point"
     * @return The {@link Coordinate} corresponding to the new position occupied
     */
    Coordinate makeMove(int horizontalShift, int verticalShift);

    /**
     * Calculates the "principal point" according to the game's rules.
     * @return The principal point as a {@link Coordinate} object.
     */
    Coordinate calculatePrincipalPoint();

}
