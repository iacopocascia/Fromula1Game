package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
//TODO javadoc
public class WeightedMove {
    private final Coordinate coordinate;
    private final double weight;

    public WeightedMove(Coordinate coordinate, double weight) {
        this.coordinate = coordinate;
        this.weight = weight;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getWeight() {
        return weight;
    }
}
