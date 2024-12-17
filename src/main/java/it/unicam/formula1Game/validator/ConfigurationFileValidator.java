package it.unicam.formula1Game.validator;

import java.io.File;

/**
 * The {@code ConfigurationFileValidator} interface defines the contract for validating
 * configuration files used in the game.
 */
public interface ConfigurationFileValidator {
    /**
     * Validates the given configuration file to ensure it meets the required criteria.
     *
     * @param configurationFile The {@link File} object representing the configuration file to validate.
     * @return {@code true} if the file is valid, {@code false} otherwise.
     */
    boolean validate(File configurationFile);
}
