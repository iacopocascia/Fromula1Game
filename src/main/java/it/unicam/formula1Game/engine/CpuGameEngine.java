package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.RandomStrategy;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsElaborator;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsProcessor;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsStrategy;

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
     *
     */
    @Override
    public void initializeEnvironment(RaceTrack raceTrack) {
        try {
            this.raceTrack=raceTrack;
            this.players = new CpuPlayer[raceTrack.getNumberOfPlayers()];
            placeCpuPlayers();
            assignStrategies();
            System.out.println("Game Initialized");
            System.out.println(this.raceTrack);
        } catch (InvalidConfigurationException e) {
            System.out.println("An error occurred during players placement");
        }
    }

    /**
     * Initializes and places each player on the start line.
     * Each player is assigned a random ID between 1 and 10, and players are evenly placed across the start line.
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
     * Generates a list of unique random IDs between 1 and 10 for the given number of players.
     *
     * @return a list of unique player IDs.
     */
    private List<Integer> generateUniquePlayerIds(int numberOfPlayers) {
        List<Integer> availableIds = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            availableIds.add(i);  // Add IDs from 1 to 10
        }
        Collections.shuffle(availableIds);  // Shuffle the list to randomize IDs
        return availableIds.subList(0, numberOfPlayers);  // Return only the number of IDs needed for the players
    }

    /**
     * Assigns {@link GameStrategy} to each player in a Round-Robin fashion.
     */
    private void assignStrategies() {
        List<GameStrategy> gameStrategies=new ArrayList<>();
        gameStrategies.add(new RandomStrategy(this.raceTrack));
        gameStrategies.add(new LandingRegionsStrategy(this.raceTrack,new LandingRegionsProcessor(this.raceTrack,new LandingRegionsDetector(),new LandingRegionsElaborator())));
        for(int i=0;i<this.players.length;i++){
            this.players[i].setStrategy(gameStrategies.get(i%gameStrategies.size()));
        }
    }
    /**
     * Makes the players move to their left.
     */
    @Override
    public void makeFirstMove(){
        for (CpuPlayer player:this.players){
            player.makeMove(new Coordinate(player.getPosition().getRow(),player.getPosition().getColumn()-1));
        }
    }

    /**
     * Starts the main game loop where players take turns until the game ends.
     * Each player makes a move based on its strategy, and the game engine checks for crashes or win conditions.
     */
    @Override
    public void startGame() {
        boolean gameInProgress=true;
        while (gameInProgress){
            for(CpuPlayer player:this.players){
                player.applyStrategy();
            }

        }

    }

    /**
     *
     */
    @Override
    public void endGame() {

    }

    /**
     * @return
     */
    @Override
    public boolean checkCrash() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean checkWinCondition() {
        return false;
    }

}
