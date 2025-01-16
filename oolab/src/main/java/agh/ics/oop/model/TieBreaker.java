package agh.ics.oop.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TieBreaker {

    private final List<Animal> animals;

    public TieBreaker(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> breakTheTie() {
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::getEnergy).reversed() // 1. Energy
                        .thenComparingInt(Animal::getChildren).reversed() // 2. Number of children
                        .thenComparingInt(Animal::getDaysLived).reversed() // 3. Age
                        .thenComparing(a -> Math.random())) // 4. Random (if all above are equal)
                .limit(2)
                .collect(Collectors.toList());
    }
}
