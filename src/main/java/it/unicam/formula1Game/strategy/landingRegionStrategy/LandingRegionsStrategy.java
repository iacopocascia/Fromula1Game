package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.StrategyUtils;
import it.unicam.formula1Game.strategy.WeightedMove;

import java.util.*;

import static it.unicam.formula1Game.strategy.StrategyUtils.calculateTheoreticalVelocity;
import static it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsStrategyUtils.*;

/**
 * Implements a strategy for navigating a racetrack by focusing on landing regions.
 * Landing regions represent critical points of interest (e.g. corners) where
 * players must adjust their strategy for optimal performance.
 */
public class LandingRegionsStrategy implements GameStrategy {
    private final RaceTrack raceTrack;
    private final ILandingRegionsDetector landingRegionsDetector;
    private final Map<LandingRegion, Boolean> landingRegions = new LinkedHashMap<>();
    private final static double DISTANCE_WEIGHT = 0.7;
    private final static double VELOCITY_WEIGHT = 0.3;
    private final static double MAX_WEIGHT = Double.MAX_VALUE;
    private final static double MIN_WEIGHT = Double.MIN_VALUE;

    /**
     * Constructs a {@code LandingRegionsStrategy} with the provided dependencies.
     *
     * @param raceTrack              the {@link RaceTrack} on which the strategy will be applied.
     * @param landingRegionsDetector the {@link LandingRegionsDetector} object used to build the path.
     */
    public LandingRegionsStrategy(RaceTrack raceTrack, ILandingRegionsDetector landingRegionsDetector) {
        this.raceTrack = raceTrack;
        this.landingRegionsDetector = landingRegionsDetector;
        initializeLandingRegions();
    }

    /**
     * Initializes the landing regions for the strategy using the {@link LandingRegionsDetector} object.
     * Each detected {@link LandingRegion} in the {@link RaceTrack} is added to the internal map,
     * with its visited status set to {@code false}.
     */
    private void initializeLandingRegions() {
        List<LandingRegion> landingRegions = landingRegionsDetector.detectLandingRegions(raceTrack);
        for (LandingRegion landingRegion : landingRegions) {
            this.landingRegions.put(landingRegion, false);
        }
    }

    /**
     * Applies the landing regions strategy to the specified {@link CpuPlayer}.
     * The strategy evaluates the available moves and selects the most appropriate one based on its
     * proximity to the next unvisited landing region.
     * If the player has no valid moves, it is marked as crashed.
     *
     * @param player The {@link CpuPlayer} to which the strategy will be applied.
     */
    @Override
    public void applyStrategy(CpuPlayer player) {
        Set<Coordinate> availableMoves = getAvailableMoves(player);
        if (!availableMoves.isEmpty()) {
            // Get the next unvisited landing region
            LandingRegion nextLandingRegion = getFirstUnvisitedLandingRegion();
            List<WeightedMove> weightedMoves = evaluateMoves(availableMoves, player.getPosition(), nextLandingRegion);
            if (!weightedMoves.isEmpty()) {
                // Choose the move with the highest weight
                Coordinate chosenMove = chooseMove(weightedMoves);
                // Check if the chosen move is inside the next unvisited landing region
                updateVisitedLandingRegions(chosenMove);
                player.makeMove(chosenMove);
                checkHasCrashed(player);
                return;
            }
        }
        player.setHasCrashed(true);
    }

    /**
     * Selects the move with the highest weight from a list of weighted moves.
     *
     * @param weightedMoves A {@link List} of {@link WeightedMove} objects representing the available moves.
     * @return The {@link Coordinate} of the chosen move.
     * @throws NoSuchElementException if the list of weighted moves is empty.
     */
    private Coordinate chooseMove(List<WeightedMove> weightedMoves) {
        return weightedMoves.stream()
                .max(Comparator.comparingDouble(WeightedMove::weight))
                .orElseThrow()
                .coordinate();
    }

    /**
     * Updates the visited status of the next landing region.
     * If the given {@link Coordinate} belongs to the first unvisited {@link LandingRegion},
     * the region is marked as visited.
     *
     * @param coordinate The {@link Coordinate} of the move to evaluate.
     */
    private void updateVisitedLandingRegions(Coordinate coordinate) {
        LandingRegion next = getFirstUnvisitedLandingRegion();
        if (next != null && next.getCells().contains(coordinate)) {
            landingRegions.put(next, true); // Mark as visited
        }
    }

    /**
     * Evaluates a single move by calculating its weight based on the player's current position,
     * the target landing region, and the move's proximity and velocity factors.
     *
     * @param move              The {@link Coordinate} representing the move to evaluate.
     * @param playerPosition    The current position of the player.
     * @param nextLandingRegion The next unvisited {@link LandingRegion}.
     * @param maxVelocity       The maximum recommended velocity for the target landing region.
     * @return A {@link WeightedMove} object containing the move and its calculated weight.
     */
    private WeightedMove evaluateSingleMove(Coordinate move, Coordinate playerPosition, LandingRegion nextLandingRegion, double maxVelocity) {
        CellType cellType = this.raceTrack.getCellAt(move).cellType();
        if (cellType == CellType.FINISH) {
            return new WeightedMove(move, MAX_WEIGHT);
        } else if (cellType == CellType.WALL) {
            return new WeightedMove(move, MIN_WEIGHT);
        }
        double theoreticalVelocity = calculateTheoreticalVelocity(playerPosition, move);
        double averageDistance = calculateAverageDistance(move, nextLandingRegion);
        double weight = calculateWeight(theoreticalVelocity, averageDistance, maxVelocity);
        if (weight == 1) {
            return new WeightedMove(move, adjustWeight(weight, move, nextLandingRegion));
        }
        return new WeightedMove(move, weight);
    }

    /**
     * Evaluates the available moves by assigning weights based on their distance to the next unvisited landing region.
     *
     * @param availableMoves    A set of available {@link Coordinate} objects for the player.
     * @param playerPosition    The {@link CpuPlayer} current position.
     * @param nextLandingRegion The first unvisited {@link LandingRegion} towards which the player is moving.
     * @return A list of {@link WeightedMove} objects representing the moves and their corresponding weights.
     */
    private List<WeightedMove> evaluateMoves(Set<Coordinate> availableMoves, Coordinate playerPosition, LandingRegion nextLandingRegion) {
        if (nextLandingRegion == null) {
            return List.of(); // No moves to evaluate if there's no next region
        }
        double maxVelocity = calculateMaxVelocity(nextLandingRegion, this.raceTrack);
        return availableMoves.stream()
                .map(move -> evaluateSingleMove(move, playerPosition, nextLandingRegion, maxVelocity))
                .toList();

    }

    /**
     * Adjusts the weight of a move based on its proximity to the next unvisited landing region
     * and the following unvisited region, if it exists.
     * The method calculates the distance of the move to the second-next unvisited landing region
     * for a more refined weight adjustment.
     *
     * @param weight        The initial weight of the move.
     * @param move          The {@link Coordinate} representing the player's potential move.
     * @param currentRegion The {@link LandingRegion} currently being targeted.
     * @return The final adjusted weight.
     */
    private double adjustWeight(double weight, Coordinate move, LandingRegion currentRegion) {
        // Find the next unvisited landing region
        LandingRegion secondNextUnvisited = findNextUnvisitedRegion(currentRegion);
        // Modify the weight based on the next unvisited region
        if (secondNextUnvisited != null) {
            double distance = calculateMinimumDistance(move, secondNextUnvisited);
            weight += 1 / (1 + distance); // Adjust weight based on proximity to the second-next region
        }
        return weight;
    }

    /**
     * Finds the next unvisited {@link LandingRegion} after the specified region
     * in the {@link LinkedHashMap}.
     *
     * @param currentRegion The {@link LandingRegion} to start searching from.
     * @return The next unvisited {@link LandingRegion}, or {@code null} if none exists.
     */
    private LandingRegion findNextUnvisitedRegion(LandingRegion currentRegion) {
        boolean foundCurrent = false;
        for (Map.Entry<LandingRegion, Boolean> entry : this.landingRegions.entrySet()) {
            if (foundCurrent && !entry.getValue()) {
                // Return the first unvisited region found after the current one
                return entry.getKey();
            }
            // Mark when the current region is found
            if (entry.getKey().equals(currentRegion)) {
                foundCurrent = true;
            }
        }
        // Return null if no unvisited region is found after the current region
        return null;
    }


    /**
     * Calculates the weight of a potential move based on its theoretical velocity,
     * distance to the target landing region, and the maximum recommended velocity.
     * <p>
     * This method assigns higher weights to moves that are closer to the target landing region
     * and within the maximum velocity limit, while penalizing moves that exceed the maximum velocity.
     * Staying in place is strongly discouraged and assigned the lowest weight.
     *
     * @param theoreticalVelocity The calculated velocity the player would acquire if the move is chosen.
     * @param distance            The minimum distance from the move to the target landing region.
     * @param maxVelocity         The maximum recommended velocity to avoid crashes.
     * @return The calculated weight for the move.
     */
    private double calculateWeight(double theoreticalVelocity, double distance, double maxVelocity) {
        if (theoreticalVelocity != 0.0) {
            double velocityFactor = theoreticalVelocity > maxVelocity
                    ? Math.exp(maxVelocity - theoreticalVelocity) // Exponential penalty for exceeding max velocity
                    : 1; // No penalty if within limits
            double distanceFactor = 1 / (1 + distance);
            return distanceFactor * DISTANCE_WEIGHT + velocityFactor * VELOCITY_WEIGHT;
        }
        // Lowest weight for staying in place
        return MIN_WEIGHT;
    }

    /**
     * Retrieves the first {@link LandingRegion} mapped to a {@code false} value in the landingRegions map.
     *
     * @return The first {@link LandingRegion} with a {@code false} value, or {@code null} if none exist.
     */
    private LandingRegion getFirstUnvisitedLandingRegion() {
        for (Map.Entry<LandingRegion, Boolean> entry : this.landingRegions.entrySet()) {
            if (!entry.getValue()) {
                return entry.getKey();
            }
        }
        return null; // Return null if no LandingRegion is mapped to false
    }


    /**
     * Computes the available moves as the {@link it.unicam.formula1Game.cell.Cell} objects that are
     * reachable from the player's position based on the principal point (even wall cells).
     *
     * @return a {@link List} of {@link Coordinate} objects that represent the available moves.
     */
    @Override
    public Set<Coordinate> getAvailableMoves(CpuPlayer player) {
        return StrategyUtils.getAvailableMoves(player.calculatePrincipalPoint(), this.raceTrack);
    }

    /**
     * Checks if the specified player has crashed based on their current position and game context.
     * If so sets the <code>hasCrashed</code> field of the <code>CpuPlayer</code> class to <code>true</code>.
     *
     * @param player The {@link CpuPlayer} to check.
     */
    @Override
    public void checkHasCrashed(CpuPlayer player) {
        if (this.raceTrack.getCellAt(player.getPosition()).cellType() == CellType.WALL) {
            player.setHasCrashed(true);
        }
    }

    @Override
    public String toString() {
        return "LandingRegionsStrategy";
    }
}
