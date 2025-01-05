package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agh.ics.oop.Simulation;

public class SimulationHelper {

    private final AbstractWorldMap map;
    private final Simulation simulation;
    private final WorldConfig config;
    private int epoch = 0;
    
    public SimulationHelper(AbstractWorldMap map, Simulation simulation, WorldConfig config){
        this.map = map;
        // TODO: remove simulation from this class, implement world observer as standard observer
        this.simulation = simulation;
        this.config = config;
        map.wObserver = new WorldObserver(map, simulation);
    }

    public void runEpoch(){
        newEpoch();
        removeDeadAnimals();
        if(map.getClass().getName().equals("FireWorldMap")){
            ((FireWorldMap) map).spreadFire();
        }
        moveAnimals();
        eatGrass(map.animals);
        reproduceAnimals(map.animals);
        spawnGrass(config.getDailyGrassGrowth());
        gatherStats();
        map.notifyObservers("Epoch " + this.epoch + " ended");
    }

    private void newEpoch(){
        this.epoch++;
    }

    private void removeDeadAnimals(){
        (map.animals.values()).forEach(a -> {
            a.removeIf(animal -> animal.isDead());
        });
    }

    private void moveAnimals(){
        List<Animal> allAnimals = map.animals.values().stream().flatMap(List::stream).toList();
        allAnimals.forEach(Animal::move); 
    }

    private void eatGrass(Map<Vector2d, ArrayList<Animal>> animals){
        // TODO: Implement eating grass logic
    }

    private void reproduceAnimals(Map<Vector2d, ArrayList<Animal>> animals){
        // TODO: Implement reproducing animals logic
    }

    private void spawnGrass(int n){
        map.spawnGrass(n);
    }

    private void gatherStats(){
        map.wObserver.update();
    }

    // FIXME: This is a placeholder implementing old logic
    public List<Vector2d> generateStartingPositions(int animalsCount){
        Vector2d lowerLeft = map.getCurrentBounds().lowerLeft();
        Vector2d upperRight = map.getCurrentBounds().upperRight();
        int width = upperRight.getX() - lowerLeft.getX();
        int height = upperRight.getY() - lowerLeft.getY();
        List<Vector2d> startingPositions = new java.util.ArrayList<>();
        for (int i = 0; i < animalsCount; i++) {
            Vector2d position = new Vector2d((int)(Math.random() * width), (int)(Math.random() * height));
            if(!map.isOccupied(position)){
                ArrayList<Animal> animalsAtPosition = new ArrayList<>();
                animalsAtPosition.add(new Animal(position));
                map.animals.put(position, animalsAtPosition);
                startingPositions.add(position);
            }
        }
        return startingPositions;
    }
}
