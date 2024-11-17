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

    public CpuPlayer(int id,Coordinate position) {
        this.id = id;
        this.position=position;
        this.hasCrashed = false;
        this.lastMove = new Coordinate(0, 0);
    }

    /**
     *
     * @param horizontalShift Specifies the horizontal shift from the "principal point"
     * @param verticalShift   Specifies the vertical shift from the "principal point"
     * @return
     */
    @Override
    public Coordinate makeMove(int horizontalShift, int verticalShift) {
        return null;
    }

    /**
     * Calculates the principal point based on the actual player's position and its last move
     * according to the game's rules.
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
}
