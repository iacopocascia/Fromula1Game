package it.unicam.formula1Game.strategy.strategyFactory;

import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.RandomStrategy;

/**
 * Factory for creating a {@link RandomStrategy}.
 */
public class RandomStrategyFactory implements StrategyFactory {
    @Override
    public GameStrategy createStrategy(CpuPlayer player, RaceTrack raceTrack) {
        return new RandomStrategy(player,raceTrack);
    }
}
