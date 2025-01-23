package agh.ics.oop;

import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapBuilder;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldConfig;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.SimulationHelper;
import agh.ics.oop.model.MapChangeListener;

import java.util.List;
import java.util.Map;

public class Simulation implements Runnable{
    private List<Vector2d> starting_positions;
    private AbstractWorldMap map;
    protected SimulationHelper simulationHelper;
    private final WorldConfig config;
    private final MapBuilder mapBuilder = new MapBuilder();

    public List<Animal> getAnimals() {
        return map.getAllAnimals();
    }

    public Simulation(WorldConfig config) {
        this.config = config;
        init();
    }

    public void addObserver(MapChangeListener observer) {
        this.map.addObserver(observer);
    }

    private void init(){
        this.map = this.mapBuilder.createMap(this.config);
        this.simulationHelper = new SimulationHelper(this.map, config);
        this.starting_positions = this.simulationHelper.generateStartingPositions(this.config.getInitialAnimalCount());
        for(Vector2d position: starting_positions){
            Animal animal = new Animal(position,this.config,map);
            try {
                this.map.place(animal);
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

    public void step(){
        this.simulationHelper.runEpoch();
    }

    public Map<String, Object> getStats(){
        return this.simulationHelper.getStats();
    }

    public final AbstractWorldMap getMap(){
        return this.map;
    }
}
