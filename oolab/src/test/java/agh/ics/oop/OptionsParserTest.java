package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void InvalidCharacters(){
        String[] dir = {"q","!", ".", "-", "v",")"};
        MoveDirection[] expected_result = {};
        List<MoveDirection> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

    @Test
    void NoCharacters(){
        String[] dir = {};
        MoveDirection[] expected_result = {};
        List<MoveDirection> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

    @Test
    void ValidCharacters(){
        String[] dir = {"f", "l", "l", "l", "r","b"};
        MoveDirection[] expected_result = {MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD};
        List<MoveDirection> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

    @Test
    void SomeValidSomeInvalidCharacters(){
        String[] dir = {"-","f", "4","b","q"};
        MoveDirection[] expected_result = {MoveDirection.FORWARD, MoveDirection.BACKWARD};
        List<MoveDirection> actual_result = OptionsParser.parse(dir);
        //assertArrayEquals(result,OptionsParser.parse(dir));
        assertArrayEquals(expected_result, actual_result.toArray());
    }

}