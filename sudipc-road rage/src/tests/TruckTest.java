package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EnumMap;
import java.util.Map;
import model.*;

/**
 * Unit tests for the Truck class.
 * Verifies construction, movement logic, and collision behavior.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class TruckTest {
    private Truck truck;
    private Map<Direction, Terrain> neighbors;

    /**
     * Sets up a Truck and default neighbor terrains before each test.
     */
    @BeforeEach
    void setUp() {
        truck = new Truck(0, 0, Direction.NORTH);
        neighbors = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) neighbors.put(dir, Terrain.STREET);
    }

    /**
     * Tests the constructor and getter methods.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(0, truck.getX());
        assertEquals(0, truck.getY());
        assertEquals(Direction.NORTH, truck.getDirection());
        assertTrue(truck.isAlive());
        assertEquals(0, truck.getDeathTime());
    }

    /**
     * Verifies terrain and light conditions that a Truck can pass.
     */
    @Test
    void testCanPass() {
        assertTrue(truck.canPass(Terrain.STREET, Light.RED));
        assertTrue(truck.canPass(Terrain.LIGHT, Light.GREEN));
        assertTrue(truck.canPass(Terrain.CROSSWALK, Light.YELLOW));
        assertFalse(truck.canPass(Terrain.GRASS, Light.GREEN));
        assertFalse(truck.canPass(Terrain.TRAIL, Light.GREEN));
    }

    /**
     * Ensures the Truck prefers to move straight when possible.
     */
    @Test
    void testChooseDirectionPrefersStraight() {
        truck.setDirection(Direction.NORTH);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        Direction result = truck.chooseDirection(neighbors);
        assertEquals(Direction.NORTH, result);
    }

    /**
     * Verifies the Truck avoids reversing unless necessary.
     */
    @Test
    void testChooseDirectionAvoidsReverseIfPossible() {
        truck.setDirection(Direction.NORTH);
        neighbors.put(Direction.NORTH, Terrain.GRASS);
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        Direction result = truck.chooseDirection(neighbors);
        assertNotEquals(Direction.SOUTH, result);
    }

    /**
     * Confirms the Truck never dies in collisions.
     */
    @Test
    void testTruckNeverDies() {
        truck.collide(new Car(0, 0, Direction.NORTH));
        assertTrue(truck.isAlive());
        assertEquals("truck.gif", truck.getImageFileName());
    }
}
