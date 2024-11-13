package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Vector2d> starting_positions;
    private List<MoveDirection> moves;
    private WorldMap map;

    public List<Animal> getAnimals() {
        return animals;
    }

    private List<Animal> animals = new ArrayList<>();

    public Simulation(List<Vector2d> starting_positions, List<MoveDirection> moves, WorldMap world) {
        /*if (starting_positions.size() == 0 || moves.size() == 0) {
            throw new IllegalArgumentException("starting_positions and moves must not be empty");
        }*/
        this.map = world;
        this.starting_positions = starting_positions;
        this.moves = moves;
        int i = 0;
        for (Vector2d position : starting_positions) {
            Animal new_animal = new Animal(position);
            new_animal.setIndex(i);
            if (map.place(new_animal)){
                this.animals.add(new_animal);
            }
            i++;
        }
    }

    public void run() {
        System.out.println(map);
        if (this.animals.isEmpty()){
            return;
        }
        int a = 0;
        int m = 0;
        while (m < this.moves.size()){
            map.move(this.animals.get(a % this.animals.size()), this.moves.get(m));
            System.out.println(map);
            a++;
            m++;
        }
    }

}
