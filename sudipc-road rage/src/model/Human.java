package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Human pedestrian in the Road Rage simulation.
 * Implements chooseDirection using a single-return statement for extra credit.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class Human extends AbstractVehicle {

    /** Time this human stays dead after collision. */
    private static final int DEATH_TIME = 45;

    /**
     * Constructs a Human with the given starting position and direction.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     */
    public Human(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Determines if the human can pass the given terrain based on light status.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the current light state
     * @return true if passable based on terrain and light
     */
    @Override
    public boolean canPass(Terrain t, Light l) {
        return t == Terrain.GRASS || (t == Terrain.CROSSWALK && l != Light.GREEN);
    }


    /**
     * Chooses the next direction favoring grass and crosswalks.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return the chosen direction
     */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors) {
        return getWalkableDirections(theNeighbors).get(0); // example: pick first valid
    }

    /**
     * Returns a list of directions the human can walk to.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return list of walkable directions
     */
    private List<Direction> getWalkableDirections(Map<Direction, Terrain> theNeighbors) {
        List<Direction> walkable = new ArrayList<>();
        for (Direction d : Direction.values()) if (canPass(theNeighbors.get(d), Light.RED)) walkable.add(d);
        return walkable;
    }
}
