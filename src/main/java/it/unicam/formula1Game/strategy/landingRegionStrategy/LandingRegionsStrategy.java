package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.unicam.formula1Game.cell.Coordinate.calculateDistance;

/**
 * Implements a strategy for navigating a racetrack by focusing on landing regions.
 * Landing regions represent critical points of interest (e.g., corners) where
 * players must adjust their strategy for optimal performance.
 */
public class LandingRegionsStrategy implements GameStrategy {
    private final RaceTrack raceTrack;
    private final LandingRegionsProcessor landingRegionsProcessor;
    private final List<Coordinate> path;
    /**
     * Constructs a {@code LandingRegionsStrategy} with the provided dependencies.
     * <p>
     *  @param raceTrack                     the {@link RaceTrack} on which the strategy will be applied.
     *  @param landingRegionsProcessor       the {@link LandingRegionsProcessor} object used to build the path.
     */
    public LandingRegionsStrategy(RaceTrack raceTrack, LandingRegionsProcessor landingRegionsProcessor) {
        this.raceTrack = raceTrack;
        this.landingRegionsProcessor = landingRegionsProcessor;
        this.path = initializePath();
    }

    /**
     * Initializes the path to follow by elaborating the landing regions and appending
     * the finish line coordinates to the end of the path.
     *
     * @return the {@link List} of {@link Coordinate} objects representing the path.
     */
    private List<Coordinate> initializePath() {
        List<Coordinate> path = landingRegionsProcessor.processLandingRegions(this.raceTrack);
        path.addAll(raceTrack.getFinishCoordinates());
        return path;
    }

    /**
     * Applies the landing regions strategy. The player moves toward the next coordinate
     * in the path if it is available. If no path coordinates are available, a fallback
     * move is determined based on proximity to the path.
     */
    @Override
    public void applyStrategy(CpuPlayer player) {
        Set<Coordinate> availableMoves = getAvailableMoves(player);
        if (!availableMoves.isEmpty()) {
            for (int i = 0; i < path.size(); i++) {
                Coordinate pathCoordinate = path.get(i);
                if (availableMoves.contains(pathCoordinate)) {
                    player.makeMove(pathCoordinate);
                    // Remove all coordinates in the path up to and including the chosen one
                    path.subList(0, i + 1).clear();
                    return; // Exit early after making the move
                }
            }
            handleFallBackMove(availableMoves,player); // Only called if no move was made
        } else {
            player.setHasCrashed(true);
        }
    }

    /**
     * Determines a fallback move when no available moves match the path coordinates.
     * The move is selected based on the smallest distance to the path coordinates,
     * weighted by the coordinate's priority in the path.
     *
     * @param availableMoves the {@link Set} of available moves for the player.
     */
    private void handleFallBackMove(Set<Coordinate> availableMoves, CpuPlayer player) {
        Coordinate fallbackMove = null;
        double bestScore = Double.MAX_VALUE; // Lower score is better
        for (Coordinate availableMove : availableMoves) {
            for (int i = 0; i < path.size(); i++) {
                Coordinate pathCoordinate = path.get(i);

                // Calculate the Manhattan distance between the available move and the path coordinate
                int distance = calculateDistance(availableMove, pathCoordinate);

                // Assign a score that considers both the distance and the position in the path
                double score = distance + (i * 0.5); // Distance plus a weighted priority based on path index

                // Update the fallback move if this score is the best
                if (score < bestScore) {
                    bestScore = score;
                    fallbackMove = availableMove;
                }
            }
        }
        player.makeMove(fallbackMove);
    }


    /**
     * Computes the available moves as the {@link it.unicam.formula1Game.cell.Cell} objects that are
     * reachable from the player's position based on the principal point (even wall cells).
     *
     * @return a {@link List} of {@link Coordinate} objects that represent the available moves.
     */
    @Override
    public Set<Coordinate> getAvailableMoves(CpuPlayer player) {
        Set<Coordinate> availableMoves = new HashSet<>();
        Coordinate principalPoint = player.calculatePrincipalPoint();
        // Iterate over all possible combinations of shifts (-1, 0, 1)
        for (int rowShift = -1; rowShift <= 1; rowShift++) {
            for (int colShift = -1; colShift <= 1; colShift++) {
                // Add the move to the list if it is within the track boundaries
                Coordinate move = new Coordinate(principalPoint.getRow() + rowShift,
                        principalPoint.getColumn() + colShift);
                if (raceTrack.isWithinBoundaries(move)) {
                    availableMoves.add(move);
                }
            }
        }
        return availableMoves;
    }
}
