package it.unicam.formula1Game.exceptions;

/**
 * Thrown to indicate that the given {@link it.unicam.formula1Game.racetrack.RaceTrack} configuration is not valid.
 */
public class InvalidConfigurationException extends Exception {
    public InvalidConfigurationException(String message) {
        super(message);
    }
}
