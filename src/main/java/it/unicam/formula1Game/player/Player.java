package it.unicam.formula1Game.player;

import it.unicam.formula1Game.cell.Coordinate;

import java.util.List;

/**
 * The {@code Player} interface represents the contract for a player in the game.
 */
public interface Player {
    /**
     * Moves the player to the position specified as an argument.
     * @param move The position where the player will be moved.
     */
    void makeMove(Coordinate move);

    /**
     * Calculates the "principal point" according to the game's rules.
     * @return The principal point as a {@link Coordinate} object.
     */
    Coordinate calculatePrincipalPoint();

    /**
     *
     * @return
     */
    List<Coordinate> getAvailableMoves();

}
