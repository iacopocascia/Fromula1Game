package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.RandomStrategy;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsStrategy;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;
import static org.junit.jupiter.api.Assertions.*;

public class CpuGameEngineTest {
    private final CpuGameEngine gameEngine = new CpuGameEngine();
    private final JsonParser parser = new JsonParser();
    private final RaceTrack raceTrack = parser.parse(new File(filePath));

    public CpuGameEngineTest() throws InvalidConfigurationException {
    }

    @Test
    public void initialize_environment_test() throws InvalidConfigurationException {
        this.gameEngine.initializeEnvironment(this.raceTrack);
        assertEquals(this.raceTrack, this.gameEngine.getRaceTrack());
        assertEquals(this.gameEngine.getPlayers().length, 2);
        assertNotEquals(this.gameEngine.getPlayers()[0], this.gameEngine.getPlayers()[1]);
        Set<Coordinate> playersStartingPositions = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            playersStartingPositions.add(this.gameEngine.getPlayers()[i].getPosition());
        }
        assertTrue(this.gameEngine.getRaceTrack().getStartCoordinates().containsAll(playersStartingPositions));
        if (this.gameEngine.getPlayers()[0].getStrategy() instanceof RandomStrategy) {
            assertInstanceOf(LandingRegionsStrategy.class, this.gameEngine.getPlayers()[1].getStrategy());
        } else {
            assertInstanceOf(RandomStrategy.class, this.gameEngine.getPlayers()[1].getStrategy());
        }
    }

    @Test
    public void make_first_move_test() {
        this.gameEngine.initializeEnvironment(this.raceTrack);
        this.gameEngine.makeFirstMove();
        for (CpuPlayer player : this.gameEngine.getPlayers()) {
            assertEquals(player.getLastMove().getRow(), 0);
            assertEquals(player.getLastMove().getColumn(), -1);
        }
    }

    @Test
    public void check_end_condition_test() throws InvalidConfigurationException {
        this.gameEngine.initializeEnvironment(this.raceTrack);
        assertFalse(this.gameEngine.checkEndCondition());
        this.gameEngine.getPlayers()[0].setPosition(this.raceTrack.getFinishCoordinates().get(1));
        assertTrue(this.gameEngine.checkEndCondition());
        this.gameEngine.getPlayers()[0].setPosition(this.raceTrack.getStartCoordinates().get(1));
        for (CpuPlayer player : this.gameEngine.getPlayers()) {
            player.setHasCrashed(true);
        }
        assertTrue(this.gameEngine.checkEndCondition());
    }

    @Test
    public void end_game_test() {
        this.gameEngine.initializeEnvironment(this.raceTrack);
        this.gameEngine.getPlayers()[0].setPosition(this.raceTrack.getFinishCoordinates().get(1));
        this.gameEngine.checkEndCondition();
        assertEquals(this.gameEngine.endGame(), this.gameEngine.getWinner());
    }
    @Test
    public void start_game_test(){
        this.gameEngine.initializeEnvironment(this.raceTrack);
        this.gameEngine.startGame();

    }



}
