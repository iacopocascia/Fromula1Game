package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsElaborator;
import it.unicam.formula1Game.strategy.landingRegionStrategy.Segment;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;

public class LandingRegionsElaboratorTest {
    private final JsonParser parser = new JsonParser();
    private final RaceTrack raceTrack = parser.parse(new File(filePath));
    private final List<Segment> landingRegions = new LandingRegionsDetector().detectLandingRegions(raceTrack);
    private final LandingRegionsElaborator elaborator = new LandingRegionsElaborator();

    public LandingRegionsElaboratorTest() throws InvalidConfigurationException {
    }
    @Test
    public void elaborate_landing_regions_test(){
        List<Coordinate> path=elaborator.elaborateLandingRegions(landingRegions,raceTrack);
        System.out.println(path.toString());
    }

}
