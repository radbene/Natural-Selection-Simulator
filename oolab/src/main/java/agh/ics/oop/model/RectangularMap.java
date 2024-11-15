package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap<Animal, Vector2d>{
    private Map<Vector2d, Animal> animals;


    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final MapVisualizer visualizer;


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

    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        MoveDirection oppositeDirection = direction.opposite();
        animal.move(direction);
        if(place(animal)){
            animals.remove(oldPosition);
        }
        else{
            if(!oldPosition.equals(animal.getPosition())) {
                animal.move(oppositeDirection);
            }
        }
    }

    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public Animal objectAt(Vector2d position){
        return animals.get(position);
    }

    public boolean canMoveTo(Vector2d position){
        return !isOccupied(position) && position.follows(lowerLeft) && position.precedes(upperRight);

    }

    @Override
    public String toString() {
        return this.visualizer.draw(lowerLeft, upperRight);
    }
}
