package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.List;

/**
 * A processor class that combines the functionality of {@link LandingRegionsDetector}
 * and {@link LandingRegionsElaborator} to detect and elaborate landing regions
 * in a {@link it.unicam.formula1Game.racetrack.RaceTrack}.
 */
public class LandingRegionsProcessor {
    //private final RaceTrack raceTrack;
    private final ILandingRegionsDetector detector;
    private final ILandingRegionsElaborator elaborator;

    public LandingRegionsProcessor(RaceTrack raceTrack,ILandingRegionsDetector detector, ILandingRegionsElaborator elaborator) {
        //this.raceTrack = raceTrack;
        this.detector = detector;
        this.elaborator = elaborator;
    }
    /**
     * Detects and elaborates landing regions in the racetrack, returning
     * a prioritized list of {@link Coordinate}s that represent the path to follow.
     *
     * @return a {@link List} of {@link Coordinate} objects representing the prioritized path.
     */
    public List<Coordinate> processLandingRegions(RaceTrack raceTrack) {
        // Detect landing regions
        List<Segment> landingRegions = detector.detectLandingRegions(raceTrack);

        // Elaborate the detected landing regions
        return elaborator.elaborateLandingRegions(landingRegions, raceTrack);
    }
}
