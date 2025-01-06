package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class WorldObserver {
    private final AbstractWorldMap map;
    private int epoch;
    private final Map<String, Object> stats;

    public WorldObserver(AbstractWorldMap map) {
        this.map = map;
        this.epoch = 0;
        this.stats = new HashMap<>();
    }

    public void update() {
        this.epoch++;
        collectStats();
    }

    private void collectStats() {
        stats.put("Epoch", epoch);
        stats.put("Total Animals", countAnimals());
        stats.put("Total Grass", countGrass());
        stats.put("Empty Squares", calculateEmptySquares());
        stats.put("Average Energy", calculateAverageEnergy());
        stats.put("Average Lifespan", calculateAverageLifespan());
        stats.put("Average Children", calculateAverageChildren());
        stats.put("Dominant Genome", findDominantGenome());
    }

    private int countAnimals() {
        return map.getAllAnimals().size();
    }

    private int calculateEmptySquares() {
        // TODO: implement
        return 0;
    }

    private int countGrass() {
        return map.getGrass().size();
    }

    private double calculateAverageEnergy() {
        return map.getAllAnimals().stream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0);
    }

    private double calculateAverageLifespan() {
        return map.getDeadAnimals().stream()
                .mapToInt(Animal::getLifespan)
                .average()
                .orElse(0);
    }

    private double calculateAverageChildren() {
        return map.getDeadAnimals().stream()
                .mapToInt(Animal::getChildren)
                .average()
                .orElse(0);
    }

    private Genome findDominantGenome() {
        Map<Genome, Integer> genomeCounts = new HashMap<>();
        map.getAllAnimals().stream()
                .map(Animal::getGenome)
                .forEach(genome -> genomeCounts.put(genome, genomeCounts.getOrDefault(genome, 0) + 1));

        return genomeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Map<String, Object> getStats() {
        return new HashMap<>(stats);
    }

    public void printStats() {
        System.out.println("Current Map Stats:");
        stats.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
