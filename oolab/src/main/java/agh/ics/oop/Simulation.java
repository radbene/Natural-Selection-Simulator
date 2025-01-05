package agh.ics.oop;

import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapBuilder;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldConfig;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.SimulationHelper;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable{
    private List<Vector2d> starting_positions;
    private AbstractWorldMap map;
    protected final SimulationHelper simulationHelper;
    private final WorldConfig config;
    private final MapBuilder mapBuilder = new MapBuilder();

    public List<Animal> getAnimals() {
        return animals;
    }
    private List<Animal> animals = new ArrayList<>();

    public Simulation(List<Vector2d> starting_positions, WorldConfig config) {
        this.starting_positions = starting_positions;
        this.simulationHelper = new SimulationHelper(this.map, this, config);
        this.config = config;
        init();
    }

    private void init(){
        this.map = this.mapBuilder.createMap(this.config);
        for(Vector2d position: starting_positions){
            Animal animal = new Animal(position);
            try {
                this.map.place(animal);
                this.animals.add(animal);
            } catch (IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while(true){
            this.simulationHelper.runEpoch();;
        }
    }
}
