package agh.ics.oop.model;

import agh.ics.oop.model.variants.EMapVariant;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

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
        if (this.epoch > 100) {
            throw new RuntimeException("Epoch limit reached");
        }
        newEpoch();
        removeDeadAnimals();
        if (config.getMapVariant()== EMapVariant.FIRE) {
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
        map.animals.values().forEach(animalList -> {
            animalList.removeIf(animal -> {
                if (animal.isDead()) {
                    map.deadAnimals.add(animal);

                    if (animal.getStats() != null) {
                        animal.getStats().die(epoch);
                    }
                    return true;
                }
                return false;
            });
        });
    }


    private void moveAnimals() {
        Map<Vector2d, ArrayList<Animal>> updatedMap = new HashMap<>();

        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : map.animals.entrySet()) {
            List<Animal> animalsAtCurrentPosition = entry.getValue();

            for (Animal animal : animalsAtCurrentPosition) {
                animal.move();
//                map.notifyObservers("Animal moved at " + animal.getPosition());
                Vector2d newPosition = animal.getPosition();
                updatedMap.computeIfAbsent(newPosition, k -> new ArrayList<>()).add(animal);
            }
        }
        map.animals = updatedMap;
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
        // Find fields with more than one animal
        List<Vector2d> matchingFields = animals.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .toList();

        // Get the lists of animals on the matching fields
        List<ArrayList<Animal>> animalsToReproduce = matchingFields.stream()
                .map(animals::get)
                .filter(Objects::nonNull)
                .toList();

        // Iterate through the lists of animals and reproduce if conditions are met
        for (ArrayList<Animal> animalList : animalsToReproduce) {
            TieBreaker tb = new TieBreaker(animalList);
            List<Animal> strongestAnimals = tb.breakTheTie();

            // Check if there are at least two animals and the second one can reproduce
            if (strongestAnimals.size() > 1 && strongestAnimals.get(1).canReproduce()) {
                // Reproduce and get the offspring
                Animal offspring = strongestAnimals.get(0).reproduce(strongestAnimals.get(1));

                // Add the offspring to the same position in the map
                Vector2d position = strongestAnimals.get(0).getPosition();
                animals.get(position).add(offspring);
            }
        }
    }

    private void spawnGrass(int n) {
        map.spawnGrass(n);
    }

    private void gatherStats() {
        map.wObserver.update();
    }

    public Map<String, Object> getStats() {
        return map.wObserver.getStats();
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
        System.out.println(map.animals);
        int map_size = config.getMapHeight() * config.getMapWidth();
        map.grassSpawner.spawnGrass(min(config.getInitialPlantCount(), map_size - animalsCount));
        return startingPositions;
    }
}
