package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a Bicycle in the Road Rage simulation.
 * Implements chooseDirection using a single-return statement for extra credit.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class Bicycle extends AbstractVehicle {

    /** Time this bicycle stays dead after collision. */
    private static final int DEATH_TIME = 35;

    /**
     * Constructs a Bicycle with the given position and direction.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     */
    public Bicycle(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Determines if the bicycle can pass the given terrain under the current light.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the light controlling the terrain
     * @return true if the terrain is a valid path based on light rules
     */
    @Override
    public boolean canPass(Terrain t, Light l) {
        return t == Terrain.TRAIL || (t == Terrain.CROSSWALK && l == Light.GREEN);
    }

    /**
     * Chooses the next movement direction using standard street-based logic.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return the chosen direction based on street preferences
     */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> n) {
        Direction r = getDirection().reverse();
        List<Direction> trails = n.entrySet().stream()
                .filter(e -> e.getKey() != r && e.getValue() == Terrain.TRAIL)
                .map(Map.Entry::getKey)
                .toList();
        List<Direction> cross = n.entrySet().stream()
                .filter(e -> e.getKey() != r && e.getValue() == Terrain.CROSSWALK)
                .map(Map.Entry::getKey)
                .toList();
        List<Direction> valid = !trails.isEmpty() ? trails : !cross.isEmpty() ? cross : n.entrySet().stream()
                .filter(e -> e.getKey() != r && canPass(e.getValue(), Light.GREEN))
                .map(Map.Entry::getKey)
                .toList();
        return valid.isEmpty() ? r : valid.get(ThreadLocalRandom.current().nextInt(valid.size()));
    }


    /**
     * Returns a list of directions the bicycle can move to.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return list of valid directions
     */
    private List<Direction> getStreetDirections(Map<Direction, Terrain> theNeighbors) {
        List<Direction> valid = new ArrayList<>();
        for (Direction d : Direction.values()) if (canPass(theNeighbors.get(d), Light.GREEN)) valid.add(d);
        return valid;
    }
}
