package it.unicam.formula1Game.exceptions;

/**
 * Thrown to indicate that an unsupported file format has been prompted.
 */
public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}
