// package agh.ics.oop;

// import agh.ics.oop.model.*;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestTemplate;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// class SimulationTest {


//     @Test
//     void SameStartingPosition() {
//         List<Vector2d> starting_positions = Arrays.asList(new Vector2d[]{new Vector2d(0,0), new Vector2d(0,0), new Vector2d(0,0), new Vector2d(0,0)});
//         List<MoveDirection> moves = OptionsParser.parse(new String[]{"b","r","l","f","b","f","f","f"});
//         WorldMap map = new RectangularMap(6,6);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertEquals(1, animals.size());
//         assertTrue(animals.get(0).isAt(new Vector2d(0,3)));
//         assertEquals(animals.get(0).getDirection(),MapDirection.NORTH);

//     }

//     @Test
//     void GoingOutOfBoundsTest() {
//         List<Vector2d> starting_positions = Arrays.asList(new Vector2d[]{new Vector2d(0,0), new Vector2d(5,0), new Vector2d(0,5), new Vector2d(5,5)});
//         List<MoveDirection> moves = OptionsParser.parse(new String[]{"b","r","l","f","b","f","f","f"});
//         WorldMap map = new RectangularMap(6,6);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertFalse(animals.isEmpty());
//         assertEquals(animals.size(), 4);
//         assertTrue(animals.get(0).isAt(new Vector2d(0,0)));
//         assertTrue(animals.get(1).isAt(new Vector2d(5,0)));
//         assertTrue(animals.get(2).isAt(new Vector2d(0,5)));
//         assertTrue(animals.get(3).isAt(new Vector2d(5,5)));

//         assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
//         assertEquals(animals.get(1).getDirection(), MapDirection.EAST);
//         assertEquals(animals.get(2).getDirection(), MapDirection.WEST);
//         assertEquals(animals.get(3).getDirection(), MapDirection.NORTH);
//     }

//     @Test
//     void StartingPositionsOutOfBoundsTest() {
//         List<Vector2d> starting_positions = Arrays.asList(new Vector2d[]{new Vector2d(3,4), new Vector2d(5,0), new Vector2d(0,5), new Vector2d(5,5)});
//         List<MoveDirection> moves = OptionsParser.parse(new String[]{"b","r","f","f","b","f","f","f"});
//         WorldMap map = new RectangularMap(2,2);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertTrue(animals.isEmpty());
//     }
//     @Test
//     void EmptyStartingPositionsTest() {
//         List<Vector2d> starting_positions = new ArrayList<>();
//         List<MoveDirection> moves = new ArrayList<>();
//         moves.add(MoveDirection.FORWARD);
//         moves.add(MoveDirection.RIGHT);
//         moves.add(MoveDirection.FORWARD);
//         WorldMap map = new RectangularMap(5,5);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertTrue(animals.isEmpty());
//     }
//     @Test
//     void EmptyMovesTest() {
//         List<Vector2d> starting_positions = new ArrayList<>();
//         List<MoveDirection> moves = new ArrayList<>();
//         starting_positions.add(new Vector2d(2, 2));
//         starting_positions.add(new Vector2d(0, 0));
//         WorldMap map = new RectangularMap(5,5);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertTrue(animals.get(0).isAt(new Vector2d(2, 2)));
//         assertTrue(animals.get(1).isAt(new Vector2d(0, 0)));
//         assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
//         assertEquals(animals.get(1).getDirection(), MapDirection.NORTH);
//     }

//     @Test
//     void RotatingRightTest() {
//         List<Vector2d> starting_positions = new ArrayList<>();
//         List<MoveDirection> moves = new ArrayList<>();
//         starting_positions.add(new Vector2d(2, 2));
//         moves.add(MoveDirection.RIGHT);
//         WorldMap map = new RectangularMap(5,5);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.EAST);
//         simulation.run();
//         animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.SOUTH);
//         simulation.run();
//         animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.WEST);
//         simulation.run();
//         animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
//     }

//     @Test
//     void RotatingLeftTest() {
//         List<Vector2d> starting_positions = new ArrayList<>();
//         List<MoveDirection> moves = new ArrayList<>();
//         starting_positions.add(new Vector2d(2, 2));
//         moves.add(MoveDirection.LEFT);
//         WorldMap map = new RectangularMap(5,5);
//         Simulation simulation = new Simulation(starting_positions, moves, map);
//         simulation.run();
//         List<Animal> animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.WEST);
//         simulation.run();
//         animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.SOUTH);
//         simulation.run();
//         animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.EAST);
//         simulation.run();
//         animals = simulation.getAnimals();
//         assertEquals(animals.get(0).getDirection(), MapDirection.NORTH);
//     }
// }