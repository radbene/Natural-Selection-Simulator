package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTests {

    private RectangularMap rectangularMap;

    @BeforeEach
    void setUp() {
        rectangularMap = new RectangularMap(5, 5); // A 5x5 map
    }

    @Test
    void testPlaceAnimal() {
        Animal animal = new Animal(new Vector2d(2, 2));
        assertTrue(rectangularMap.place(animal));
        assertTrue(rectangularMap.isOccupied(new Vector2d(2, 2)));
        assertEquals(animal, rectangularMap.objectAt(new Vector2d(2, 2)));
    }

    @Test
    void testPlaceAnimalOnOccupiedPosition() {
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 2));

        rectangularMap.place(animal1);
        assertFalse(rectangularMap.place(animal2));
    }

    @Test
    void testPlaceAnimalOutOfBounds() {
        Animal animal = new Animal(new Vector2d(5, 5));
        assertFalse(rectangularMap.place(animal));
    }

    @Test
    void testCanMoveToEmptyPosition() {
        Vector2d position = new Vector2d(3, 3);
        assertTrue(rectangularMap.canMoveTo(position));
    }

    @Test
    void testCannotMoveToOccupiedPosition() {
        Animal animal = new Animal(new Vector2d(3, 3));
        rectangularMap.place(animal);

        assertFalse(rectangularMap.canMoveTo(new Vector2d(3, 3)), "Animal should not be able to move to an occupied position.");
    }

    @Test
    void testCannotMoveOutOfBounds() {
        Vector2d outOfBoundsPosition = new Vector2d(5, 0); // Out of bounds
        assertFalse(rectangularMap.canMoveTo(outOfBoundsPosition), "Animal should not be able to move outside the map boundaries.");
    }

    @Test
    void testMoveAnimalWithinBounds() {
        Animal animal = new Animal(new Vector2d(1, 1));
        rectangularMap.place(animal);

        rectangularMap.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(1, 2), animal.getPosition(), "Animal should move forward within the bounds.");
        assertTrue(rectangularMap.isOccupied(new Vector2d(1, 2)), "New position should be marked as occupied.");
        assertFalse(rectangularMap.isOccupied(new Vector2d(1, 1)), "Old position should no longer be occupied.");
    }

    @Test
    void testMoveAnimalOutOfBounds() {
        Animal animal = new Animal(new Vector2d(0, 0));
        rectangularMap.place(animal);

        rectangularMap.move(animal, MoveDirection.BACKWARD);
        assertEquals(new Vector2d(0, 0), animal.getPosition(), "Animal should not move out of bounds.");
    }

    @Test
    void testMoveIntoOccupiedPosition() {
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 3));
        rectangularMap.place(animal1);
        rectangularMap.place(animal2);

        rectangularMap.move(animal1, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 2), animal1.getPosition(), "Animal should not move into an occupied position.");
    }

    @Test
    void testToString() {
        Animal animal1 = new Animal(new Vector2d(0, 0));
        Animal animal2 = new Animal(new Vector2d(4, 4));
        rectangularMap.place(animal1);
        rectangularMap.place(animal2);

        String mapString = rectangularMap.toString();
        assertNotNull(mapString);
        assertFalse(mapString.isEmpty());
        assertTrue(mapString.contains("N"));
    }
}
