package agh.ics.oop.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TieBreaker {

    private final List<Animal> animals;

    public TieBreaker(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> breakTheTie() {
        // Precompute a random value for each animal
        HashMap<Animal, Double> randomValues = new HashMap<>();
        Random random = new Random(); // Use a single Random instance for consistency

        for (Animal animal : animals) {
            randomValues.put(animal, random.nextDouble());
        }

        return animals.stream()
                .sorted(Comparator
                        .comparingInt(Animal::getEnergy).reversed() // 1. Sort by energy (descending)
                        .thenComparingInt(Animal::getChildren).reversed() // 2. Sort by number of children (descending)
                        .thenComparingInt(Animal::getDaysLived).reversed() // 3. Sort by age (descending)
                        .thenComparingDouble(randomValues::get) // 4. Stable random tiebreaker
                )
                .limit(2)
                .collect(Collectors.toList());
    }
}
