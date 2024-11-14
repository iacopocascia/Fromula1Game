package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidConfigurationException;
import racetrack.RaceTrack;

import java.io.File;
import java.io.IOException;

import static racetrack.Constants.MAX_PLAYERS;
import static racetrack.Constants.MIN_PLAYERS;

/**
 * The {@code JsonParser} class implements the {@link ConfigurationFileParser} interface
 * to provide functionality for parsing JSON configuration files and creating {@link RaceTrack} objects.
 */
public class JsonParser implements ConfigurationFileParser{
    /**
     * Parses the given JSON configuration file to create a {@link RaceTrack} object.
     *
     * @param configurationFile the JSON configuration file to be parsed
     * @return a {@link RaceTrack} object representing the parsed configuration
     * @throws InvalidConfigurationException if the configuration file is invalid, cannot be parsed, or does not conform to the expected JSON format
     */
    @Override
    public RaceTrack parse(File configurationFile) throws InvalidConfigurationException {
        // Ensure the file is a valid JSON
        validateJsonFile(configurationFile);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            // Parse the JSON file into a tree structure
            jsonNode = mapper.readTree(configurationFile);
        } catch (IOException e) {
            throw new InvalidConfigurationException("Error reading the configuration file: " + e.getMessage());
        }
        // Parse and validate the number of players
        int numPlayers = jsonNode.get("numPlayers").asInt();
        validateConfiguration(numPlayers);

        return null;
    }
    /**
     * Validates the number of players to ensure they are within the allowed range.
     *
     * @param numPlayers the number of players
     * @throws InvalidConfigurationException if the number of players is out of the allowed range
     */
    private void validateConfiguration(int numPlayers) throws InvalidConfigurationException {
        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            throw new InvalidConfigurationException("Invalid number of players.");
        }
    }

    /**
     * Validates if the given file is an actual JSON file.
     *
     * @param configurationFile the configuration file to be validated
     * @throws InvalidConfigurationException if the file is not a valid JSON file
     */
    private void validateJsonFile(File configurationFile) throws InvalidConfigurationException {
        if (!configurationFile.getName().endsWith(".json")) {
            throw new InvalidConfigurationException("Not a JSON file.");
        }
    }


}
