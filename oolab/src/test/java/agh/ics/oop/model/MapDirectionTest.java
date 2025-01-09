package agh.ics.oop.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    @DisplayName("Test toString method for all directions")
    void testToString() {
        assertEquals("N", MapDirection.NORTH.toString(), "NORTH should return 'Północ'");
        assertEquals("NE", MapDirection.NORTHEAST.toString(), "NORTHEAST should return 'Północny wschód'");
        assertEquals("E", MapDirection.EAST.toString(), "EAST should return 'Wschód'");
        assertEquals("SE", MapDirection.SOUTHEAST.toString(), "SOUTHEAST should return 'Południowy wschód'");
        assertEquals("S", MapDirection.SOUTH.toString(), "SOUTH should return 'Południe'");
        assertEquals("SW", MapDirection.SOUTHWEST.toString(), "SOUTHWEST should return 'Południowy zachód'");
        assertEquals("W", MapDirection.WEST.toString(), "WEST should return 'Zachód'");
        assertEquals("NW", MapDirection.NORTHWEST.toString(), "NORTHWEST should return 'Północny zachód'");
    }

    @Test
    @DisplayName("Test next method for all directions")
    void testNext() {
        assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.next(), "NEXT of NORTH should be NORTHEAST");
        assertEquals(MapDirection.EAST, MapDirection.NORTHEAST.next(), "NEXT of NORTHEAST should be EAST");
        assertEquals(MapDirection.SOUTHEAST, MapDirection.EAST.next(), "NEXT of EAST should be SOUTHEAST");
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHEAST.next(), "NEXT of SOUTHEAST should be SOUTH");
        assertEquals(MapDirection.SOUTHWEST, MapDirection.SOUTH.next(), "NEXT of SOUTH should be SOUTHWEST");
        assertEquals(MapDirection.WEST, MapDirection.SOUTHWEST.next(), "NEXT of SOUTHWEST should be WEST");
        assertEquals(MapDirection.NORTHWEST, MapDirection.WEST.next(), "NEXT of WEST should be NORTHWEST");
        assertEquals(MapDirection.NORTH, MapDirection.NORTHWEST.next(), "NEXT of NORTHWEST should be NORTH");
    }

    @Test
    @DisplayName("Test previous method for all directions")
    void testPrevious() {
        assertEquals(MapDirection.NORTHWEST, MapDirection.NORTH.previous(), "PREVIOUS of NORTH should be NORTHWEST");
        assertEquals(MapDirection.WEST, MapDirection.NORTHWEST.previous(), "PREVIOUS of NORTHWEST should be WEST");
        assertEquals(MapDirection.SOUTHWEST, MapDirection.WEST.previous(), "PREVIOUS of WEST should be SOUTHWEST");
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHWEST.previous(), "PREVIOUS of SOUTHWEST should be SOUTH");
        assertEquals(MapDirection.SOUTHEAST, MapDirection.SOUTH.previous(), "PREVIOUS of SOUTH should be SOUTHEAST");
        assertEquals(MapDirection.EAST, MapDirection.SOUTHEAST.previous(), "PREVIOUS of SOUTHEAST should be EAST");
        assertEquals(MapDirection.NORTHEAST, MapDirection.EAST.previous(), "PREVIOUS of EAST should be NORTHEAST");
        assertEquals(MapDirection.NORTH, MapDirection.NORTHEAST.previous(), "PREVIOUS of NORTHEAST should be NORTH");
    }

    @Test
    @DisplayName("Test toUnitVector method for all directions")
    void testToUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.NORTH.toUnitVector(), "NORTH unit vector should be (0,1)");
        assertEquals(new Vector2d(1, 1), MapDirection.NORTHEAST.toUnitVector(), "NORTHEAST unit vector should be (1,1)");
        assertEquals(new Vector2d(1, 0), MapDirection.EAST.toUnitVector(), "EAST unit vector should be (1,0)");
        assertEquals(new Vector2d(1, -1), MapDirection.SOUTHEAST.toUnitVector(), "SOUTHEAST unit vector should be (1,-1)");
        assertEquals(new Vector2d(0, -1), MapDirection.SOUTH.toUnitVector(), "SOUTH unit vector should be (0,-1)");
        assertEquals(new Vector2d(-1, -1), MapDirection.SOUTHWEST.toUnitVector(), "SOUTHWEST unit vector should be (-1,-1)");
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector(), "WEST unit vector should be (-1,0)");
        assertEquals(new Vector2d(-1, 1), MapDirection.NORTHWEST.toUnitVector(), "NORTHWEST unit vector should be (-1,1)");
    }

    @Test
    @DisplayName("Test opposite method for all directions")
    void testOpposite() {
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH.opposite(), "Opposite of NORTH should be SOUTH");
        assertEquals(MapDirection.SOUTHWEST, MapDirection.NORTHEAST.opposite(), "Opposite of NORTHEAST should be SOUTHWEST");
        assertEquals(MapDirection.WEST, MapDirection.EAST.opposite(), "Opposite of EAST should be WEST");
        assertEquals(MapDirection.NORTHWEST, MapDirection.SOUTHEAST.opposite(), "Opposite of SOUTHEAST should be NORTHWEST");
        assertEquals(MapDirection.NORTH, MapDirection.SOUTH.opposite(), "Opposite of SOUTH should be NORTH");
        assertEquals(MapDirection.NORTHEAST, MapDirection.SOUTHWEST.opposite(), "Opposite of SOUTHWEST should be NORTHEAST");
        assertEquals(MapDirection.EAST, MapDirection.WEST.opposite(), "Opposite of WEST should be EAST");
        assertEquals(MapDirection.SOUTHEAST, MapDirection.NORTHWEST.opposite(), "Opposite of NORTHWEST should be SOUTHEAST");
    }
}
