package it.unicam.formula1Game.parser.weightedRandomStrategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

/**
 * A utility class that provides features for the {@link WeightedRandomStrategy} class.
 */
public class WeightedRandomStrategyUtils {
    /**
     * Weight for cell type importance in move evaluation.
     */
    public static final double CELL_TYPE_WEIGHT = 0.6;
    /**
     * Weight for border proximity importance in move evaluation.
     */
    public static final double BORDER_WEIGHT = 0.3;

    /**
     * Weight for velocity factor importance in move evaluation.
     */
    public static final double VELOCITY_WEIGHT = 0.1;
    /**
     * Ideal velocity for smoother gameplay.
     */
    public static final double IDEAL_VELOCITY = 2.0;
    /**
     * Penalty constant for the "stay in place" choice.
     */
    public static final double STAY_IN_PLACE_PENALTY = -10.0;
    /**
     * Standard deviation for Gaussian-like velocity penalty calculation.
     */
    public static final double SIGMA_VALUE = 1.0;

    /**
     * Calculates the distance of a move from the closest border.
     *
     * @param move The move to evaluate.
     * @return The distance from the closest border.
     */
    public static int calculateDistanceFromBorders(Coordinate move, RaceTrack raceTrack) {
        int distanceToTop = calculateDistanceInDirection(move, -1, 0, raceTrack);
        int distanceToRight = calculateDistanceInDirection(move, 0, 1, raceTrack);
        int distanceToBottom = calculateDistanceInDirection(move, 1, 0, raceTrack);
        int distanceToLeft = calculateDistanceInDirection(move, 0, -1, raceTrack);
        return Math.min(Math.min(distanceToTop, distanceToBottom), Math.min(distanceToLeft, distanceToRight));
    }

    /**
     * Calculates the distance from a given move to the nearest wall in a specified direction.
     *
     * @param move      The starting coordinate.
     * @param rowStep   The row increment for the direction (-1 for up, 1 for down, 0 for no vertical movement).
     * @param colStep   The column increment for the direction (-1 for left, 1 for right, 0 for no horizontal movement).
     * @param raceTrack The {@link RaceTrack} object where the game takes place.
     * @return The distance to the nearest wall in the specified direction.
     */
    public static int calculateDistanceInDirection(Coordinate move, int rowStep, int colStep, RaceTrack raceTrack) {
        int distance = 0;
        boolean found = false;
        while (!found) {
            distance++;
            Coordinate next = new Coordinate(move.getRow() + distance * rowStep, move.getColumn() + distance * colStep);
            if (raceTrack.getCellAt(next).cellType() == CellType.WALL) {
                found = true;
            }
        }
        return distance;
    }
}
