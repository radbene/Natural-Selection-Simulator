package agh.ics.oop.model;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final List<MapChangeListener> observers = new ArrayList<>();

    protected final UUID uuid = UUID.randomUUID();




    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public boolean place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            notifyObservers("Animal placed at " + animal.getPosition());
            return true;
        }else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }


    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        Vector2d next_pos = new Vector2d(0,0);
        animal.move(direction,this);
        animals.remove(oldPosition);
        animals.put(animal.getPosition(), animal);
        notifyObservers("Animal moved from " + oldPosition + " to " + animal.getPosition());
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        if(animals.get(position) != null) return animals.get(position);
        return null;
    }



    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>(animals.values());
        return elements;
    }

    @Override
    abstract public boolean canMoveTo(Vector2d position);

    @Override
    abstract public Boundary getCurrentBounds();

    @Override
    public String toString() {
        System.out.println(getCurrentBounds().lowerLeft());
        return visualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }
}
