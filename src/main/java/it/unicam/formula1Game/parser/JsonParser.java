package it.unicam.formula1Game.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.formula1Game.cell.Cell;
import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.io.File;
import java.io.IOException;

import static it.unicam.formula1Game.racetrack.Constants.*;

/**
 * The {@code JsonParser} class implements the {@link ConfigurationFileParser} interface
 * to provide functionality for parsing JSON configuration files and creating {@link RaceTrack} objects.
 */
public class JsonParser implements ConfigurationFileParser {
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
        //validateJsonFile(configurationFile);
        JsonNode jsonNode = readJson(configurationFile);
        // Parse track's data
        int width = jsonNode.get("width").asInt();
        int height = jsonNode.get("height").asInt();
        int numPlayers = jsonNode.get("numPlayers").asInt();
        String direction = jsonNode.get("direction").asText();
        // Validate the parsed values
        //validateConfiguration(width, numPlayers, height);
        // Parse the track grid
        Cell[][] grid = parseGrid(jsonNode, width, height);

        return new RaceTrack(width, height, grid, numPlayers);
    }

    /**
     * Read the json file parsing it as a tree structure.
     *
     * @param configurationFile the JSON configuration file to be parsed
     * @return a {@link JsonNode} object
     * @throws InvalidConfigurationException if there was an error reading the file
     */
    private JsonNode readJson(File configurationFile) throws InvalidConfigurationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a tree structure
            return mapper.readTree(configurationFile);
        } catch (IOException e) {
            throw new InvalidConfigurationException("Error reading the configuration file: " + e.getMessage());
        }
    }

    /**
     * Parses the track grid from the JSON object and converts it into a 2D array of {@link Cell}.
     *
     * @param jsonNode the root JSON node containing the configuration
     * @param width    the width of the track
     * @param height   the height of the track
     * @return a 2D array of {@link Cell} representing the parsed track grid
     * @throws InvalidConfigurationException if the grid is missing, improperly formatted, or contains invalid characters
     */
    private Cell[][] parseGrid(JsonNode jsonNode, int width, int height) throws InvalidConfigurationException {
        // Get the track array from the JSON
        JsonNode trackArray = jsonNode.get("track");
        if (trackArray == null || !trackArray.isArray()) {
            throw new InvalidConfigurationException("Track layout is missing or not properly formatted.");
        }

        // Ensure the number of rows matches the specified height
        if (trackArray.size() != height) {
            throw new InvalidConfigurationException("Track height does not match the specified height.");
        }

        // Create the grid
        return createGrid(trackArray, width, height);
    }

    /**
     * Creates the grid of {@link Cell} objects based on the track array.
     *
     * @param trackArray the track array node
     * @param width      the width of the track
     * @param height     the height of the track
     * @return a 2D array of {@link Cell} objects
     * @throws InvalidConfigurationException if invalid characters are encountered
     */
    private Cell[][] createGrid(JsonNode trackArray, int width, int height) throws InvalidConfigurationException {
        Cell[][] grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            String row = trackArray.get(i).asText();
            if (row.length() != width) {
                throw new InvalidConfigurationException("Track width does not match the specified width");
            }
            for (int j = 0; j < width; j++) {
                char cellChar = row.charAt(j);
                grid[i][j] = createCell(cellChar, i, j);
            }
        }
        return grid;
    }

    /**
     * Creates a {@link Cell} object based on the given character.
     *
     * @param cellChar the character representing the cell
     * @param row      the row index of the cell
     * @param column   the column index of the cell
     * @return a {@link Cell} object
     */
    private Cell createCell(char cellChar, int row, int column) throws InvalidConfigurationException {
        CellType cellType = CellType.fromChar(cellChar);
        if (cellType == null) {
            throw new InvalidConfigurationException("Invalid cell character '" + cellChar + "' at (" + row + ", " + column + ")");
        }
        return new Cell(cellType, new Coordinate(row, column));
    }

    /**
     * Validates the width, height and number of players to ensure they are within the allowed range.
     *
     * @param width      the track's total width
     * @param height     trac track's total height
     * @param numPlayers the number of players
     * @throws InvalidConfigurationException if the number of players is out of the allowed range
     */
    private void validateConfiguration(int width, int height, int numPlayers) throws InvalidConfigurationException {
        if (width < 0 || width > MAX_WIDTH
                || height < 0 || height > MAX_HEIGHT
                || numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
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
