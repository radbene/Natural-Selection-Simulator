package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void InvalidCharacters(){
        String[] dir = {"q","!", ".", "-", "v",")"};
        MoveDirections[] result = {};
        assertArrayEquals(result,OptionsParser.parse(dir));
    }

    @Test
    void NoCharacters(){
        String[] dir = {};
        MoveDirections[] result = {};
        assertArrayEquals(result,OptionsParser.parse(dir));
    }

    @Test
    void ValidCharacters(){
        String[] dir = {"f", "l", "l", "l", "r","b"};
        MoveDirections[] result = {MoveDirections.FORWARD,MoveDirections.LEFT,MoveDirections.LEFT,MoveDirections.LEFT,MoveDirections.RIGHT,MoveDirections.BACKWARD};
        assertArrayEquals(result,OptionsParser.parse(dir));
    }

    @Test
    void SomeValidSomeInvalidCharacters(){
        String[] dir = {"-","f", "4","b","q"};
        MoveDirections[] result = {MoveDirections.FORWARD,MoveDirections.BACKWARD};
        assertArrayEquals(result,OptionsParser.parse(dir));
    }

}