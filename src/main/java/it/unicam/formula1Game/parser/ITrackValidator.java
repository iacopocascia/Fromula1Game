package it.unicam.formula1Game.parser;

/**
 *TODO
 */
public interface ITrackValidator {
    /**
     *
     * @return
     */
    boolean validateWidth(int width);

    /**
     *
     * @param height
     * @return
     */
    boolean validateHeight(int height);

    /**
     *
     * @param numberOfPlayers
     * @return
     */
    boolean validateNumberOfPlayers(int numberOfPlayers);

    /**
     *
     * @param direction
     * @return
     */
    boolean validateDirection(String direction);
}
