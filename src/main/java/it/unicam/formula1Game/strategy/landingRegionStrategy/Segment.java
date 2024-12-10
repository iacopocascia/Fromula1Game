package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a segment (both horizontal or vertical) of a
 * {@link it.unicam.formula1Game.racetrack.RaceTrack} object made up of track cells.
 */
public class Segment {
    private final List<Coordinate> points=new ArrayList<>();
    public void addNewPoint(Coordinate point){
        this.points.add(point);
    }

    public List<Coordinate> getPoints() {
        return points;
    }
}
