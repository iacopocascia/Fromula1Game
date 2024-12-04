package it.unicam.formula1Game.strategy.strategyFactory;

import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;

/**
 * Factory interface for creating game strategies.
 */
public interface StrategyFactory {
    /**
     * Creates a game strategy for the given player and track.
     *
     * @param player    The {@link CpuPlayer} for whom the strategy is created.
     * @param raceTrack The {@link RaceTrack} representing the game environment.
     * @return The created {@link GameStrategy}.
     */
    GameStrategy createStrategy(CpuPlayer player, RaceTrack raceTrack);
}
