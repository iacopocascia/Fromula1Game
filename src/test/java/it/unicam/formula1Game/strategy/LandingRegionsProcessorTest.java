package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsElaborator;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsProcessor;
import org.junit.jupiter.api.Test;

import java.io.File;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;

public class LandingRegionsProcessorTest {
    private final JsonParser parser = new JsonParser();
    private final RaceTrack raceTrack = parser.parse(new File(filePath));
    private final LandingRegionsProcessor processor=new LandingRegionsProcessor(new LandingRegionsDetector(),new LandingRegionsElaborator());

    public LandingRegionsProcessorTest() throws InvalidConfigurationException {
    }
    @Test
    public void process_landing_regions_test(){
        System.out.println(this.processor.processLandingRegions(this.raceTrack).toString());
    }
}
