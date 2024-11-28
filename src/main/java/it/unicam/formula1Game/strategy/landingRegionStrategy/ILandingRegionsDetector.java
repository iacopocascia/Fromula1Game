package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.List;

/**
 * todo
 */
public interface ILandingRegionsDetector {
    /**
     *
     * @param raceTrack
     * @return
     */
    List<Segment> detectLandingRegions(RaceTrack raceTrack);
}

