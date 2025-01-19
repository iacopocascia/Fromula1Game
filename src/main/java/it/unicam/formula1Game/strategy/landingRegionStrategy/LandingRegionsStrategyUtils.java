package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.StrategyUtils;

/**
 * Defines helper methods to handle calculations over movement decisions such as velocity and distance.
 */
public class LandingRegionsStrategyUtils {
    /**
     * Calculates the maximum allowable velocity for a given {@link LandingRegion}.
     * The maximum velocity is determined as the square root of the larger dimension
     * (either width or height) of the next landing region. This ensures that the velocity
     * is capped based on the size of the region.
     * If the landing region is the finish line there is no need to cap the maximum velocity.
     *
     * @param landingRegion The {@link LandingRegion} for which the maximum velocity is calculated.
     * @param raceTrack     The {@link RaceTrack} object where the game takes place.
     * @return The maximum allowable velocity.
     */
    public static double calculateMaxVelocity(LandingRegion landingRegion, RaceTrack raceTrack) {
        if (!landingRegion.getCells().containsAll(raceTrack.getFinishCoordinates())) {
            return Math.sqrt(Math.max(landingRegion.getHeight(), landingRegion.getWidth()));
        }
        return Double.MAX_VALUE; // No cap needed if the next landing region is the finish line
    }

    /**
     * Calculates the minimum distance between a move and the cells in the specified landing region.
     *
     * @param move          The {@link Coordinate} representing the player's potential move.
     * @param landingRegion The {@link LandingRegion} to evaluate against.
     * @return The minimum distance between the move and the cells in the landing region.
     */
    public static int calculateMinimumDistance(Coordinate move, LandingRegion landingRegion) {
        return landingRegion.getCells().stream()
                .mapToInt(coordinate -> StrategyUtils.calculateDistance(move, coordinate))
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    /**
     * Calculates the average distance between a move and the cells in the specified landing region.
     *
     * @param move          The {@link Coordinate} representing the player's potential move.
     * @param landingRegion The {@link LandingRegion} to evaluate against.
     * @return The average distance between the move and the cells in the landing region.
     */
    public static double calculateAverageDistance(Coordinate move, LandingRegion landingRegion) {
        return landingRegion.getCells().stream()
                .mapToInt(coordinate -> StrategyUtils.calculateDistance(move, coordinate))
                .average()
                .orElse(Double.MAX_VALUE); // Return a large value if there are no cells
    }

}
