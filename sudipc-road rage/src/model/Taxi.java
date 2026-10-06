package model;

import java.util.Map;

/**
 * Represents a Taxi in the Road Rage simulation.
 * Taxis behave similarly to cars but may move on crosswalks
 * when the light is green.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class Taxi extends AbstractVehicle {

    /** Time this taxi stays dead after collision. */
    private static final int DEATH_TIME = 10;

    /**
     * Constructs a Taxi with the given initial state.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     */
    public Taxi(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Determines if the taxi can pass the given terrain based on light conditions.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the current traffic light state
     * @return true if the taxi can move through the terrain
     */
    @Override
    public boolean canPass(Terrain theTerrain, Light theLight) {
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT)
            return theLight != Light.RED;
        if (theTerrain == Terrain.CROSSWALK)
            return theLight == Light.GREEN;
        return false;
    }

    /**
     * Chooses the next direction using default street-based logic.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return the chosen direction
     */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors) {
        return defaultChooseStreetDir(theNeighbors);
    }
}
