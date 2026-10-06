package model;

import java.util.Map;

/**
 * Represents a Car in the Road Rage simulation.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class Car extends AbstractVehicle {

    /** Time this car stays dead after collision. */
    private static final int DEATH_TIME = 5;

    /**
     * Constructs a Car with the given starting position and direction.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     */
    public Car(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Determines if the car can pass a given terrain and light condition.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the light controlling the terrain
     * @return true if allowed to pass under the light’s state
     */
    @Override
    public boolean canPass(Terrain t, Light l) {
        return t == Terrain.STREET || t == Terrain.LIGHT
                || (t == Terrain.CROSSWALK && l == Light.GREEN);
    }

    /**
     * Chooses the next direction using default street movement logic.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return the chosen direction
     */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors) {
        return defaultChooseStreetDir(theNeighbors);
    }
}
