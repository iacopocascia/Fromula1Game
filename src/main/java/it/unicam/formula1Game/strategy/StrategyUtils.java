package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.HashSet;
import java.util.Set;
/**
 * A utility class that provides common methods for game strategies.
 */
public class StrategyUtils {
    /**
     * Calculates the theoretical velocity resulting from moving to a given coordinate.
     *
     * @param currentPosition The player's current position.
     * @param move            The move to evaluate.
     * @return The theoretical velocity resulting from the move.
     */
    public static double calculateTheoreticalVelocity(Coordinate currentPosition, Coordinate move) {
        return Math.sqrt(
                Math.pow(move.getRow() - currentPosition.getRow(), 2) +
                        Math.pow(move.getColumn() - currentPosition.getColumn(), 2)
        );
    }

    /**
     * Calculates the Manhattan distance between two coordinates.
     *
     * @param a The first coordinate.
     * @param b The second coordinate.
     * @return The distance value between the two coordinates.
     */
    public static int calculateDistance(Coordinate a, Coordinate b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getColumn() - b.getColumn());
    }

    /**
     * Computes all possible moves from the given principal point that are within the racetrack's boundaries.
     * The available moves are determined by checking all adjacent cells relative to the principal point.
     *
     * @param principalPoint The player's principal point on the track.
     * @param raceTrack      The {@link RaceTrack} where the game is being played.
     * @return A {@link Set} of {@link Coordinate} objects representing valid moves from the principal point.
     */
    public static Set<Coordinate> getAvailableMoves(Coordinate principalPoint, RaceTrack raceTrack){
        Set<Coordinate> moves = new HashSet<>();
        for (int rowShift = -1; rowShift <= 1; rowShift++) {
            for (int colShift = -1; colShift <= 1; colShift++) {
                Coordinate move = new Coordinate(principalPoint.getRow() + rowShift, principalPoint.getColumn() + colShift);
                if (raceTrack.isWithinBoundaries(move)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

}
