package it.unicam.formula1Game.racetrack;

import it.unicam.formula1Game.cell.Cell;
import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.exceptions.InvalidConfigurationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceTrackTest {
    private final int width = 2;
    private final int height = 2;
    private final Cell[][] grid = new Cell[][]{
            // row 1
            {new Cell(CellType.WALL, new Coordinate(0, 0)), new Cell(CellType.START, new Coordinate(0, 1))},
            // row 2
            {new Cell(CellType.FINISH, new Coordinate(1, 0)), new Cell(CellType.TRACK, new Coordinate(1, 1))}
    };
    private final int numPlayers = 5;
    private final String direction = "ccw";
    private RaceTrack raceTrack=new RaceTrack(width, height, grid, numPlayers, direction);

    public RaceTrackTest() throws InvalidConfigurationException {
    }

    @Test
    public void test_racetrack_constructor() throws InvalidConfigurationException {
        assertEquals(raceTrack.getWidth(), width);
        assertEquals(raceTrack.getHeight(), height);
        assertEquals(raceTrack.getGrid(), grid);
        assertEquals(raceTrack.getNumberOfPlayers(), numPlayers);
        assertEquals(raceTrack.getDirection(), direction);
    }

    @Test
    public void test_visual_grid_representation() throws InvalidConfigurationException {
        assertEquals(raceTrack.getVisualGridRepresentation()[0][0], "*");
        assertEquals(raceTrack.getVisualGridRepresentation()[0][1], "+");
        assertEquals(raceTrack.getVisualGridRepresentation()[1][0], "-");
        assertEquals(raceTrack.getVisualGridRepresentation()[1][1], " ");
    }
    @Test
    public void test_get_cell_at() throws InvalidConfigurationException {
        Coordinate coordinate=new Coordinate(0,0);
        assertEquals(raceTrack.getCellAt(coordinate),new Cell(CellType.WALL,coordinate));
        coordinate=new Coordinate(1,1);
        assertEquals(raceTrack.getCellAt(coordinate),new Cell(CellType.TRACK,coordinate));
    }
    @Test
    public void test_get_start_coordinates() throws InvalidConfigurationException {
        List<Coordinate> startCoordinates = raceTrack.getStartCoordinates();
        assertNotNull(startCoordinates);
        assertEquals(startCoordinates.size(),1);
        assertEquals(startCoordinates.get(0),new Coordinate(0,1));
    }
    @Test
    public void test_get_finish_coordinates(){
        List<Coordinate> finishCoordinates = raceTrack.getFinishCoordinates();
        assertNotNull(finishCoordinates);
        assertEquals(finishCoordinates.size(),1);
        assertEquals(finishCoordinates.get(0),new Coordinate(1,0));
    }
    @Test
    public void test_is_within_boundaries(){
        assertTrue(raceTrack.isWithinBoundaries(new Coordinate(0,0)));
        assertTrue(raceTrack.isWithinBoundaries(new Coordinate(1,1)));
        assertFalse(raceTrack.isWithinBoundaries(new Coordinate(0,2)));
        assertFalse(raceTrack.isWithinBoundaries(new Coordinate(2,0)));
    }
}
