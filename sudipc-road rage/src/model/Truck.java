package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Truck in the Road Rage simulation.
 * Implements chooseDirection using a single-return statement for extra credit.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class Truck extends AbstractVehicle {

    /** Trucks never die, so death time is 0. */
    private static final int DEATH_TIME = 0;

    /**
     * Constructs a Truck with the given initial state.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     */
    public Truck(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Determines if the truck can pass the given terrain.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the light controlling the terrain
     * @return true if the terrain is drivable
     */
    @Override
    public boolean canPass(Terrain theTerrain, Light theLight) {
        return theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK;
    }

    /**
     * Chooses the next direction using standard street-driving logic.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return the chosen direction
     */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors) {
        return getStreetDirections(theNeighbors).get(0); // pick first valid direction
    }

    /**
     * Returns a list of directions the truck can drive to.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return list of valid street directions
     */
    private List<Direction> getStreetDirections(Map<Direction, Terrain> theNeighbors) {
        List<Direction> valid = new ArrayList<>();
        for (Direction d : Direction.values()) if (canPass(theNeighbors.get(d), Light.GREEN)) valid.add(d);
        return valid;
    }
}
