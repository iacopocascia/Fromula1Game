package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.racetrack.RaceTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * The {@code LandingRegionsElaborator} class implements the {@link ILandingRegionsElaborator} interface.
 * It is responsible for processing and organizing landing region segments into prioritized lists
 * of coordinates, sorted by quadrants and removing duplicates.
 */
public class LandingRegionsElaborator implements ILandingRegionsElaborator {
    /**
     * Elaborates landing regions by extracting their coordinates, dividing them into sections,
     * sorting each section, and reassembling the coordinates into a unified prioritized list.
     *
     * @param landingRegions A {@link List} of {@link Segment} objects representing landing regions.
     * @return A {@link List} of {@link Coordinate} objects representing the processed and prioritized landing regions.
     */
    @Override
    public List<Coordinate> elaborateLandingRegions(List<Segment> landingRegions, RaceTrack raceTrack) {
        // Extract coordinates
        List<Coordinate> allLandingRegionsCoordinates = extractCoordinates(landingRegions);
        // Filter each quadrant
        List<Coordinate> s1 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row <= 6 && col <= 21);
        List<Coordinate> s2 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row > 6 && col <= 12);
        List<Coordinate> s3 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row > 5 / 2 && col > 12 && col<=26);
        List<Coordinate> q4 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row <= raceTrack.getHeight() / 2 && col > raceTrack.getWidth() / 2);
        // Sort each quadrant
        sortCoordinates(s1, Comparator.comparing(Coordinate::getRow)
                .thenComparing(Coordinate::getColumn).reversed());
        sortCoordinates(s2, Comparator.comparing(Coordinate::getRow)
                .thenComparing(Coordinate::getColumn));
        sortCoordinates(s3, Comparator.comparing(Coordinate::getColumn)
                .thenComparing(Coordinate::getRow).reversed());
        sortCoordinates(q4, Comparator.comparing(Coordinate::getRow).reversed()
                .thenComparing(Coordinate::getColumn).reversed());
        // Reassemble the coordinates
        List<Coordinate> finalPath = reassembleCoordinates(s1, s2, s3, q4);
        finalPath.addAll(raceTrack.getFinishCoordinates());
        return finalPath;
    }

    /**
     * Reassembles coordinates from multiple lists and removes duplicates.
     *
     * @param quadrants The lists of coordinates from the quadrants.
     * @return A unified list of coordinates without duplicates.
     */
    @SafeVarargs
    private List<Coordinate> reassembleCoordinates(List<Coordinate>... quadrants) {
        return Arrays.stream(quadrants)
                .flatMap(List::stream) // Combine all quadrants into a single stream
                .distinct()           // Remove duplicate coordinates
                .collect(Collectors.toCollection(ArrayList::new)); // Collect into a mutable list
    }

    /**
     * Extracts all coordinates from a list of landing region segments.
     *
     * @param landingRegions A {@link List} of {@link Segment} objects representing landing regions.
     * @return A {@link List} of {@link Coordinate} objects from all segments.
     */
    private List<Coordinate> extractCoordinates(List<Segment> landingRegions) {
        List<Coordinate> allCoordinates = new ArrayList<>();
        for (Segment segment : landingRegions) {
            allCoordinates.addAll(segment.getPoints());
        }
        return allCoordinates;
    }

    /**
     * Filters coordinates based on the condition defined by a BiPredicate.
     *
     * @param allCoordinates The list of all coordinates to filter.
     * @param condition      The condition to apply for filtering, taking row and column values.
     * @return A list of coordinates that match the condition.
     */
    private List<Coordinate> filterCoordinates(List<Coordinate> allCoordinates, BiPredicate<Integer, Integer> condition) {
        List<Coordinate> filteredCoordinates = new ArrayList<>();
        for (Coordinate coordinate : allCoordinates) {
            if (condition.test(coordinate.getRow(), coordinate.getColumn())) {
                filteredCoordinates.add(coordinate);
            }
        }
        return filteredCoordinates;
    }

    /**
     * Sorts a list of coordinates based on the given comparator.
     *
     * @param coordinates The list of coordinates to sort.
     * @param comparator  The comparator defining the sorting logic.
     */
    private void sortCoordinates(List<Coordinate> coordinates, Comparator<Coordinate> comparator) {
        coordinates.sort(comparator);
    }
}
