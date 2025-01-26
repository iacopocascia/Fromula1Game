package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.player.Player;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;

import java.util.*;

/**
 * A game engine implementation that manages the race for CPU players.
 */
public class CpuGameEngine implements GameEngine {
    /**
     * The cpu players that take part to the race.
     */
    private CpuPlayer[] players;
    /**
     * The racetrack where the game takes place.
     */
    private RaceTrack raceTrack;
    /**
     * The winner of the race.
     */
    private CpuPlayer winner;

    /**
     * The game strategies that will be applied by the players in the game.
     */
    private List<GameStrategy> strategies;

    /**
     * Initializes the game environment by placing players on the track and assigning strategies.
     *
     * @param raceTrack the {@link RaceTrack} where the game takes place.
     */
    @Override
    public void initializeEnvironment(RaceTrack raceTrack) {
        try {
            this.raceTrack = raceTrack;
            this.players = new CpuPlayer[raceTrack.getNumberOfPlayers()];
            placeCpuPlayers();
            assignStrategies();
            System.out.println("*****************GAME INITIALIZED*****************");
            System.out.println(GameVisualizer.visualizeGame(this.raceTrack, Arrays.stream(this.players).toList()));
        } catch (InvalidConfigurationException e) {
            System.out.println("An error occurred during players placement");
        }
    }

    /**
     * Places CPU players on the start line. Each player is assigned a unique random ID and a start position.
     *
     * @throws InvalidConfigurationException if players cannot be placed on the start line.
     */
    private void placeCpuPlayers() throws InvalidConfigurationException {
        int numberOfPlayers = this.raceTrack.getNumberOfPlayers();
        // Generate unique IDs for the players
        List<Integer> playerIds = generateUniquePlayerIds(numberOfPlayers);
        // Get all START cells from the track
        List<Coordinate> startLine = this.raceTrack.getStartCoordinates();
        // Assign players to START cells in a round-robin fashion
        for (int i = 0; i < numberOfPlayers; i++) {
            int playerId = playerIds.get(i);
            Coordinate startPosition = startLine.get(i % startLine.size()); // Cycle through start line positions
            // Create a player and place it on the track
            this.players[i] = new CpuPlayer(playerId, new Coordinate(startPosition.getRow(), startPosition.getColumn()));

        }

    }

    /**
     * Generates a list of unique random IDs between 0 and 9 for the given number of players.
     *
     * @param numberOfPlayers the number of players to assign IDs to.
     * @return a list of unique player IDs.
     */
    private List<Integer> generateUniquePlayerIds(int numberOfPlayers) {
        List<Integer> availableIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            availableIds.add(i);  // Add IDs from 0 to 9
        }
        Collections.shuffle(availableIds);  // Shuffle the list to randomize IDs
        return availableIds.subList(0, numberOfPlayers);  // Return only the number of IDs needed for the players
    }

    /**
     * Makes the players move left as their first move.
     */
    @Override
    public void makeFirstMove() {
        for (CpuPlayer player : this.players) {
            player.makeMove(new Coordinate(player.getPosition().getRow(), player.getPosition().getColumn() - 1));
        }
        printCurrentState(1);
    }

    /**
     * Sets the <code>strategies</code> field if not already initialized.
     *
     * @param strategies A list of {@link GameStrategy} objects.
     */
    public void setStrategies(List<GameStrategy> strategies) {
        if (this.strategies == null) {
            this.strategies = strategies;
        }
    }

    /**
     * Assigns the {@link GameStrategy} instances in the <code>strategies</code> field to each player
     * using a Round-Robin algorithm.
     */
    public void assignStrategies() {
        for (int i = 0; i < this.players.length; i++) {
            this.players[i].setStrategy(this.strategies.get(i % this.strategies.size()));
        }
    }

    /**
     * Starts the main game loop where players take turns until the game ends.
     * Each player makes a move based on its strategy, and the engine checks for end conditions.
     */
    @Override
    public void startGame() {
        int round = 2;
        while (!checkEndCondition()) {
            for (CpuPlayer player : this.players) {
                if (!player.hasCrashed()) {
                    player.applyStrategy();
                }
            }
            printCurrentState(round);
            round++;
        }
        if (this.winner == null) {
            System.out.println("NO WINNER, ALL PLAYERS CRASHED");
        } else {
            endGame();
        }

    }

    /**
     * Prints the current state of the game using the {@link GameVisualizer} class.
     *
     * @param round the round's progressive number.
     */
    private void printCurrentState(int round) {
        System.out.println("******************** ROUND " + round + " ********************");
        System.out.println(GameVisualizer.visualizeGame(this.raceTrack, Arrays.stream(this.players).toList()));
    }

    /**
     * Ends the game and announces the winner.
     *
     * @return the {@link CpuPlayer} who won the game.
     */
    @Override
    public Player endGame() {
        System.out.println("*****************THE WINNER IS******************\n");
        System.out.println(this.winner);
        return this.winner;
    }

    /**
     * Checks whether the game should end.
     * The game ends if either a player wins or all players crash.
     *
     * @return <code>true</code> if the game should end, <code>false</code> otherwise.
     */
    @Override
    public boolean checkEndCondition() {
        return checkWinCondition() || checkAllPlayersCrashed();
    }

    /**
     * Checks if a player has reached the finish line.
     *
     * @return <code>true</code> if a player crosses the finish line, <code>false</code> otherwise.
     */
    private boolean checkWinCondition() {
        boolean winConditionMet = false;
        for (CpuPlayer player : this.players) {
            if (this.raceTrack.getFinishCoordinates().contains(player.getPosition())) {
                winConditionMet = true;
                this.winner = player;
                break;
            }
        }
        return winConditionMet;
    }

    /**
     * Checks if all players have crashed.
     *
     * @return <code>true</code> if all players crash, <code>false</code> otherwise.
     */
    private boolean checkAllPlayersCrashed() {
        for (CpuPlayer player : this.players) {
            if (!player.hasCrashed()) {
                return false;
            }
        }
        return true;
    }

    public RaceTrack getRaceTrack() {
        return raceTrack;
    }

    public CpuPlayer[] getPlayers() {
        return players;
    }

    public CpuPlayer getWinner() {
        return winner;
    }
}
