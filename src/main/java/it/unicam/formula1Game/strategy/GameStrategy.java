package it.unicam.formula1Game.strategy;
/**
 * The {@code GameStrategy} interface defines the contract for implementing a game strategy.
 * Implementations of this interface will provide different strategies for CPU players to decide their next move.
 */
public interface GameStrategy {
    /**
     * Applies the defined strategy within the game context.
     */
    void applyStrategy();
}
