package it.unicam.formula1Game.player;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.strategy.GameStrategy;

/**
 * Represents a CPU-controlled player in a Formula 1 game.
 * A {@code CpuPlayer} has a unique identifier, a position on the track, and a specific strategy it follows during the game.
 * The class handles the player's movement, strategy application, and crash state.
 */
public class CpuPlayer implements Player {
    /**
     * Unique number that identifies the player during a game.
     */
    private final int id;
    /**
     * The player's position on the track.
     */
    private Coordinate position;
    /**
     * Indicates whether the player has crashed.
     */
    private boolean hasCrashed;
    /**
     * The player's last move as a coordinate difference.
     */
    private Coordinate lastMove;
    /**
     * The player's current velocity, calculated based on the last move.
     */
    private double velocity;
    /**
     * The strategy that the player follows during the game.
     */
    private GameStrategy strategy;

    /**
     * Constructs a new {@code CpuPlayer} with a specified ID and initial position.
     * The player's initial state is set to "not crashed," with no previous moves and zero velocity.
     *
     * @param id       the unique identifier for the player.
     * @param position the initial position of the player on the track.
     */
    public CpuPlayer(int id, Coordinate position) {
        this.id = id;
        this.position = position;
        this.hasCrashed = false;
        this.lastMove = new Coordinate(0, 0);
        this.velocity = 0.0;
    }

    /**
     * Moves the player by updating its position and last move.
     * Also recalculates the player's velocity based on the movement.
     *
     * @param move the target position for the player's move.
     */
    @Override
    public void makeMove(Coordinate move) {
        // Update last move
        this.lastMove.setRow(move.getRow() - position.getRow());
        this.lastMove.setColumn(move.getColumn() - position.getColumn());
        // Update position
        this.setPosition(move);
        // Update velocity
        this.calculateVelocity();
    }

    /**
     * Calculates the principal point for the player's next move.
     * The principal point is derived from the player's current position and the last move.
     *
     * @return a {@link Coordinate} object representing the principal point.
     */
    @Override
    public Coordinate calculatePrincipalPoint() {
        return new Coordinate(this.position.getRow() + this.lastMove.getRow(),
                this.position.getColumn() + this.lastMove.getColumn());
    }

    /**
     * Checks if the player has crashed.
     *
     * @return {@code true} if the player has crashed, {@code false} otherwise.
     */
    @Override
    public boolean hasCrashed() {
        return hasCrashed;
    }

    /**
     * Sets the player's crash state.
     *
     * @param hasCrashed {@code true} if the player has crashed, {@code false} otherwise.
     */
    public void setHasCrashed(boolean hasCrashed) {
        this.hasCrashed = hasCrashed;
    }

    /**
     * Returns the player's current position on the track.
     *
     * @return a {@link Coordinate} object representing the player's position.
     */
    @Override
    public Coordinate getPosition() {
        return this.position;
    }

    /**
     * Returns the player's unique identifier.
     *
     * @return an integer representing the player's ID.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Updates the player's current position on the track.
     *
     * @param position a {@link Coordinate} representing the new position.
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * Calculates and updates the player's velocity based on the last move.
     * Velocity is computed as the Euclidean distance of the last move.
     */
    private void calculateVelocity() {
        this.velocity = Math.sqrt(Math.pow(this.lastMove.getRow(), 2) + Math.pow(this.lastMove.getColumn(), 2));
    }

    /**
     * Sets the player's game strategy.
     *
     * @param strategy the {@link GameStrategy} to be applied by the player.
     */
    public void setStrategy(GameStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Returns the player's game strategy.
     *
     * @return the {@link GameStrategy} currently assigned to the player.
     */
    public GameStrategy getStrategy() {
        return strategy;
    }

    /**
     * Executes the player's current game strategy.
     * The strategy determines the player's next move.
     */

    public void applyStrategy() {
        this.strategy.applyStrategy(this);
    }

    public Coordinate getLastMove() {
        return lastMove;
    }

    public double getVelocity() {
        return velocity;
    }

    /**
     * Returns a string representation of the player.
     * The format includes the player's ID, position, velocity, and current strategy.
     *
     * @return a string representing the player's state.
     */
    @Override
    public String toString() {
        return "CpuPlayer n.: " + id +
                "\n at position: " + position +
                "\n with final velocity: " + velocity +
                "\n using strategy: " + strategy;
    }

}
