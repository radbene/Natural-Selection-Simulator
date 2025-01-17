package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private Animal animal;
    private WorldConfig config;
    private AbstractWorldMap map;
    private final MapBuilder mapBuilder = new MapBuilder();

    @BeforeEach
    void setUp() {
        WorldConfig.Builder builder = new WorldConfig.Builder();
        config = builder.build();
        this.map = this.mapBuilder.createMap(this.config);
        animal = new Animal(new Vector2d(2, 2), config, map);
    }

    @Test
    void testInitialization() {
        assertNotNull(animal.getPosition(), "Position should not be null");
        assertNotNull(animal.getDirection(), "Direction should not be null");
        assertEquals(50, animal.getEnergy(), "Initial energy should match configuration");
        assertEquals(0, animal.getLifespan(), "Days lived should start at 0");
        assertEquals(0, animal.getChildren(), "Children count should start at 0");
    }

    @Test
    void testMove() {
        Vector2d initialPosition = animal.getPosition();
        MapDirection initialDirection = animal.getDirection();

        animal.move();

        assertNotEquals(initialPosition, animal.getPosition(), "Position should change after move");
        assertEquals(1, animal.getDaysLived(), "Days lived should increment by 1 after move");
    }

    @Test
    void testIsAt() {
        assertTrue(animal.isAt(new Vector2d(2, 2)), "Animal should be at the initial position");
        animal.move();
        assertFalse(animal.isAt(new Vector2d(0, 0)), "Animal should not be at the initial position after move");
    }

    @Test
    void testEatGrass() {
        animal.eatGrass();
        assertEquals(60, animal.getEnergy(), "Energy should increase by plant energy after eating grass");
    }

    @Test
    void testIsDead() {
        for (int i = 0; i < 100; i++) {
            animal.move();
        }
        assertTrue(animal.isDead(), "Animal should be dead after running out of energy");
    }

    @Test
    void testReproduce() {
        Animal partner = new Animal(new Vector2d(0, 0), MapDirection.NORTH, config, map);
        animal.eatGrass();
        partner.eatGrass();

        assertTrue(animal.canReproduce(), "Animal should be able to reproduce");
        assertTrue(partner.canReproduce(), "Partner should be able to reproduce");

        Animal child = animal.reproduce(partner);
        assertNotNull(child, "Child should not be null");
        assertEquals(animal.getPosition(), child.getPosition(), "Child should inherit parent's position");
        assertNotNull(child.getGenome(), "Child should have a genome");
    }
}
