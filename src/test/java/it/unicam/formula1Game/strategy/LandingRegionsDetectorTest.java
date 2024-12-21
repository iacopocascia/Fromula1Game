package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.Segment;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;

public class LandingRegionsDetectorTest {
    private final JsonParser parser=new JsonParser();
    private final RaceTrack raceTrack = parser.parse(new File(filePath));
    private final LandingRegionsDetector detector=new LandingRegionsDetector();

    public LandingRegionsDetectorTest() throws InvalidConfigurationException {
    }

    @Test
    public void detect_landing_regions_test(){
        List<Segment> landingRegions=detector.detectLandingRegions(raceTrack);
        for (Segment segment:landingRegions){
            System.out.println("Segment: "+segment.toString());
        }
    }
}
