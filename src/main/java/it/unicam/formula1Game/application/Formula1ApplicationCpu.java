package it.unicam.formula1Game.application;

import it.unicam.formula1Game.engine.CpuGameEngine;
import it.unicam.formula1Game.engine.GameEngine;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.exceptions.InvalidFileFormatException;
import it.unicam.formula1Game.parser.*;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsDetector;
import it.unicam.formula1Game.strategy.landingRegionStrategy.LandingRegionsStrategy;
import it.unicam.formula1Game.strategy.weightedRandomStrategy.WeightedRandomStrategy;
import it.unicam.formula1Game.validator.ConfigurationFileValidator;
import it.unicam.formula1Game.validator.ITrackValidator;
import it.unicam.formula1Game.validator.JsonValidator;
import it.unicam.formula1Game.validator.RaceTrackValidator;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;


import static it.unicam.formula1Game.userInteraction.RequestConfigurationFile.requestConfigurationFile;

/**
 * A CPU-based implementation of the {@link IFormula1Application} interface.
 * This class provides the logic to initialize, validate, and execute a Formula 1 racing game
 * using a predefined configuration file.
 */
public class Formula1ApplicationCpu implements IFormula1Application {
    /**
     * Parser for configuration files.
     */
    private final ConfigurationFileParser fileParser;
    /**
     * Validator for the configuration file format.
     */
    private final ConfigurationFileValidator fileValidator;
    /**
     * Validator for track-related properties.
     */
    private final ITrackValidator trackValidator;
    /**
     * Game engine to manage the game.
     */
    private final GameEngine gameEngine;

    /**
     * Constructs a {@link Formula1ApplicationCpu} with the given dependencies.
     *
     * @param fileParser     the {@link ConfigurationFileParser} to parse configuration files.
     * @param fileValidator  the {@link ConfigurationFileValidator} to validate file formats.
     * @param trackValidator the {@link ITrackValidator} to validate track properties.
     * @param gameEngine     the {@link GameEngine} to manage the game.
     */
    public Formula1ApplicationCpu(ConfigurationFileParser fileParser, ConfigurationFileValidator fileValidator, ITrackValidator trackValidator, GameEngine gameEngine) {
        this.fileParser = fileParser;
        this.fileValidator = fileValidator;
        this.trackValidator = trackValidator;
        this.gameEngine = gameEngine;
    }

    /**
     * Runs the Formula 1 game application by requesting a configuration file.
     * For testing purposes the <code>run</code> methods have been split.
     *
     * @throws Exception if any step in the process fails.
     */
    @Override
    public void run() throws Exception {
        File raceTrackConfigurationFile = requestConfigurationFile();
        run(raceTrackConfigurationFile); // Call the overloaded method
    }

    /**
     * Runs the Formula 1 game application with the specified configuration file by performing the following steps:
     * <ul>
     *     <li>Requests a configuration file from the user.</li>
     *     <li>Validates the file format.</li>
     *     <li>Parses the file into a {@link RaceTrack} object.</li>
     *     <li>Validates the track configuration.</li>
     *     <li>Initializes and starts the game using the {@link GameEngine}.</li>
     * </ul>
     *
     * @param raceTrackConfigurationFile The configuration file for the racetrack.
     * @throws Exception if any step in the process fails.
     */
    public void run(File raceTrackConfigurationFile) throws Exception {
        // Validate the configuration file format
        if (fileValidator.validate(raceTrackConfigurationFile)) {
            // Parse the configuration file
            RaceTrack raceTrack = fileParser.parse(raceTrackConfigurationFile);
            // Validate the track configuration
            if (validate(raceTrack)) {
                gameEngine.initializeEnvironment(raceTrack);
                gameEngine.assignStrategies(chooseStrategies(raceTrack));
                gameEngine.makeFirstMove();
                gameEngine.startGame();
            } else {
                throw new InvalidConfigurationException("The prompted track is not valid");
            }
        } else {
            throw new InvalidFileFormatException("Configuration file format is not valid");
        }
    }

    /**
     * Validates the {@link RaceTrack} configuration using a {@link ITrackValidator} object.
     *
     * @param raceTrack the {@link RaceTrack} to validate.
     * @return <code>true</code> if the track is valid, <code>false</code> otherwise.
     */
    private boolean validate(RaceTrack raceTrack) {
        return trackValidator.validateDirection(raceTrack.getDirection()) &&
                trackValidator.validateHeight(raceTrack.getHeight()) &&
                trackValidator.validateWidth(raceTrack.getWidth()) &&
                trackValidator.validateNumberOfPlayers(raceTrack.getNumberOfPlayers());
    }

    /**
     * Selects strategies for the CPU players in the game.
     *
     * @param raceTrack The {@link RaceTrack} for which the strategies will be assigned.
     * @return A {@link List} of {@link GameStrategy} objects representing the strategies.
     */
    private List<GameStrategy> chooseStrategies(RaceTrack raceTrack) {
        List<GameStrategy> strategies = new ArrayList<>();
        strategies.add(new WeightedRandomStrategy(raceTrack));
        strategies.add(new LandingRegionsStrategy(raceTrack, new LandingRegionsDetector()));
        return strategies;
    }

    /**
     * The main method to run the application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Formula1ApplicationCpu application = new Formula1ApplicationCpu(
                new JsonParser(),
                new JsonValidator(),
                new RaceTrackValidator(),
                new CpuGameEngine()
        );
        try {
            application.run();
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (InvalidConfigurationException e) {
            System.out.println("Invalid configuration: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }


    }
}
