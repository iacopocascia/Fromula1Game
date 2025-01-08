package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.*;
//TODO javadoc

/**
 * The {@code RandomStrategy} class implements the {@link GameStrategy} interface and provides
 * a randomized strategy for CPU players. The strategy evaluates all possible moves,
 * assigning weights to each based on the cell type.
 */
public class RandomStrategy implements GameStrategy {
    /**
     * The {@link RaceTrack} where the game takes place.
     */
    private final RaceTrack raceTrack;

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
     * The player evaluates all possible moves, assigns weights to each move based on cell type,
     * and then selects a move probabilistically.
     */
    @Override
    public void applyStrategy(CpuPlayer player) {
//        Set<Coordinate> availableMoves = getAvailableMoves(player);
//        if (!availableMoves.isEmpty()) {
//            List<Coordinate> weightedMoves = evaluateMoves(availableMoves);
//            // Select a random move from the weighted moves list
//            Coordinate selectedMove = weightedMoves.get((int) (Math.random() * weightedMoves.size()));
//            player.makeMove(selectedMove);
//            checkHasCrashed(player);
//        } else {
//            // If there are no available moves left (within the track borders) it means the player is going to crash
//            player.setHasCrashed(true);
//        }
        Set<Coordinate> availableMoves = getAvailableMoves(player);
        if (!availableMoves.isEmpty()) {
            List<WeightedMove> weightedMoves = evaluateMoves(availableMoves, player);
            // Select a move based on the highest weight
            Coordinate selectedMove = weightedMoves.stream()
                    .max(Comparator.comparingDouble(WeightedMove::getWeight))
                    .orElseThrow()
                    .getCoordinate();
            player.makeMove(selectedMove);
            checkHasCrashed(player);
        } else {
            player.setHasCrashed(true);
        }
    }

    /**
     * Computes the available moves as the {@link it.unicam.formula1Game.cell.Cell} objects that are
     * within the {@link RaceTrack} boundaries.
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
                if (this.raceTrack.isWithinBoundaries(move)) {
                    availableMoves.add(move);
                }
            }
        }
        return availableMoves;
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

    /**
     * Evaluates the available moves for the player, assigning higher weights to moves
     * leading to favorable cells and lower weights to unfavorable cells.
     *
     * @return A {@link List} of {@link Coordinate} objects representing weighted moves.
     */
    private List<WeightedMove> evaluateMoves(Set<Coordinate> availableMoves, CpuPlayer player) {
//        List<Coordinate> weightedMoves = new ArrayList<>();
//        for (Coordinate move : availableMoves) {
//            switch (this.raceTrack.getCellAt(move).cellType()) {
//                case WALL:
//                    // Add fewer instances for wall cells (weight 1)
//                    addWeightedMoves(weightedMoves, move, 1);
//                    break;
//
//                case TRACK:
//                    // Add more instances for track cells (weight 5)
//                    addWeightedMoves(weightedMoves, move, 10);
//                    break;
//
//                case START:
//                    addWeightedMoves(weightedMoves, move, 1);
//                    break;
//
//                case FINISH:
//                    // Add the highest weight for finish cells (weight 10)
//                    addWeightedMoves(weightedMoves, move, 10);
//                    break;
//            }
//        }
//        return weightedMoves;
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
     * @param player The current {@link CpuPlayer}.
     * @param move   The move to evaluate.
     * @return The calculated weight of the move.
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
        if(cellType!=CellType.WALL) {
            double distanceFromBorders = calculateDistanceFromBorders(move);
            borderPenalty = Math.sqrt(distanceFromBorders); // Penalty calculated using tne sqrt function
        }else {
            borderPenalty=0.5; // If the destination cell is a WALL
        }
        // Velocity factor: penalize high accelerations because of the risk or "stay in place"
        double theoreticalVelocity = Math.sqrt(
                Math.pow(move.getRow() - player.getPosition().getRow(), 2) +
                        Math.pow(move.getColumn() - player.getPosition().getColumn(), 2));
        double weightedScore = getWeightedScore(theoreticalVelocity, baseWeight, borderPenalty);

        // Further discourage "stay in place" moves
        if (player.getPosition().equals(move)) {
            weightedScore *= 0.5; // Reduce weight significantly
        }

        return weightedScore;
    }

    private static double getWeightedScore(double theoreticalVelocity, double baseWeight, double borderPenalty) {
        double velocityPenalty = theoreticalVelocity > 0 ? 1 / theoreticalVelocity : 0.5; // Avoid division by zero

        // Weights to control importance
        double cellTypeWeight = 0.6;  // Weight for cell type importance
        double borderWeight = 0.3;    // Weight for border proximity
        double velocityWeight = 0.1;  // Weight for velocity smoothness

        // Combine the factors using weighted sum
        return (baseWeight * cellTypeWeight) +
                (borderPenalty * borderWeight) +
                (velocityPenalty * velocityWeight);
    }

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
     * @param move    The starting coordinate.
     * @param rowStep The row increment for the direction (-1 for up, 1 for down, 0 for no vertical movement).
     * @param colStep The column increment for the direction (-1 for left, 1 for right, 0 for no horizontal movement).
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

    /**
     * Adds a move to the weighted moves list multiple times based on the weight.
     *
     * @param weightedMoves The list to which the move will be added.
     * @param move          The {@link Coordinate} representing the move.
     * @param weight        The weight determining how many times the move will be added.
     */
    private void addWeightedMoves(List<Coordinate> weightedMoves, Coordinate move, int weight) {
        for (int i = 0; i < weight; i++) {
            weightedMoves.add(move);
        }
    }

    @Override
    public String toString() {
        return "RandomStrategy";
    }
}
