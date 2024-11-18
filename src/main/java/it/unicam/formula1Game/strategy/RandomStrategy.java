package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.player.Player;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RandomStrategy implements GameStrategy {
    /**
     * The {@link CpuPlayer} towards which the strategy will be applied.
     */
    private final CpuPlayer player;
    /**
     * The {@link RaceTrack} where the game takes place.
     */
    private final RaceTrack track;

    public RandomStrategy(CpuPlayer player, RaceTrack track) {
        this.player = player;
        this.track = track;
    }
    /**
     *
     */
    @Override
    public void applyStrategy() {
        List<Coordinate> weightedMoves=evaluateMoves();
        // Select a random move from the weighted moves list
        Coordinate selectedMove = weightedMoves.get((int) (Math.random() * weightedMoves.size()));
        this.player.makeMove(selectedMove);
    }

    /**
     * @return
     */
    private List<Coordinate> evaluateMoves() {
        List<Coordinate> availableMoves = player.getAvailableMoves();
        List<Coordinate> weightedMoves = new ArrayList<>();
        for (Coordinate move : availableMoves) {
            switch (this.track.getCellAt(move).cellType()) {
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
                    // Add the highest weight for finish cells (e.g., weight 10)
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

}
