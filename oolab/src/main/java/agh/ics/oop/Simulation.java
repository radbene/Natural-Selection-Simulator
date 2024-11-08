package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirections;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Vector2d> starting_positions;
    private List<MoveDirections> moves;

    public List<Animal> getAnimals() {
        return animals;
    }

    private List<Animal> animals = new ArrayList<>();

    public Simulation(List<Vector2d> starting_positions, List<MoveDirections> moves) {
        /*if (starting_positions.size() == 0 || moves.size() == 0) {
            throw new IllegalArgumentException("starting_positions and moves must not be empty");
        }*/
        this.starting_positions = starting_positions;
        this.moves = moves;
        int i = 0;
        for (Vector2d position : starting_positions) {
            Animal new_animal = new Animal(position);
            new_animal.setIndex(i);
            this.animals.add(new_animal);
            i++;
        }
    }

    public void run() {
        if (this.animals.isEmpty()){
            return;
        }
        int a = 0;
        int m = 0;
        while (m < this.moves.size()){
            this.animals.get(a % this.animals.size()).move(this.moves.get(m));
            System.out.println(this.animals.get(a % this.animals.size()).toString());
            a++;
            m++;
        }
    }

}
