package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    protected Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected Vector2d lowerLeft = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    protected Vector2d upperRight = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected WorldObserver wObserver;
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final GrassSpawner grassSpawner = new GrassSpawner(this, this.equator, new RandomPositionGenerator());
    protected final Equator equator = new Equator(new Vector2d(0, (int)(this.upperRight.getY() * 0.4)), new Vector2d(this.upperRight.getX(), (int)(this.upperRight.getY() * 0.6)));
    protected List<Animal> deadAnimals = new ArrayList<>();
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
        if (wObserver != null) {
            wObserver.update();
        }
    }

    public Vector2d getLowerleft() {
        return lowerLeft;
    }

    public Vector2d getUpperright() {
        return upperRight;
    }

    public List<Animal> getAllAnimals() {
        return this.animals.values().stream().flatMap(List::stream).toList();
    }

    public List<Grass> getGrass() {
        return new ArrayList<>(this.grasses.values());
    }

    public void addDeadAnimal(Animal animal) {
        this.deadAnimals.add(animal);
    }

    public List<Animal> getDeadAnimals() {
        return this.deadAnimals;
    }

    @Override
    public boolean place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            ArrayList<Animal> animalsAtPosition = animals.get(animal.getPosition());
            animalsAtPosition.add(animal);
            animals.put(animal.getPosition(), animalsAtPosition);
            notifyObservers("Animal placed at " + animal.getPosition());
            return true;
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(direction);
        ArrayList<Animal> animalsAtOldPosition = animals.get(oldPosition);
        animalsAtOldPosition.remove(animal);
        animals.put(oldPosition, animalsAtOldPosition);
        ArrayList<Animal> animalsAtNewPosition = animals.get(animal.getPosition());
        animalsAtNewPosition.add(animal);
        animals.put(animal.getPosition(), animalsAtNewPosition);
        notifyObservers("Animal moved from " + oldPosition + " to " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) != null)
        // FIXME: Reowrk this logic
            return animals.get(position).get(0);
        return null;
    }

    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>();
        animals.values().forEach(elements::addAll);
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    abstract public boolean canMoveTo(Vector2d position);

    public void spawnGrass(int n) {
        grassSpawner.spawnGrass(n);
        notifyObservers("Grass spawned");
    }

    protected void minMax(Map<Vector2d, ArrayList<Animal>> animals, Map<Vector2d, Grass> grasses) {
        int xmin = Integer.MAX_VALUE;
        int xmax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE;
        int ymax = Integer.MIN_VALUE;
        for (Vector2d v : animals.keySet()) {
            xmin = Math.min(v.getX(), xmin);
            xmax = Math.max(v.getX(), xmax);
            ymin = Math.min(v.getY(), ymin);
            ymax = Math.max(v.getY(), ymax);
        }

        for (Vector2d v : grasses.keySet()) {
            xmin = Math.min(v.getX(), xmin);
            xmax = Math.max(v.getX(), xmax);
            ymin = Math.min(v.getY(), ymin);
            ymax = Math.max(v.getY(), ymax);
        }
        this.lowerLeft = new Vector2d(xmin, ymin);
        this.upperRight = new Vector2d(xmax, ymax);
    }

    public void addGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
        notifyObservers("Grass added at " + grass.getPosition());
    }

    public boolean contains(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    @Override
    public String toString() {
        return visualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }

    public Boundary getCurrentBounds() {
        return new Boundary(this.lowerLeft, this.upperRight);
    }
}
