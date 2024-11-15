package it.unicam.formula1Game.parser;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.io.File;

/**
 * The {@code ConfigurationFileParser} interface defines a method for parsing configuration files
 * to create a {@link RaceTrack} object. Implementations of this interface are responsible for
 * reading the configuration from a given file and translating it into a {@link RaceTrack} instance.
 */
public interface ConfigurationFileParser {
    /**
     * Parses the given configuration file to create a {@link RaceTrack} object.
     *
     * @param configurationFile the configuration file to be parsed
     * @return a {@link RaceTrack} object representing the parsed configuration
     * @throws InvalidConfigurationException if the configuration file is invalid or cannot be parsed
     */
    RaceTrack parse(File configurationFile) throws InvalidConfigurationException;
}
