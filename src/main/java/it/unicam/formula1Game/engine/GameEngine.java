package it.unicam.formula1Game.engine;

import it.unicam.formula1Game.racetrack.RaceTrack;

/**
 * The {@code GameEngine} interface defines the main logic for running a game.
 * It manages the game lifecycle, including initialization, player moves, and end conditions.
 * Implementations can provide different types of game logic, such as CPU-driven or user-driven games.
 */
public interface GameEngine {
    /**
     * Initializes the game environment.
     * This method sets up the game environment, setting initial conditions.
     */
    void initializeEnvironment(RaceTrack raceTrack);

    /**
     * Starts the game loop where players take turns until the game ends.
     */
    void startGame();

    /**
     * Ends the game and handles any post-game logic, such as declaring the winner.
     * This method is called when the game has been won or all players have crashed.
     */
    void endGame();

    /**
     * Checks if the end condition for the game is met.
     * This method should be called after each move to determine whether the game should end.
     * @return {@code true} if there is an end condition, {@code false} otherwise.
     */
    boolean checkEndCondition();

    /**
     * Makes the players do their first move.
     */
    void makeFirstMove();

}
