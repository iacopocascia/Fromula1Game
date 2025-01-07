package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.*;

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
        Set<Coordinate> availableMoves = getAvailableMoves(player);
        if (!availableMoves.isEmpty()) {
            List<Coordinate> weightedMoves = evaluateMoves(availableMoves);
            // Select a random move from the weighted moves list
            Coordinate selectedMove = weightedMoves.get((int) (Math.random() * weightedMoves.size()));
            player.makeMove(selectedMove);
            checkHasCrashed(player);
        } else {
            // If there are no available moves left (within the track borders) it means the player is going to crash
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
    private List<Coordinate> evaluateMoves(Set<Coordinate> availableMoves) {
        List<Coordinate> weightedMoves = new ArrayList<>();
        for (Coordinate move : availableMoves) {
            switch (this.raceTrack.getCellAt(move).cellType()) {
                case WALL:
                    // Add fewer instances for wall cells (weight 1)
                    addWeightedMoves(weightedMoves, move, 1);
                    break;

                case TRACK:
                    // Add more instances for track cells (weight 5)
                    addWeightedMoves(weightedMoves, move, 5);
                    break;

                case START:
                    addWeightedMoves(weightedMoves, move, 1);
                    break;

                case FINISH:
                    // Add the highest weight for finish cells (weight 10)
                    addWeightedMoves(weightedMoves, move, 10);
                    break;
            }
        }
        return weightedMoves;
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
