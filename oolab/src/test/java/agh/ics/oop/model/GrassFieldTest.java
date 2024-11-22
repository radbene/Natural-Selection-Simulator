package agh.ics.oop.model;
import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class GrassFieldTest {
    @Test
    void testCanMoveToEmptyPosition() {
        Vector2d position = new Vector2d(5, 5);
        GrassField grassField = new GrassField(10); // Create a grass field with 10 grass patches

        assertTrue(grassField.canMoveTo(position));
    }

    @Test
    void testCannotMoveToOccupiedPositionByAnimal() {
        Animal animal = new Animal(new Vector2d(5, 5));
        GrassField grassField = new GrassField(10);

        grassField.place(animal);

        assertFalse(grassField.canMoveTo(new Vector2d(5, 5)));
    }

    @Test
    void testCannotMoveToOccupiedPositionByGrass() {
        GrassField grassField = new GrassField(1);

        assertTrue(grassField.canMoveTo(new Vector2d(0, 0)));
        assertTrue(grassField.canMoveTo(new Vector2d(1, 1)));
        assertTrue(grassField.canMoveTo(new Vector2d(0, 1)));
        assertTrue(grassField.canMoveTo(new Vector2d(1, 0)));

    }

    @Test
    void testPlaceAnimal() {
        Animal animal = new Animal(new Vector2d(2, 3));
        GrassField grassField = new GrassField(10); // Create a grass field with 10 grass patches

        assertTrue(grassField.place(animal), "Animal should be placed successfully.");

        assertTrue(grassField.isOccupied(new Vector2d(2, 3)), "Position should be marked as occupied.");
        assertEquals(animal, grassField.objectAt(new Vector2d(2, 3)), "The placed animal should be retrievable.");
    }

    @Test
    void testMoveAnimal() {
        GrassField grassField = new GrassField(10); // Create a grass field with 10 grass patches

        Animal animal = new Animal(new Vector2d(2, 2));
        grassField.place(animal);

        grassField.move(animal, MoveDirection.FORWARD);
        assertTrue(animal.getPosition().equals(new Vector2d(2, 3)), "Animal should have moved forward.");
    }

    @Test
    void testMoveIntoOccupiedPosition() {
        GrassField grassField = new GrassField(10); // Create a grass field with 10 grass patches

        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 3));
        grassField.place(animal1);
        grassField.place(animal2);

        grassField.move(animal1, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 2), animal1.getPosition(), "Animal should not move into an occupied position.");
    }

    @Test
    void testMapBoundaries() {
        GrassField map = new GrassField(1); // Create a grass field with 10 grass patches
        Animal animal1 = new Animal(new Vector2d(Integer.MAX_VALUE,Integer.MAX_VALUE));
        Animal animal2 = new Animal(new Vector2d(Integer.MIN_VALUE,Integer.MIN_VALUE));
        map.place(animal1);
        map.place(animal2);
        map.getUpperright().equals(new Vector2d(Integer.MAX_VALUE,Integer.MAX_VALUE));
        map.getLowerleft().equals(new Vector2d(Integer.MIN_VALUE,Integer.MIN_VALUE));
    }
}