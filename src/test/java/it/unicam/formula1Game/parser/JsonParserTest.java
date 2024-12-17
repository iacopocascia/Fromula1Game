package it.unicam.formula1Game.parser;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import it.unicam.formula1Game.racetrack.RaceTrack;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonParserTest {
    public final static String filePath="src/jsonRaceTracks/track.json";
    JsonParser parser=new JsonParser();
    @Test
    public void parse_test_valid() throws InvalidConfigurationException {
        RaceTrack raceTrack=parser.parse(new File(filePath));
        assertEquals(raceTrack.getWidth(),50);
        assertEquals(raceTrack.getHeight(),15);
        assertEquals(raceTrack.getNumberOfPlayers(),2);
        assertEquals(raceTrack.getDirection(),"ccw");
        assertEquals(raceTrack.getGrid().length,15);
        assertEquals(raceTrack.getGrid()[0].length,50);
        assertEquals(raceTrack.getGrid()[14].length,50);
        assertEquals(raceTrack.getCellAt(new Coordinate(1, 21)).cellType(), CellType.START);
    }

}
