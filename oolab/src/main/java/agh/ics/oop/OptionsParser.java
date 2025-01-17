package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser  {
    public static List<MoveDirection> parse(String[] args) {
        return Stream.of(args) // Tworzymy strumień z tablicy
                .map(arg -> { // Mapujemy każdą wartość wejściową na MoveDirection
                    switch (arg) {
                        case "f": return MoveDirection.FORWARD;
                        case "b": return MoveDirection.BACKWARD;
                        case "l": return MoveDirection.LEFT;
                        case "r": return MoveDirection.RIGHT;
                        default: throw new IllegalArgumentException(arg + " is not a legal move specification");
                    }
                })
                .collect(Collectors.toList()); // Zbieramy wyniki do listy
    }

}
