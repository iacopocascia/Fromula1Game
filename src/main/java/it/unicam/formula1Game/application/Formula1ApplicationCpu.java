package it.unicam.formula1Game.application;

import it.unicam.formula1Game.engine.CpuGameEngine;
import it.unicam.formula1Game.engine.GameEngine;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.exceptions.InvalidFileFormatException;
import it.unicam.formula1Game.parser.*;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;
import it.unicam.formula1Game.strategy.RandomStrategy;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;

import static it.unicam.formula1Game.userInteraction.RequestConfigurationFile.requestConfigurationFile;

public class Formula1ApplicationCpu implements IFormula1Application {
    private final ConfigurationFileParser fileParser;
    private final ConfigurationFileValidator fileValidator;
    private final ITrackValidator trackValidator;
    private final GameEngine gameEngine;

    public Formula1ApplicationCpu(ConfigurationFileParser fileParser, ConfigurationFileValidator fileValidator, ITrackValidator trackValidator, GameEngine gameEngine) {
        this.fileParser = fileParser;
        this.fileValidator = fileValidator;
        this.trackValidator = trackValidator;
        this.gameEngine = gameEngine;
    }

    /**
     * @throws Exception
     */
    @Override
    public void run() throws Exception {
        File raceTrackConfigurationFile = requestConfigurationFile();
        if(fileValidator.validate(raceTrackConfigurationFile)) {
            RaceTrack raceTrack = fileParser.parse(raceTrackConfigurationFile);
            if (validate(raceTrack)) {
                gameEngine.initializeEnvironment(raceTrack);
                gameEngine.makeFirstMove();
                gameEngine.startGame();
                //gameEngine.endGame();
            } else {
                throw new InvalidConfigurationException("The prompted track is not valid");
            }
        }else {
            throw new InvalidFileFormatException("Configuration file format is not valid");
        }
    }

    private boolean validate(RaceTrack raceTrack) {
        return trackValidator.validateDirection(raceTrack.getDirection()) &&
                trackValidator.validateHeight(raceTrack.getHeight()) &&
                trackValidator.validateWidth(raceTrack.getWidth()) &&
                trackValidator.validateNumberOfPlayers(raceTrack.getNumberOfPlayers());
    }

    public static void main(String[] args) {
        Formula1ApplicationCpu application = new Formula1ApplicationCpu(new JsonParser(), new JsonValidator(), new TrackValidator(), new CpuGameEngine());
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
