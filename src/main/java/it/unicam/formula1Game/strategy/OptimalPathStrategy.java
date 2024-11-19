package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.*;

public class OptimalPathStrategy implements GameStrategy {
    /**
     *
     */
    private final RaceTrack track;
    /**
     *
     */
    private final CpuPlayer player;

    /**
     * @param track
     * @param player
     */
    public OptimalPathStrategy(RaceTrack track, CpuPlayer player) {
        this.track = track;
        this.player = player;
    }

    /**
     *
     */
    @Override
    public void applyStrategy() {
        // Use A* to find the best path
        List<Coordinate> bestPath = findBestPath();
        if (!bestPath.isEmpty()) {
            // Move the player to the next step in the path
            player.makeMove(bestPath.get(0));
        }
    }

    /**
     * @return
     */
    @Override
    public List<Coordinate> getAvailableMoves() {
        return List.of();
    }

    /**
     * Finds the best path to the finish line using the A* algorithm.
     *
     * @return A {@link List} of {@link Coordinate} objects representing the path.
     */
    private List<Coordinate> findBestPath() {
        PriorityQueue<PathNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(PathNode::getFScore));
        Map<Coordinate, Double> gScores = new HashMap<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();

        Coordinate start = player.getPosition();
        List<Coordinate> finish = track.getFinishCoordinates();

        gScores.put(start, 0.0);
        openSet.add(new PathNode(start, heuristic(start, finish)));

        while (!openSet.isEmpty()) {
            PathNode current = openSet.poll();

            if (current.coordinate().equals(finish)) {
                return reconstructPath(cameFrom, current.coordinate());
            }

            for (Coordinate neighbor : this.getAvailableMoves()) {
                double tentativeGScore = gScores.get(current.coordinate()) + 1; // All moves have cost 1
                if (tentativeGScore < gScores.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current.coordinate());
                    gScores.put(neighbor, tentativeGScore);
                    openSet.add(new PathNode(neighbor, tentativeGScore + heuristic(neighbor, finish)));
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    private double heuristic(Coordinate start, List<Coordinate> finish) {

        return 0.0;
    }

    /**
     * Reconstructs the path from the start to the finish using the cameFrom map.
     *
     * @param cameFrom the map of visited nodes.
     * @param current the current {@link Coordinate}.
     * @return A {@link List} of {@link Coordinate} objects representing the path.
     */
    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate current) {
        List<Coordinate> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }
    /**
     * Represents a node in the A* algorithm with its coordinate and f-score.
     */
    private record PathNode(Coordinate coordinate, double fScore) {

        public double getFScore() {
            return fScore;
        }
    }
}
