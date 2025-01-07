package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;

import java.util.List;
import java.util.Set;

/**
 * The {@code GameStrategy} interface defines the contract for implementing a game strategy.
 * Implementations of this interface will provide different strategies for CPU players to decide their next move.
 */
public interface GameStrategy {
    /**
     * Applies the defined strategy to the within the game context.
     */
    void applyStrategy(CpuPlayer player);

    /**
     * Computes all the available moves that a player can make from their current position,
     * based on the rules of the game.
     *
     * @return A {@link List} of {@link Coordinate} objects representing the valid moves.
     */
    Set<Coordinate> getAvailableMoves(CpuPlayer player);

    /**
     * Checks if the specified player has crashed based on their current position and game context.
     * If so sets the <code>hasCrashed</code> field of the <code>CpuPlayer</code> class to <code>true</code>.
     *
     * @param player The {@link CpuPlayer} to check.
     */
    void checkHasCrashed(CpuPlayer player);
}
