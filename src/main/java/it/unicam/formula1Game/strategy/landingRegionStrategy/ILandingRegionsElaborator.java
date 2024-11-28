package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.List;

public interface ILandingRegionsElaborator {
    /**
     * todo
     *
     * @param landingRegions
     * @param raceTrack
     * @return
     */
    List<Coordinate> elaborateLandingRegions(List<Segment> landingRegions, RaceTrack raceTrack);
}
