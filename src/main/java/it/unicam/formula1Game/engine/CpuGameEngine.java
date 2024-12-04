package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.strategyFactory.StrategyFactory;
import it.unicam.formula1Game.strategy.strategyFactory.StrategyFactorySelector;

/**
 * todo
 */
public class CpuGameEngine implements GameEngine{
    private GameStrategy[] strategies;
    /**
     *
     */
    @Override
    public void initializeEnvironment(RaceTrack raceTrack) {
        try{
            raceTrack.placeCpuPlayers();
        } catch (InvalidConfigurationException e) {
           System.out.println("An error occurred during players placement");
        }
        setStrategies(raceTrack);
    }

    /**
     *
     * @param raceTrack
     */

    private void setStrategies(RaceTrack raceTrack) {
        int numberOfPlayers = raceTrack.getNumberOfPlayers();
        strategies = new GameStrategy[numberOfPlayers];
        StrategyFactory factory;
        for (int i = 0; i < numberOfPlayers; i++) {
            CpuPlayer player = raceTrack.getCpuPlayers().get(i);
            factory = StrategyFactorySelector.selectFactory(i);
            strategies[i] = factory.createStrategy(player, raceTrack);
        }
    }

    /**
     *
     */
    @Override
    public void startGame() {

    }

    /**
     *
     */
    @Override
    public void endGame() {

    }

    /**
     * @return
     */
    @Override
    public boolean checkCrash() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean checkWinCondition() {
        return false;
    }
}
