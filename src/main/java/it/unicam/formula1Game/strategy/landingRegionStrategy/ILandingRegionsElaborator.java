package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;

import java.util.List;
/**
 * The {@code ILandingRegionsElaborator} interface defines the contract for elaborating landing regions
 * detected on a racetrack.
 */
public interface ILandingRegionsElaborator {
    /**
     * Processes and organizes the detected landing regions for strategic use.
     *
     * @param landingRegions A {@link List} of {@link Segment} objects representing the detected landing regions.
     * @return A {@link List} of {@link Coordinate} objects representing the processed landing regions.
     */
    List<Coordinate> elaborateLandingRegions(List<Segment> landingRegions);
}
