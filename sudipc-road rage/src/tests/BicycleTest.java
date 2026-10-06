package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EnumMap;
import java.util.Map;
import model.*;

/**
 * Unit tests for the Bicycle class.
 * Validates movement and direction rules for trails and crosswalks.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class BicycleTest {
    private Bicycle bike;
    private Map<Direction, Terrain> neighbors;

    /**
     * Sets up a Bicycle and default trail terrain before each test.
     */
    @BeforeEach
    void setUp() {
        bike = new Bicycle(0, 0, Direction.NORTH);
        neighbors = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) neighbors.put(dir, Terrain.TRAIL);
    }

    /**
     * Tests pass conditions for valid and invalid terrains.
     */
    @Test
    void testCanPassOnlyTrailOrCrosswalk() {
        assertTrue(bike.canPass(Terrain.TRAIL, Light.RED));
        assertTrue(bike.canPass(Terrain.CROSSWALK, Light.GREEN));
        assertFalse(bike.canPass(Terrain.STREET, Light.GREEN));
    }

    /**
     * Ensures the Bicycle avoids reversing direction.
     */
    @Test
    void testChooseDirectionAvoidsReverse() {
        bike.setDirection(Direction.NORTH);
        neighbors.put(Direction.SOUTH, Terrain.GRASS);
        Direction dir = bike.chooseDirection(neighbors);
        assertNotEquals(Direction.SOUTH, dir);
    }
}

