package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EnumMap;
import java.util.Map;
import model.*;

/**
 * Unit tests for the Atv class.
 * Verifies terrain passing, direction choice, and death cycle.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class AtvTest {
    private Atv atv;
    private Map<Direction, Terrain> neighbors;

    /**
     * Initializes an Atv and neighbor terrains before each test.
     */
    @BeforeEach
    void setUp() {
        atv = new Atv(0, 0, Direction.NORTH);
        neighbors = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) neighbors.put(dir, Terrain.STREET);
    }

    /**
     * Tests allowed terrains excluding walls.
     */
    @Test
    void testCanPassAllButWall() {
        assertTrue(atv.canPass(Terrain.GRASS, Light.GREEN));
        assertTrue(atv.canPass(Terrain.TRAIL, Light.RED));
        assertFalse(atv.canPass(Terrain.WALL, Light.GREEN));
    }

    /**
     * Ensures the Atv chooses a valid direction each time.
     */
    @Test
    void testChoosesAnyValidDirection() {
        Direction result = atv.chooseDirection(neighbors);
        assertNotNull(result);
        assertTrue(neighbors.containsKey(result));
    }

    /**
     * Verifies Atv death and revival after collision.
     */
    @Test
    void testDeathCycle() {
        atv.collide(new Truck(0, 0, Direction.NORTH));
        assertFalse(atv.isAlive());
        for (int i = 0; i < atv.getDeathTime(); i++) atv.poke();
        assertTrue(atv.isAlive());
    }
}
