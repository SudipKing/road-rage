package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an All-Terrain Vehicle (ATV) in the Road Rage simulation.
 * Implements chooseDirection using a single-return statement for extra credit.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class Atv extends AbstractVehicle {

    /** Time this ATV stays dead after collision. */
    private static final int DEATH_TIME = 20;

    /** Random generator for direction selection. */
    private final Random myRand = new Random();

    /**
     * Constructs an ATV with the specified initial position and direction.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     */
    public Atv(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * Determines if the ATV can pass the given terrain and light condition.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the light controlling the terrain
     * @return true if the terrain is not a wall
     */
    @Override
    public boolean canPass(Terrain theTerrain, Light theLight) {
        return theTerrain != Terrain.WALL;
    }

    /**
     * Chooses a random direction from all valid passable neighbors.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return a randomly chosen valid direction
     */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors) {
        return getValidDirections(theNeighbors).get(myRand.nextInt(getValidDirections(theNeighbors).size()));
    }

    /**
     * Returns a list of directions this ATV can move to.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return list of valid directions
     */
    private List<Direction> getValidDirections(Map<Direction, Terrain> theNeighbors) {
        List<Direction> valid = new ArrayList<>();
        for (Direction d : Direction.values()) if (canPass(theNeighbors.get(d), Light.GREEN)) valid.add(d);
        return valid;
    }
}
