package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.parser.JsonParser;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.io.File;

import static it.unicam.formula1Game.parser.JsonParserTest.filePath;

public class RandomStrategyTest {
    private final JsonParser parser=new JsonParser();
    RaceTrack raceTrack = parser.parse(new File(filePath));

    public RandomStrategyTest() throws InvalidConfigurationException {
    }

}
