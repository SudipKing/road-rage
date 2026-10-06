package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * An abstract base class implementing shared vehicle behavior for the Road Rage simulation.
 * This class defines core vehicle properties such as position, direction, and death status,
 * and provides default implementations for common movement and collision mechanics.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public abstract class AbstractVehicle implements Vehicle {

    /** The current x-coordinate of this vehicle. */
    private int x;

    /** The current y-coordinate of this vehicle. */
    private int y;

    /** The current direction of travel. */
    private Direction direction;

    /** The number of updates this vehicle remains dead. */
    private final int deathTime;

    /** The remaining time this vehicle stays dead. */
    private int deathCounter;

    /** The starting x-coordinate of this vehicle. */
    private final int startX;

    /** The starting y-coordinate of this vehicle. */
    private final int startY;

    /** The initial facing direction of this vehicle. */
    private final Direction startDirection;

    /** Random generator used for direction selection. */
    private final Random random = new Random();

    /**
     * Constructs a new vehicle with the specified initial state.
     *
     * @param theX the starting x-coordinate
     * @param theY the starting y-coordinate
     * @param theDir the initial direction
     * @param theDeathTime the number of updates the vehicle stays dead
     */
    protected AbstractVehicle(int theX, int theY, Direction theDir, int theDeathTime) {
        x = theX;
        y = theY;
        startX = theX;
        startY = theY;
        direction = theDir;
        startDirection = theDir;
        deathTime = theDeathTime;
        deathCounter = 0;
    }

    /** @return the current x-coordinate */
    public int getX() { return x; }

    /** @return the current y-coordinate */
    public int getY() { return y; }

    /** @return {@code true} if this vehicle is alive; {@code false} otherwise */
    public boolean isAlive() { return deathCounter == 0; }

    /** @return the current facing direction */
    public Direction getDirection() { return direction; }

    /** @return the total time this vehicle remains dead */
    public int getDeathTime() { return deathTime; }

    /**
     * Sets the vehicle’s x-coordinate.
     *
     * @param theX the new x-coordinate
     */
    public void setX(int theX) { x = theX; }

    /**
     * Sets the vehicle’s y-coordinate.
     *
     * @param theY the new y-coordinate
     */
    public void setY(int theY) { y = theY; }

    /**
     * Updates the vehicle’s facing direction.
     *
     * @param theDir the new direction
     */
    public void setDirection(Direction theDir) { direction = theDir; }

    /**
     * Handles collision logic between this vehicle and another.
     *
     * @param theOther the other vehicle involved in the collision
     */
    public void collide(Vehicle theOther) {
        if (isAlive() && theOther.isAlive() && deathTime > theOther.getDeathTime()) {
            deathCounter = deathTime;
        }
    }

    /**
     * Advances this vehicle’s state by one update tick.
     */
    public void poke() {
        if (!isAlive() && deathCounter > 0) {
            deathCounter--;
            if (deathCounter == 0) direction = Direction.random();
        }
    }

    /**
     * Resets this vehicle to its initial state:
     * starting position, direction, and alive status.
     */
    public void reset() {
        x = startX;
        y = startY;
        direction = startDirection;
        deathCounter = 0;
    }

    /**
     * Returns the image filename for this vehicle.
     *
     * @return the image filename associated with this vehicle’s state
     */
    public String getImageFileName() {
        String base = getClass().getSimpleName().toLowerCase();
        return base + (isAlive() ? ".gif" : "_dead.gif");
    }

    /**
     * Returns a text representation of this vehicle including
     * class name, coordinates, direction, and alive/dead status.
     *
     * @return a formatted string representing this vehicle
     */
    public String toString() {
        return getClass().getSimpleName() + " [" + x + "," + y + "] " + direction + (isAlive() ? " alive" : " dead");
    }

    /**
     * Collects all possible movement directions based on valid terrains.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @param theTerrains the allowed terrain types
     * @return a list of directions that this vehicle may move toward
     */
    protected List<Direction> validMoves(Map<Direction, Terrain> theNeighbors, Terrain... theTerrains) {
        List<Direction> dirs = new ArrayList<>();
        for (Map.Entry<Direction, Terrain> e : theNeighbors.entrySet()) {
            for (Terrain t : theTerrains) {
                if (e.getValue() == t && e.getKey() != direction.reverse()) {
                    dirs.add(e.getKey());
                }
            }
        }
        return dirs;
    }

    /**
     * Returns a random direction from a given list.
     *
     * @param dirs a list of possible directions
     * @return one randomly chosen direction
     */
    protected Direction randomFrom(List<Direction> dirs) {
        return dirs.get(random.nextInt(dirs.size()));
    }

    /**
     * Provides a default directional choice for street-based movement.
     *
     * @param theNeighbors the surrounding terrains
     * @return the chosen direction based on street preference logic
     */
    protected Direction defaultChooseStreetDir(Map<Direction, Terrain> theNeighbors) {
        Direction dir = getDirection();
        if (theNeighbors.get(dir) == Terrain.STREET
                || theNeighbors.get(dir) == Terrain.LIGHT
                || theNeighbors.get(dir) == Terrain.CROSSWALK
                || theNeighbors.get(dir) == Terrain.TRAIL) {
            return dir;
        } else if (theNeighbors.get(dir.left()) == Terrain.STREET
                || theNeighbors.get(dir.left()) == Terrain.LIGHT
                || theNeighbors.get(dir.left()) == Terrain.CROSSWALK
                || theNeighbors.get(dir.left()) == Terrain.TRAIL) {
            return dir.left();
        } else if (theNeighbors.get(dir.right()) == Terrain.STREET
                || theNeighbors.get(dir.right()) == Terrain.LIGHT
                || theNeighbors.get(dir.right()) == Terrain.CROSSWALK
                || theNeighbors.get(dir.right()) == Terrain.TRAIL) {
            return dir.right();
        } else {
            return dir.reverse();
        }
    }

    /**
     * Provides a default directional choice for walkable entities
     * such as humans, preferring grass or crosswalks.
     *
     * @param theNeighbors the surrounding terrains
     * @return the chosen direction based on walkable terrain preference
     */
    protected Direction defaultChooseWalkableDir(Map<Direction, Terrain> theNeighbors) {
        Direction dir = getDirection();
        if (theNeighbors.get(dir) == Terrain.GRASS || theNeighbors.get(dir) == Terrain.CROSSWALK) {
            return dir;
        } else if (theNeighbors.get(dir.left()) == Terrain.GRASS || theNeighbors.get(dir.left()) == Terrain.CROSSWALK) {
            return dir.left();
        } else if (theNeighbors.get(dir.right()) == Terrain.GRASS || theNeighbors.get(dir.right()) == Terrain.CROSSWALK) {
            return dir.right();
        } else {
            return dir.reverse();
        }
    }

    /**
     * Determines whether this vehicle may move onto a given terrain,
     * considering the state of a traffic light.
     *
     * @param theTerrain the terrain being evaluated
     * @param theLight the light controlling the terrain
     * @return {@code true} if this vehicle may enter the terrain; otherwise {@code false}
     */
    public abstract boolean canPass(Terrain theTerrain, Light theLight);

    /**
     * Chooses the next movement direction based on surrounding terrains.
     *
     * @param theNeighbors the surrounding terrains mapped by direction
     * @return the chosen movement direction
     */
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);
}
