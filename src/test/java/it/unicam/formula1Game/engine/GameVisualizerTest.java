package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsStrategy;
import it.unicam.formula1Game.strategy.weightedRandomStrategy.WeightedRandomStrategy;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;

public class GameVisualizerTest {
    private final JsonParser parser=new JsonParser();
    private final RaceTrack raceTrack=parser.parse(new File(filePath));
    private final CpuGameEngine gameEngine=new CpuGameEngine();
    private final GameStrategy[] strategies = {
            new WeightedRandomStrategy(raceTrack),
            new LandingRegionsStrategy(raceTrack, new LandingRegionsDetector())
    };
    public GameVisualizerTest() throws InvalidConfigurationException {
    }
    @Test
    public void visualize_game_test(){
        this.gameEngine.setStrategies(Arrays.stream(strategies).toList());
        this.gameEngine.initializeEnvironment(this.raceTrack);
        System.out.println(GameVisualizer.visualizeGame(this.raceTrack, Arrays.stream(this.gameEngine.getPlayers()).toList()));
    }
}
