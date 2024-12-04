package it.unicam.formula1Game.strategy.strategyFactory;

import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsElaborator;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsStrategy;

/**
 * Factory for creating a {@link LandingRegionsStrategy}.
 */
public class LandingRegionsStrategyFactory implements StrategyFactory {
    @Override
    public GameStrategy createStrategy(CpuPlayer player, RaceTrack raceTrack) {
        return new LandingRegionsStrategy(raceTrack,
                player,
                new LandingRegionsDetector(raceTrack),
                new LandingRegionsElaborator(raceTrack));
    }
}
