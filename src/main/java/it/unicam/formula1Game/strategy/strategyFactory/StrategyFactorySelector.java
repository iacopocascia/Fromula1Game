package it.unicam.formula1Game.strategy.strategyFactory;
/**
 * Selector for determining which {@link StrategyFactory} to use.
 */
public class StrategyFactorySelector {
    /**
     * Selects a {@link StrategyFactory} based on player index.
     *
     * @param playerIndex The index of the player.
     * @return The selected {@link StrategyFactory}.
     */
    public static StrategyFactory selectFactory(int playerIndex){
        if (playerIndex % 2 == 0) {
            return new RandomStrategyFactory();
        } else {
            return new LandingRegionsStrategyFactory();
        }
    }
}
