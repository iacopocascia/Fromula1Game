package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Cell;
import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the detection of landing regions in a racetrack.
 * Landing regions represent critical segments in the track where players
 * might need to adjust their strategy, such as corners or transitions between straightaways.
 */
public class LandingRegionsDetector implements ILandingRegionsDetector {
    /**
     * @return
     */
    @Override
    public List<Segment> detectLandingRegions(RaceTrack raceTrack) {
        Map<Integer, List<Segment>> horizontalSegments = calculateSegments(true, raceTrack);
        Map<Integer, List<Segment>> verticalSegments = calculateSegments(false, raceTrack);
        List<Segment> horizontalLandingRegions = applyDetectionLogic(horizontalSegments);
        List<Segment> verticalLandingRegions = applyDetectionLogic(verticalSegments);
        return Stream.concat(horizontalLandingRegions.stream(), verticalLandingRegions.stream())
                .collect(Collectors.toList());
    }

    /**
     * Finds segments of contiguous track cells in the grid, either horizontally or vertically.
     *
     * @param isRowBased a flag indicating whether to find horizontal segments (true) or vertical segments (false).
     * @param track      the {@link RaceTrack} object where to calculate the segments.
     * @return a {@link Map} where the key is the row or column index, and the value is a {@link List} of {@link Segment} objects.
     */
    private Map<Integer, List<Segment>> calculateSegments(boolean isRowBased, RaceTrack track) {
        Map<Integer, List<Segment>> segmentsMap = new HashMap<>();

        int outerLimit = isRowBased ? track.getHeight() : track.getWidth();
        int innerLimit = isRowBased ? track.getWidth() : track.getHeight();
        for (int outer = 0; outer < outerLimit; outer++) {
            List<Segment> segments = new ArrayList<>();
            Segment currentSegment = null;

            for (int inner = 0; inner < innerLimit; inner++) {
                Cell cell = isRowBased ? track.getGrid()[outer][inner] : track.getGrid()[inner][outer];

                if (cell.cellType() == CellType.TRACK) {
                    if (currentSegment == null) {
                        currentSegment = new Segment();
                    }
                    currentSegment.addNewPoint(isRowBased
                            ? new Coordinate(outer, inner)
                            : new Coordinate(inner, outer));
                } else if (currentSegment != null) {
                    segments.add(currentSegment);
                    currentSegment = null;
                }
            }
            // Add the last segment in the row/column if it exists
            if (currentSegment != null) {
                segments.add(currentSegment);
            }
            if (!segments.isEmpty()) {
                segmentsMap.put(outer, segments);
            }
        }
        return segmentsMap;
    }

    /**
     * Detects landing regions based on changes in the number of track segments.
     * A landing region is identified when the number of segments increases or decreases
     * (excluding transitions from 0 to n or n to 0).
     *
     * @param segmentsMap A {@link Map} where the key represents the row or column index
     *                    and the value is a {@link List} of {@link Segment} objects for that index.
     * @return A {@link List} of {@link Segment} objects representing the landing regions.
     */
    public List<Segment> applyDetectionLogic(Map<Integer, List<Segment>> segmentsMap) {
        List<Segment> landingRegions = new ArrayList<>();
        for (int i = 1; i < segmentsMap.size(); i++) {
            int currentCount = segmentsMap.get(i).size();
            int previousCount = segmentsMap.get(i - 1).size();
            if (currentCount != 0 && previousCount != 0) {
                if (previousCount != currentCount) {
                    landingRegions.addAll(segmentsMap.get(i));
                }

            }
        }
        return landingRegions;
    }
}
