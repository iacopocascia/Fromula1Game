package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.List;

/**
 * The {@code ILandingRegionsDetector} interface defines the contract for detecting landing regions
 * on a racetrack. Landing regions represent key areas of the track, such as corners or transitions,
 * where players need to adjust their strategy.
 */
public interface ILandingRegionsDetector {
    /**
     * Detects landing regions on a racetrack.
     *
     * @return A {@link List} of {@link LandingRegion} objects representing the detected landing regions.
     */
    List<LandingRegion> detectLandingRegions(RaceTrack raceTrack);
}

