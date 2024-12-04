package it.unicam.formula1Game.parser;

/**
 * The {@code ITrackValidator} interface defines the contract for validating various properties
 * of a racetrack.
 */
public interface ITrackValidator {
    /**
     * Validates the width of the racetrack.
     *
     * @param width The width of the track to validate.
     * @return {@code true} if the width is valid, {@code false} otherwise.
     */
    boolean validateWidth(int width);

    /**
     * Validates the height of the racetrack.
     *
     * @param height The height of the track to validate.
     * @return {@code true} if the height is valid, {@code false} otherwise.
     */
    boolean validateHeight(int height);

    /**
     * Validates the number of players on the racetrack.
     *
     * @param numberOfPlayers The number of players to validate.
     * @return {@code true} if the number of players is within the allowed range, {@code false} otherwise.
     */
    boolean validateNumberOfPlayers(int numberOfPlayers);

    /**
     * Validates the direction of the race on the racetrack.
     *
     * @param direction The direction of the race, either clockwise or counter-clockwise.
     * @return {@code true} if the direction is valid, {@code false} otherwise.
     */
    boolean validateDirection(String direction);
}
