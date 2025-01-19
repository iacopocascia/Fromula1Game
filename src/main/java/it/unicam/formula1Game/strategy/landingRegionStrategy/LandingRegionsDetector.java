package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.*;

/**
 * The {@code LandingRegionsDetector} class implements the {@link LandingRegionsDetector} interface.
 * It is responsible for detecting landing regions in a {@link RaceTrack}.
 * Landing regions represent critical zones where players may need to adjust their velocity,
 * such as corners or transitions between straightaways.
 */
public class LandingRegionsDetector implements ILandingRegionsDetector {
    /**
     * Predefined boundaries for landing regions in the track.
     * Each region is represented as an array of integers:
     * [minRow, maxRow, minCol, maxCol].
     */
    private static final List<int[]> LANDING_REGIONS_BOUNDARIES = List.of(
            new int[]{1, 4, 11, 12},   // Region 0: row 1-4, column 11-12
            new int[]{11, 12, 0, 8}, // Region 1: row 11-12, column 0-8
            new int[]{11, 13, 12, 13}, // Region 2: row 11-13, column 12-13
            new int[]{7, 8, 16, 23}, // Region 3: row 7-8, column 16-23
            new int[]{6, 9, 28, 29},  // Region 4: row 6-9, column 28-29
            new int[]{11, 12, 33, 38},  // Region 5: row 11-12, column 33-38
            new int[]{11, 13, 42, 43},  // Region 6: row 11-13, column 42-43
            new int[]{4, 5, 38, 47}     // Region 7: row 4-5, column 38-47

    );

    /**
     * Detects landing regions in the given {@link RaceTrack}.
     * Each region is identified based on predefined boundaries, and the cells
     * that belong to these regions are grouped into corresponding {@link LandingRegion} objects.
     *
     * @param raceTrack The {@link RaceTrack} to analyze for landing regions.
     * @return A {@link List} of {@link LandingRegion} objects representing the detected regions.
     */
    @Override
    public List<LandingRegion> detectLandingRegions(RaceTrack raceTrack) {
        // Initialize landing regions
        List<LandingRegion> landingRegions = new ArrayList<>();
        for (int i = 0; i < LANDING_REGIONS_BOUNDARIES.size(); i++) {
            landingRegions.add(new LandingRegion(new HashSet<>()));
        }
        // Get track coordinates
        List<Coordinate> trackCoordinates = getTrackCoordinates(raceTrack);
        // Assign each coordinate to the appropriate landing region
        for (Coordinate coordinate : trackCoordinates) {
            for (int i = 0; i < LANDING_REGIONS_BOUNDARIES.size(); i++) {
                int[] boundaries = LANDING_REGIONS_BOUNDARIES.get(i);
                if (isWithinBounds(coordinate, boundaries)) {
                    landingRegions.get(i).addNewCell(coordinate);
                }
            }
        }
        // Add the finish line coordinates as the last landing region
        addFinishLine(landingRegions, raceTrack);

        return landingRegions;
    }

    /**
     * Adds the finish line coordinates as the final landing region in the list.
     *
     * @param landingRegions The list of {@link LandingRegion} objects to which the finish line is added.
     * @param raceTrack      The {@link RaceTrack} from which to extract finish line coordinates.
     */
    private void addFinishLine(List<LandingRegion> landingRegions, RaceTrack raceTrack) {
        LandingRegion finishLine = new LandingRegion(new HashSet<>());
        finishLine.getCells().addAll(raceTrack.getFinishCoordinates());
        landingRegions.add(finishLine);
    }

    /**
     * Retrieves all the track cells from the given {@link RaceTrack}.
     * Track cells are those that are not marked as walls.
     *
     * @param raceTrack The {@link RaceTrack} to extract track coordinates from.
     * @return A {@link List} of {@link Coordinate} objects representing the track cells.
     */
    private List<Coordinate> getTrackCoordinates(RaceTrack raceTrack) {
        List<Coordinate> track = new ArrayList<>();
        for (int row = 0; row < raceTrack.getHeight(); row++) {
            for (int col = 0; col < raceTrack.getWidth(); col++) {
                Coordinate coordinate = new Coordinate(row, col);
                if (raceTrack.getCellAt(coordinate).cellType() != CellType.WALL) {
                    track.add(coordinate);
                }
            }
        }
        return track;
    }

    /**
     * Checks if a coordinate is within the specified boundaries.
     *
     * @param coordinate The {@link Coordinate} to check.
     * @param boundaries An array representing [minRow, maxRow, minCol, maxCol].
     * @return <code>true</code> if the coordinate is within bounds; <code>false</code> otherwise.
     */
    private boolean isWithinBounds(Coordinate coordinate, int[] boundaries) {
        int row = coordinate.getRow();
        int col = coordinate.getColumn();
        return row >= boundaries[0] && row <= boundaries[1] && col >= boundaries[2] && col <= boundaries[3];
    }

}
