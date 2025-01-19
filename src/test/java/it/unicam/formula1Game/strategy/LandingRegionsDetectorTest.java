package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegion;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;

public class LandingRegionsDetectorTest {
    private final JsonParser parser = new JsonParser();
    private final RaceTrack raceTrack = parser.parse(new File(filePath));
    private final LandingRegionsDetector detector = new LandingRegionsDetector();
    private final List<LandingRegion> landingRegions = detector.detectLandingRegions(raceTrack);

    public LandingRegionsDetectorTest() throws InvalidConfigurationException {
    }

    @Test
    public void detect_landing_regions_test() {
        for (int i = 0; i < landingRegions.size(); i++) {
            System.out.println("Landing region " + i + " coordinates: ");
            for (Coordinate coordinate : landingRegions.get(i).getCells()) {
                System.out.println(coordinate.toString());
            }
        }
    }

}

