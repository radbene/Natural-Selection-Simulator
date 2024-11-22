package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap{
    private Map<Vector2d, Animal> animals;


    private  Vector2d lowerLeft;
    private  Vector2d upperRight;
    private  MapVisualizer visualizer;


    public RectangularMap(int width, int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width -1, height -1);
        this.animals = new HashMap<>();
        Animal animal = new Animal();
        animal.setBorder(this.lowerLeft,this.upperRight);
        animal = null;
        this.visualizer = new MapVisualizer(this);
    }

    public boolean place(Animal animal){
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.follows(lowerLeft)  && position.precedes(upperRight);
    }

    @Override
    public String toString() {
        return this.visualizer.draw(lowerLeft, upperRight);
    }
}
