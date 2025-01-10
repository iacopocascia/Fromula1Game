package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.*;

/**
 * The {@code RandomStrategy} class implements the {@link GameStrategy} interface and provides
 * a pseudo-randomized strategy for CPU players. The strategy evaluates all possible moves,
 * assigning weights based on cell type, distance from track borders, and player velocity.
 * This ensures a more refined decision-making process while still maintaining randomness.
 */
public class RandomStrategy implements GameStrategy {
    /**
     * The {@link RaceTrack} where the game takes place.
     */
    private final RaceTrack raceTrack;
    /**
     * Weight for cell type importance in move evaluation.
     */
    private static final double CELL_TYPE_WEIGHT = 0.6;
    /**
     * Weight for border proximity importance in move evaluation.
     */
    private static final double BORDER_WEIGHT = 0.3;

    /**
     * Weight for velocity factor importance in move evaluation.
     */
    private static final double VELOCITY_WEIGHT = 0.1;
    /**
     * Ideal velocity for smoother gameplay.
     */
    private static final double IDEAL_VELOCITY = 2.0;
    /**
     * Standard deviation for Gaussian-like velocity penalty calculation.
     */
    private static final double SIGMA_VALUE = 1.0;

    /**
     * Constructs a new {@code RandomStrategy} with the specified racetrack.
     *
     * @param raceTrack the {@link RaceTrack} where the game is being played.
     */
    public RandomStrategy(RaceTrack raceTrack) {
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
            Coordinate selectedMove = weightedMoves.stream()
                    .max(Comparator.comparingDouble(WeightedMove::weight))
                    .orElseThrow()
                    .coordinate();
            player.makeMove(selectedMove);
            checkHasCrashed(player);
        } else {
            player.setHasCrashed(true);
        }
    }

    /**
     * Computes all possible moves for the {@link CpuPlayer} that are within the {@link RaceTrack} boundaries.
     *
     * @param player the {@link CpuPlayer} whose moves are being computed.
     * @return A {@link Set} of {@link Coordinate} objects representing valid moves.
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
                if (this.raceTrack.isWithinBoundaries(move)) {
                    availableMoves.add(move);
                }
            }
        }
        return availableMoves;
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
        List<WeightedMove> weightedMoves = new ArrayList<>();
        for (Coordinate move : availableMoves) {
            double weight = calculateMoveWeight(player, move);
            weightedMoves.add(new WeightedMove(move, weight));
        }
        return weightedMoves;

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
        double baseWeight = switch (cellType) {
            case WALL -> 1; // Lowest weight for walls
            case TRACK -> 10; // Favor track cells
            case START -> 1; // Lowest weight for start cells
            case FINISH -> 20; // Highest weight for finish line
        };
        // Border penalty: discourage moves closer to borders
        double borderPenalty;
        if (cellType != CellType.WALL) {
            double distanceFromBorders = calculateDistanceFromBorders(move);
            borderPenalty = Math.sqrt(distanceFromBorders); // Penalty calculated using the sqrt function
        } else {
            borderPenalty = 0.5; // If the destination cell is a WALL
        }
        // Velocity factor: penalize high accelerations because of the risk or the "stay in place" choice using a Gaussian-like function
        double theoreticalVelocity = Math.sqrt(
                Math.pow(move.getRow() - player.getPosition().getRow(), 2) +
                        Math.pow(move.getColumn() - player.getPosition().getColumn(), 2));
        double velocityPenalty = Math.exp(-Math.pow(theoreticalVelocity - IDEAL_VELOCITY, 2) / (2 * Math.pow(SIGMA_VALUE, 2)));
        double weightedScore = getWeightedScore(velocityPenalty, baseWeight, borderPenalty);

        // Further discourage "stay in place" moves
        if (player.getPosition().equals(move)) {
            weightedScore *= 0.5; // Reduce weight significantly
        }

        return weightedScore;
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
    /**
     * Calculates the distance of a move from the closest border.
     *
     * @param move The move to evaluate.
     * @return The distance from the closest border.
     */
    private int calculateDistanceFromBorders(Coordinate move) {
        int distanceToTop = calculateDistanceInDirection(-1, 0, move);
        int distanceToRight = calculateDistanceInDirection(0, 1, move);
        int distanceToBottom = calculateDistanceInDirection(1, 0, move);
        int distanceToLeft = calculateDistanceInDirection(0, -1, move);
        return Math.min(Math.min(distanceToTop, distanceToBottom), Math.min(distanceToLeft, distanceToRight));

    }

    /**
     * Calculates the distance from a given move to the nearest wall in a specified direction.
     *
     * @param rowStep The row increment for the direction (-1 for up, 1 for down, 0 for no vertical movement).
     * @param colStep The column increment for the direction (-1 for left, 1 for right, 0 for no horizontal movement).
     * @param move    The starting coordinate.
     * @return The distance to the nearest wall in the specified direction.
     */
    private int calculateDistanceInDirection(int rowStep, int colStep, Coordinate move) {
        int distance = 0;
        boolean found = false;
        while (!found) {
            distance++;
            Coordinate next = new Coordinate(move.getRow() + distance * rowStep, move.getColumn() + distance * colStep);
            if (this.raceTrack.getCellAt(next).cellType() == CellType.WALL) {
                found = true;
            }
        }
        return distance;
    }

    @Override
    public String toString() {
        return "RandomStrategy";
    }
}
