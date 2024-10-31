package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.text.Normalizer;

import static org.junit.jupiter.api.Assertions.*;
class MapDirectionTest {
    @Test
    void NextTest() {
        //given
        MapDirection dir1 = MapDirection.NORTH;
        MapDirection dir2 = MapDirection.EAST;
        MapDirection dir3 = MapDirection.SOUTH;
        MapDirection dir4 = MapDirection.WEST;

        //then
        assertEquals(MapDirection.EAST, dir1.next(dir1));
        assertEquals(MapDirection.SOUTH, dir2.next(dir2));
        assertEquals(MapDirection.WEST, dir3.next(dir3));
        assertEquals(MapDirection.NORTH, dir4.next(dir4));
    }


    @Test
    void PreviousTest() {
        //given
        MapDirection dir1 = MapDirection.NORTH;
        MapDirection dir2 = MapDirection.EAST;
        MapDirection dir3 = MapDirection.SOUTH;
        MapDirection dir4 = MapDirection.WEST;

        //then
        assertEquals(MapDirection.WEST, dir1.previous(dir1));
        assertEquals(MapDirection.NORTH, dir2.previous(dir2));
        assertEquals(MapDirection.EAST, dir3.previous(dir3));
        assertEquals(MapDirection.SOUTH, dir4.previous(dir4));
    }
  
}