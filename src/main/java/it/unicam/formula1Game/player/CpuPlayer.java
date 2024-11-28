package it.unicam.formula1Game.player;

import it.unicam.formula1Game.cell.Coordinate;

/**
 *
 */
public class CpuPlayer implements Player {
    /**
     * Unique number that identifies a player on the track
     */
    private final int id;
    /**
     * The player's position in the track
     */
    private Coordinate position;
    /**
     * Specifies whether the player has crashed or not
     */
    private boolean hasCrashed;
    /**
     * Stores the player's last move
     */
    private Coordinate lastMove;
    /**
     * Stores the player's velocity
     */
    private double velocity;

    /**
     *
     * @param id
     * @param position
     */

    public CpuPlayer(int id, Coordinate position) {
        this.id = id;
        this.position = position;
        this.hasCrashed = false;
        this.lastMove = new Coordinate(0, 0);
        this.velocity=0.0;
    }

    /**
     * Moves the player by updating its position based on the last move.
     *
     * @param move
     */
    @Override
    public void makeMove(Coordinate move) {
        // Update last move
        this.lastMove.setRow(move.getRow()-position.getRow());
        this.lastMove.setColumn(move.getColumn()- position.getColumn());
        // Update position
        this.setPosition(move);
        // Update velocity
        this.calculateVelocity();
    }

    /**
     * Calculates the principal point based on the actual player's position and its last move
     * according to the game's rules.
     *
     * @return The principal point as a {@link Coordinate} object.
     */
    @Override
    public Coordinate calculatePrincipalPoint() {
        return new Coordinate(this.position.getRow() + this.lastMove.getRow(),
                this.position.getColumn() + this.lastMove.getColumn());
    }

    public Coordinate getLastMove() {
        return lastMove;
    }

    public void setLastMove(Coordinate lastMove) {
        this.lastMove = lastMove;
    }

    public boolean isHasCrashed() {
        return hasCrashed;
    }

    public void setHasCrashed(boolean hasCrashed) {
        this.hasCrashed = hasCrashed;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }
    public double getVelocity() {
        return velocity;
    }

    /**
     * Calculates and sets the velocity field
     */
    private void calculateVelocity() {
        this.velocity=Math.sqrt(Math.pow(this.lastMove.getRow(), 2) + Math.pow(this.lastMove.getColumn(), 2));
    }
}
