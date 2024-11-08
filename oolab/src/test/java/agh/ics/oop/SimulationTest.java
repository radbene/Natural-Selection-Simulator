package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirections;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void GoingOutOfBoundsTest() {
        List<Vector2d> starting_positions = Arrays.asList(new Vector2d[]{new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,2), new Vector2d(2,2)});
        List<MoveDirections> moves = OptionsParser.parse(new String[]{"f", "r","b","l","f","f","b","f","f","f","b","f","f","f","b","f","f","f","b","f","f","f","b","f"});
        Simulation simulation = new Simulation(starting_positions, moves);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        assertFalse(animals.isEmpty());
        assertEquals(animals.size(), 4);
        assertTrue(animals.get(0).isAt(new Vector2d(2,4)));
        assertTrue(animals.get(1).isAt(new Vector2d(4,2)));
        assertTrue(animals.get(2).isAt(new Vector2d(2,0)));
        assertTrue(animals.get(3).isAt(new Vector2d(0,2)));

        assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
        assertEquals(animals.get(1).getDirection(), MapDirection.EAST);
        assertEquals(animals.get(2).getDirection(), MapDirection.NORTH);
        assertEquals(animals.get(3).getDirection(), MapDirection.WEST);
    }
    @Test
    void EmptyStartingPositionsTest() {
        List<Vector2d> starting_positions = new ArrayList<>();
        List<MoveDirections> moves = new ArrayList<>();
        moves.add(MoveDirections.FORWARD);
        moves.add(MoveDirections.RIGHT);
        moves.add(MoveDirections.FORWARD);
        Simulation simulation = new Simulation(starting_positions, moves);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        assertTrue(animals.isEmpty());
    }
    @Test
    void EmptyMovesTest() {
        List<Vector2d> starting_positions = new ArrayList<>();
        List<MoveDirections> moves = new ArrayList<>();
        starting_positions.add(new Vector2d(2, 2));
        starting_positions.add(new Vector2d(0, 0));
        Simulation simulation = new Simulation(starting_positions, moves);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        assertTrue(animals.get(0).isAt(new Vector2d(2, 2)));
        assertTrue(animals.get(1).isAt(new Vector2d(0, 0)));
        assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
        assertEquals(animals.get(1).getDirection(), MapDirection.NORTH);
    }

    @Test
    void RotatingRightTest() {
        List<Vector2d> starting_positions = new ArrayList<>();
        List<MoveDirections> moves = new ArrayList<>();
        starting_positions.add(new Vector2d(2, 2));
        moves.add(MoveDirections.RIGHT);
        Simulation simulation = new Simulation(starting_positions, moves);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.EAST);
        simulation.run();
        animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.SOUTH);
        simulation.run();
        animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.WEST);
        simulation.run();
        animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
    }

    @Test
    void RotatingLeftTest() {
        List<Vector2d> starting_positions = new ArrayList<>();
        List<MoveDirections> moves = new ArrayList<>();
        starting_positions.add(new Vector2d(2, 2));
        moves.add(MoveDirections.LEFT);
        Simulation simulation = new Simulation(starting_positions, moves);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.WEST);
        simulation.run();
        animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.SOUTH);
        simulation.run();
        animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.EAST);
        simulation.run();
        animals = simulation.getAnimals();
        assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
    }
}