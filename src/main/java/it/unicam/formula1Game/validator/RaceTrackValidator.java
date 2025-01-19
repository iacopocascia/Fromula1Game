package it.unicam.formula1Game.validator;

import it.unicam.formula1Game.racetrack.RacetrackUtils;

import static it.unicam.formula1Game.racetrack.RacetrackUtils.*;

/**
 * The {@code TrackValidator} class implements the {@link ITrackValidator} interface
 * to provide concrete methods for validating the properties of a racetrack.
 */
public class RaceTrackValidator implements ITrackValidator {

    /**
     * Validates the width of the racetrack.
     * The width is considered valid if it is greater than 0 and less than {@link RacetrackUtils#MAX_WIDTH}.
     *
     * @param width The width of the track to validate.
     * @return {@code true} if the width is valid, {@code false} otherwise.
     */
    @Override
    public boolean validateWidth(int width) {
        return width > 0 && width < MAX_WIDTH;
    }

    /**
     * Validates the height of the racetrack.
     * The height is considered valid if it is greater than 0 and less than {@link RacetrackUtils#MAX_HEIGHT}.
     *
     * @param height The height of the track to validate.
     * @return {@code true} if the height is valid, {@code false} otherwise.
     */
    @Override
    public boolean validateHeight(int height) {
        return height > 0 && height < MAX_HEIGHT;
    }

    /**
     * Validates the number of players on the racetrack.
     * The number is valid if it falls within the range defined by {@link RacetrackUtils#MIN_PLAYERS}
     * and {@link RacetrackUtils#MAX_PLAYERS}.
     *
     * @param numberOfPlayers The number of players to validate.
     * @return {@code true} if the number of players is within the allowed range, {@code false} otherwise.
     */
    @Override
    public boolean validateNumberOfPlayers(int numberOfPlayers) {
        return numberOfPlayers >= MIN_PLAYERS && numberOfPlayers <= MAX_PLAYERS;
    }

    /**
     * Validates the direction of the race on the racetrack.
     * The direction is valid if it is either "cw" (clockwise) or "ccw" (counter-clockwise).
     *
     * @param direction The direction of the race to validate.
     * @return {@code true} if the direction is valid, {@code false} otherwise.
     */
    @Override
    public boolean validateDirection(String direction) {
        return direction.equals("cw") || direction.equals("ccw");
    }
}
