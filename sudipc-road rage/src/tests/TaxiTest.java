package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EnumMap;
import java.util.Map;
import model.*;

/**
 * Unit tests for the Taxi class.
 * Validates passing logic, image changes, and revival behavior.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class TaxiTest {
    private Taxi taxi;
    private Map<Direction, Terrain> neighbors;

    /**
     * Initializes a Taxi and sets default neighbors before each test.
     */
    @BeforeEach
    void setUp() {
        taxi = new Taxi(0, 0, Direction.NORTH);
        neighbors = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) neighbors.put(dir, Terrain.STREET);
    }

    /**
     * Tests Taxi logic for crossing various terrains and lights.
     */
    @Test
    void testCanPassCrosswalkLogic() {
        assertTrue(taxi.canPass(Terrain.CROSSWALK, Light.GREEN));
        assertFalse(taxi.canPass(Terrain.CROSSWALK, Light.RED));
        assertTrue(taxi.canPass(Terrain.STREET, Light.YELLOW));
    }

    /**
     * Ensures image updates correctly when Taxi dies.
     */
    @Test
    void testImageChangeWhenDead() {
        taxi.collide(new Truck(0, 0, Direction.NORTH));
        assertEquals("taxi_dead.gif", taxi.getImageFileName());
    }

    /**
     * Confirms Taxi revives after its death cycle ends.
     */
    @Test
    void testDeathCycleAndRevival() {
        taxi.collide(new Truck(0, 0, Direction.NORTH));
        for (int i = 0; i < taxi.getDeathTime(); i++) taxi.poke();
        assertTrue(taxi.isAlive());
    }
}
