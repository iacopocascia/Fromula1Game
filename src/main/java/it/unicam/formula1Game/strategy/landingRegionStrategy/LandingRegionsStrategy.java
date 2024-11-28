package it.unicam.formula1Game.strategy.landingRegionStrategy;

import it.unicam.formula1Game.cell.CellType;
import it.unicam.formula1Game.cell.Coordinate;
import it.unicam.formula1Game.player.CpuPlayer;
import it.unicam.formula1Game.racetrack.RaceTrack;
import it.unicam.formula1Game.strategy.GameStrategy;

import java.util.ArrayList;
import java.util.List;

public class LandingRegionsStrategy implements GameStrategy {
    private final RaceTrack track;
    private final CpuPlayer player;

    public LandingRegionsStrategy(RaceTrack track, CpuPlayer player) {
        this.track = track;
        this.player = player;
    }

    /**
     *
     */
    @Override
    public void applyStrategy() {

    }

    /**
     * @return
     */
    @Override
    public List<Coordinate> getAvailableMoves() {
        return List.of();
    }
}
