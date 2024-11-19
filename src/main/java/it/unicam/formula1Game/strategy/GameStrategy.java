package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;

import java.util.List;

/**
 * The {@code GameStrategy} interface defines the contract for implementing a game strategy.
 * Implementations of this interface will provide different strategies for CPU players to decide their next move.
 */
public interface GameStrategy {
    /**
     * Applies the defined strategy within the game context.
     */
    void applyStrategy();

    /**
     * Computes all the available moves that a player can make from their current position,
     * based on the rules of the game.
     *
     * @return A {@link List} of {@link Coordinate} objects representing the valid moves.
     */
    List<Coordinate> getAvailableMoves();
}
