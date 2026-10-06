package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EnumMap;
import java.util.Map;
import model.*;

/**
 * Unit tests for the Car class.
 * Checks terrain logic, death handling, and reset behavior.
 *
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class CarTest {
    private Car car;
    private Map<Direction, Terrain> neighbors;

    /**
     * Prepares a Car and neighbor map before each test.
     */
    @BeforeEach
    void setUp() {
        car = new Car(0, 0, Direction.NORTH);
        neighbors = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) neighbors.put(dir, Terrain.STREET);
    }

    /**
     * Tests passing rules for different terrains and lights.
     */
    @Test
    void testCanPass() {
        assertTrue(car.canPass(Terrain.STREET, Light.GREEN));
        assertTrue(car.canPass(Terrain.LIGHT, Light.RED));
        assertTrue(car.canPass(Terrain.CROSSWALK, Light.GREEN));
        assertFalse(car.canPass(Terrain.GRASS, Light.RED));
    }

    /**
     * Tests death cycle and revival after a collision.
     */
    @Test
    void testDeathAndRevival() {
        car.collide(new Truck(0, 0, Direction.NORTH));
        assertFalse(car.isAlive());
        for (int i = 0; i < car.getDeathTime(); i++) car.poke();
        assertTrue(car.isAlive());
    }

    /**
     * Ensures reset restores original position.
     */
    @Test
    void testResetRestoresPosition() {
        car.setX(5);
        car.setY(5);
        car.reset();
        assertEquals(0, car.getX());
        assertEquals(0, car.getY());
    }
}
