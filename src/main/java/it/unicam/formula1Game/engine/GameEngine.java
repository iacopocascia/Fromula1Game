package it.unicam.formula1Game.engine;

/**
 * The {@code GameEngine} interface defines the main logic for running a game.
 * It manages the game lifecycle, including initialization, player moves, and end conditions.
 * Implementations can provide different types of game logic, such as CPU-driven or user-driven games.
 */
public interface GameEngine {
    /**
     * Initializes the game environment.
     * This method sets up the game environment, including loading players and setting initial conditions.
     */
    void initializeEnvironment();

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
     * Checks if the player has crashed by exceeding track boundaries or hitting a wall.
     * If a crash is detected, the player's status is updated accordingly.
     *
     * @return {@code true} if the player has crashed, {@code false} otherwise.
     */
    boolean checkCrash();

    /**
     * Checks if a player has won the race by crossing the finish line.
     * This method should be called after each move to determine whether the game should end.
     * @return {@code true} if there is a win condition, {@code false} otherwise.
     */
    boolean checkWinCondition();


}
