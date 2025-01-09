package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class TieBreaker {

    private final List<Animal> animals;

    public TieBreaker(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> breakTheTie() {
        //TODO: implement tie logic
        //priorities: 1.energy, 2.number of children, 3.age, 4.random
        return animals;
    }
}
