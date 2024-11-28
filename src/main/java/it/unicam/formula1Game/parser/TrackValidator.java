package it.unicam.formula1Game.parser;

import static it.unicam.formula1Game.racetrack.Constants.*;

/**
 *todo
 */
public class TrackValidator implements ITrackValidator {

    /**
     * @param width
     * @return
     */
    @Override
    public boolean validateWidth(int width) {
        return width>0 && width<MAX_WIDTH;
    }

    /**
     * @param height
     * @return
     */
    @Override
    public boolean validateHeight(int height) {
        return height>0 && height<MAX_HEIGHT;
    }

    /**
     * @param numberOfPlayers
     * @return
     */
    @Override
    public boolean validateNumberOfPlayers(int numberOfPlayers) {
        return numberOfPlayers>=MIN_PLAYERS && numberOfPlayers<=MAX_PLAYERS;
    }

    /**
     * @param direction
     * @return
     */
    @Override
    public boolean validateDirection(String direction) {
        return direction.equals("cw")||direction.equals("ccw");
    }
}
