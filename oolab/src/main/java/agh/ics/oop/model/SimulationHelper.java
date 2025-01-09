package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimulationHelper {

    private final AbstractWorldMap map;
    private final WorldConfig config;
    private int epoch = 0;

    public SimulationHelper(AbstractWorldMap map, WorldConfig config) {
        this.map = map;
        this.config = config;
        map.wObserver = new WorldObserver(map);
    }

    public void runEpoch() {
        if (this.epoch > 10) {
            throw new RuntimeException("Epoch limit reached");
        }
        newEpoch();
        removeDeadAnimals();
        if (map.getClass().getName().equals("FireWorldMap")) {
            ((FireWorldMap) map).spreadFire(this.config.getFireMaxAge(), this.epoch % this.config.getFireFreq() == 0);
        }
        moveAnimals();
        eatGrass(map.animals, map.grasses);
        reproduceAnimals(map.animals);
        spawnGrass(config.getDailyGrassGrowth());
        gatherStats();
        map.notifyObservers("Epoch " + this.epoch + " ended");
    }

    private void newEpoch() {
        this.epoch++;
    }

    private void removeDeadAnimals() {
        (map.animals.values()).forEach(a -> {
            a.removeIf(animal -> animal.isDead());
        });
    }

    private void moveAnimals() {
        List<Animal> allAnimals = map.animals.values().stream().flatMap(List::stream).toList();
        allAnimals.forEach(Animal::move);
    }

    private void eatGrass(Map<Vector2d, ArrayList<Animal>> animals, Map<Vector2d, Grass> grasses) {
        List<Vector2d> matchingFields = animals.keySet().stream()
                .filter(grasses::containsKey)
                .toList();

        for (Vector2d vector : matchingFields) {
            ArrayList<Animal> animalList = animals.get(vector);
            if (animalList != null && !animalList.isEmpty()) {
                TieBreaker tb = new TieBreaker(animalList);
                Animal strongestAnimal = tb.breakTheTie().getFirst();
                strongestAnimal.eatGrass();
                grasses.remove(vector);
            }
        }
    }

    private void reproduceAnimals(Map<Vector2d, ArrayList<Animal>> animals) {
        List<Vector2d> matchingFields = animals.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .toList();

        List<ArrayList<Animal>> animalsToReproduce = matchingFields.stream()
                .map(animals::get)
                .filter(Objects::nonNull)
                .toList();

        // Now you can process the animals in animalsToEatGrass
        for (ArrayList<Animal> animalList : animalsToReproduce) {
            TieBreaker tb = new TieBreaker(animalList);
            List<Animal> strongestAnimals = tb.breakTheTie();
            if (strongestAnimals.size() > 1 && strongestAnimals.get(1).canReproduce()) {
                strongestAnimals.get(0).reproduce(strongestAnimals.get(1));
            }
        }

    }

    private void spawnGrass(int n) {
        map.spawnGrass(n);
    }

    private void gatherStats() {
        map.wObserver.update();
    }

    public List<Vector2d> generateStartingPositions(int animalsCount) {
        Vector2d lowerLeft = map.getCurrentBounds().lowerLeft();
        Vector2d upperRight = map.getCurrentBounds().upperRight();
        int width = upperRight.getX() - lowerLeft.getX();
        int height = upperRight.getY() - lowerLeft.getY();
        List<Vector2d> startingPositions = new ArrayList<>();

        for (int i = 0; i < animalsCount; i++) {
            Vector2d position = new Vector2d(
                    lowerLeft.getX() + (int) (Math.random() * width),
                    lowerLeft.getY() + (int) (Math.random() * height));

            map.animals.computeIfAbsent(position, _ -> new ArrayList<>());
            Animal animal = new Animal(position, config, this.map);
            map.animals.get(position).add(animal);
            startingPositions.add(position);
        }

        return startingPositions;
    }
}
