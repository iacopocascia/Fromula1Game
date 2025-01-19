package it.unicam.formula1Game.strategy.weightedRandomStrategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.StrategyUtils;
import it.unicam.formula1Game.strategy.WeightedMove;

import java.util.*;

import static it.unicam.formula1Game.strategy.weightedRandomStrategy.WeightedRandomStrategyUtils.*;


/**
 * The {@code WeightedRandomStrategy} class implements the {@link GameStrategy} interface and provides
 * a random weight-biased strategy. The strategy evaluates all possible moves,
 * assigning weights based on cell type, distance from track borders, and player velocity.
 */
public class WeightedRandomStrategy implements GameStrategy {
    /**
     * The {@link RaceTrack} where the game takes place.
     */
    private final RaceTrack raceTrack;

    /**
     * Constructs a new {@code WeightedRandomStrategy} with the specified racetrack.
     *
     * @param raceTrack the {@link RaceTrack} where the game is being played.
     */
    public WeightedRandomStrategy(RaceTrack raceTrack) {
        this.raceTrack = raceTrack;
    }

    /**
     * Applies the random strategy to the {@link CpuPlayer}.
     * The player evaluates all possible moves, assigns weights to each move,
     * and selects the one with the highest weight.
     *
     * @param player the {@link CpuPlayer} using this strategy.
     */
    @Override
    public void applyStrategy(CpuPlayer player) {
        Set<Coordinate> availableMoves = getAvailableMoves(player);
        if (!availableMoves.isEmpty()) {
            List<WeightedMove> weightedMoves = evaluateMoves(availableMoves, player);
            // Select a move based on the highest weight
            makeOptimalMove(weightedMoves, player);
        } else {
            player.setHasCrashed(true);
        }
    }

    /**
     * Applies the highest weighted move to the player based on the evaluated weighted moves.
     * If no move is available, the player is marked as crashed.
     *
     * @param weightedMoves A {@link List} of {@link WeightedMove} objects representing available moves with their weights.
     * @param player        The {@link CpuPlayer} executing the strategy.
     */
    private void makeOptimalMove(List<WeightedMove> weightedMoves, CpuPlayer player) {
        weightedMoves.stream()
                .max(Comparator.comparingDouble(WeightedMove::weight))
                .ifPresentOrElse(
                        move -> {
                            player.makeMove(move.coordinate());
                            checkHasCrashed(player);
                        },
                        () -> player.setHasCrashed(true)
                );
    }

    /**
     * Computes all possible moves for the {@link CpuPlayer} that are within the {@link RaceTrack} boundaries.
     *
     * @param player the {@link CpuPlayer} whose moves are being computed.
     * @return A {@link Set} of {@link Coordinate} objects representing valid moves.
     */
    @Override
    public Set<Coordinate> getAvailableMoves(CpuPlayer player) {
        return StrategyUtils.getAvailableMoves(player.calculatePrincipalPoint(), this.raceTrack);
    }

    /**
     * Checks if the specified player has crashed based on their current position.
     * If the player crashes into a {@link CellType#WALL}, their {@code hasCrashed} field is set to {@code true}.
     *
     * @param player The {@link CpuPlayer} to check.
     */
    @Override
    public void checkHasCrashed(CpuPlayer player) {
        if (this.raceTrack.getCellAt(player.getPosition()).cellType() == CellType.WALL) {
            player.setHasCrashed(true);
        }
    }

    /**
     * Evaluates all available moves for the player, assigning weights to each move.
     *
     * @param availableMoves the set of all possible moves.
     * @param player         the {@link CpuPlayer} whose moves are being evaluated.
     * @return A {@link List} of {@link WeightedMove} objects representing evaluated moves with weights.
     */
    private List<WeightedMove> evaluateMoves(Set<Coordinate> availableMoves, CpuPlayer player) {
        return availableMoves.stream()
                .map(move -> new WeightedMove(move, calculateMoveWeight(player, move)))
                .toList();

    }

    /**
     * Calculates the weight of a move based on cell type, distance from borders, and player velocity.
     *
     * @param player The {@link CpuPlayer} making the move.
     * @param move   The {@link Coordinate} being evaluated.
     * @return The calculated weight for the move.
     */
    private double calculateMoveWeight(CpuPlayer player, Coordinate move) {
        CellType cellType = this.raceTrack.getCellAt(move).cellType();
        double cellTypePenalty = switch (cellType) {
            case WALL -> 1; // Lowest value for walls
            case TRACK -> 10; // Favor track cells
            case START -> 1; // Lowest value for start cells
            case FINISH -> 20; // Highest value for finish line
        };
        // Border penalty: discourage moves closer to borders
        double borderPenalty = cellType != CellType.WALL
                ? Math.sqrt(calculateDistanceFromBorders(move, raceTrack))
                : 0.5;
        // Velocity penalty: penalize high accelerations because of the risk or the "stay in place" choice using a Gaussian-like function
        double theoreticalVelocity = StrategyUtils.calculateTheoreticalVelocity(player.getPosition(),move);
        double velocityPenalty = calculateVelocityPenalty(theoreticalVelocity);

        return getWeightedScore(velocityPenalty, cellTypePenalty, borderPenalty);
    }

    /**
     * Calculates the velocity penalty for a move based on the player's theoretical velocity.
     * This method applies a Gaussian penalty for velocities deviating from the ideal velocity
     * and strongly penalizes the "stay in place" choice (velocity = 0.0).
     *
     * @param theoreticalVelocity The velocity the player would acquire if the move is chosen.
     * @return The penalty factor for the move's velocity.
     */
    private double calculateVelocityPenalty(double theoreticalVelocity) {
        double velocityPenalty=Math.exp(-Math.pow(theoreticalVelocity - IDEAL_VELOCITY, 2) / (2 * Math.pow(SIGMA_VALUE, 2)));
        if (theoreticalVelocity == 0.0) {
            return velocityPenalty*STAY_IN_PLACE_PENALTY; // Assign a negative penalty factor
        }
        return velocityPenalty;
    }

    /**
     * Combines cell type weight, border penalty, and velocity penalty into a single score.
     *
     * @param velocityPenalty The velocity penalty factor.
     * @param baseWeight      The weight based on the cell type.
     * @param borderPenalty   The weight based on border proximity.
     * @return The combined weighted score.
     */
    private static double getWeightedScore(double velocityPenalty, double baseWeight, double borderPenalty) {
        return (baseWeight * CELL_TYPE_WEIGHT) +
                (borderPenalty * BORDER_WEIGHT) +
                (velocityPenalty * VELOCITY_WEIGHT);
    }

    @Override
    public String toString() {
        return "WeightedRandomStrategy";
    }
}
