package it.unicam.formula1Game.cell;

import it.unicam.formula1Game.racetrack.RaceTrack;

/**
 * Represents a single cell in the track.
 *
 * @param cellType The type of the cell, represented as a {@link CellType}.
 * @param position The cell's position in the {@link RaceTrack} as a {@link Coordinate}.
 */
public record Cell(CellType cellType, Coordinate position) {
}
