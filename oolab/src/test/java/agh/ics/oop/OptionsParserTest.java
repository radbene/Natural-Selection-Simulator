package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirections;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void InvalidCharacters(){
        String[] dir = {"q","!", ".", "-", "v",")"};
        MoveDirections[] expected_result = {};
        List<MoveDirections> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

    @Test
    void NoCharacters(){
        String[] dir = {};
        MoveDirections[] expected_result = {};
        List<MoveDirections> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

    @Test
    void ValidCharacters(){
        String[] dir = {"f", "l", "l", "l", "r","b"};
        MoveDirections[] expected_result = {MoveDirections.FORWARD,MoveDirections.LEFT,MoveDirections.LEFT,MoveDirections.LEFT,MoveDirections.RIGHT,MoveDirections.BACKWARD};
        List<MoveDirections> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

    @Test
    void SomeValidSomeInvalidCharacters(){
        String[] dir = {"-","f", "4","b","q"};
        MoveDirections[] expected_result = {MoveDirections.FORWARD,MoveDirections.BACKWARD};
        List<MoveDirections> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

}