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
 * of coordinates, sorted by sections and removing duplicates.
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
        // Filter each section
        List<Coordinate> s1 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row <= 4 && col <= 20);
        List<Coordinate> s2 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row > 4 && col <= 9);
        List<Coordinate> s3 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row > 5 && col > 12 && col <= 26);
        List<Coordinate> s4 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row > 5 && col > 26 && col <= 42);
        List<Coordinate> s5 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row >= 4 && row <= 11 && col >= 41);
        List<Coordinate> s6 = filterCoordinates(allLandingRegionsCoordinates, (row, col) ->
                row <= 3 && col >= 23);
        // Sort each section
        sortCoordinates(s1, Comparator.comparing(Coordinate::getColumn).reversed()
                .thenComparing(Coordinate::getRow));
        sortCoordinates(s2, Comparator.comparing(Coordinate::getRow)
                .thenComparing(Comparator.comparing(Coordinate::getColumn).reversed()));
        sortCoordinates(s3, Comparator.comparing(Coordinate::getRow).reversed()
                .thenComparing(Coordinate::getColumn));
        sortCoordinates(s4, Comparator.comparing(Coordinate::getRow)
                .thenComparing(Coordinate::getColumn));
        sortCoordinates(s5, Comparator.comparing(Coordinate::getRow).reversed()
                .thenComparing(Coordinate::getColumn));
        sortCoordinates(s6, Comparator.comparing(Coordinate::getColumn).reversed()
                .thenComparing(Comparator.comparing(Coordinate::getRow).reversed()));
        // Reassemble the coordinates
        List<Coordinate> finalPath = reassembleCoordinates(s1, s2, s3, s4, s5, s6);
        finalPath.addAll(raceTrack.getFinishCoordinates());
        return finalPath;
    }

    /**
     * Reassembles coordinates from multiple lists and removes duplicates.
     *
     * @param quadrants The lists of coordinates from the sections.
     * @return A unified list of coordinates without duplicates.
     */
    @SafeVarargs
    private List<Coordinate> reassembleCoordinates(List<Coordinate>... quadrants) {
        return Arrays.stream(quadrants)
                .flatMap(List::stream) // Combine all sections into a single stream
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
