package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap extends Globe {

    protected Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected WorldObserver wObserver;
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final Equator equator;
    protected final GrassSpawner grassSpawner;
    protected List<Animal> deadAnimals = new ArrayList<>();
    protected final UUID uuid = UUID.randomUUID();

    public AbstractWorldMap(int width, int height) {
        super(new Vector2d(width, height));
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
        this.equator= new Equator(new Vector2d(0, (int)(this.upperRight.getY() * 0.4)), new Vector2d(this.upperRight.getX(), (int)(this.upperRight.getY() * 0.6)));
        this.grassSpawner = new GrassSpawner(this, this.equator, new RandomPositionGenerator());
    }

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
//        if (wObserver != null) {
//            wObserver.update();
//        }
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
        if (this.contains(animal.getPosition())) {
            ArrayList<Animal> animalsAtPosition = animals.get(animal.getPosition());
            animalsAtPosition.add(animal);
            animals.put(animal.getPosition(), animalsAtPosition);
//            notifyObservers("Animal placed at " + animal.getPosition());
            return true;
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        // animal.move(direction);
        ArrayList<Animal> animalsAtOldPosition = animals.get(oldPosition);
        animalsAtOldPosition.remove(animal);
        animals.put(oldPosition, animalsAtOldPosition);
        ArrayList<Animal> animalsAtNewPosition = animals.get(animal.getPosition());
        animalsAtNewPosition.add(animal);
        animals.put(animal.getPosition(), animalsAtNewPosition);
//        notifyObservers("Animal moved from " + oldPosition + " to " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).size() > 0;
    }

    @Override
    public ArrayList<WorldElement> objectAt(Vector2d position) {
        ArrayList<WorldElement> animalObjects = new ArrayList<>();
        ArrayList<WorldElement> grassObjects = new ArrayList<>();
        if (animals.get(position) != null)
            animalObjects.addAll(animals.get(position));
        if (grasses.get(position) != null)
            grassObjects.add(grasses.get(position));
        return List.of(animalObjects, grassObjects).stream().flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));

    }

    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>();
        animals.values().forEach(elements::addAll);
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return this.contains(position);
    }

    public void spawnGrass(int n) {
        grassSpawner.spawnGrass(n);
        // notifyObservers("Grass spawned");
    }

    public boolean hasGrass(Vector2d position) {
        return grasses.get(position) != null;
    }

    public void addGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
        // notifyObservers("Grass added at " + grass.getPosition());
    }

    public boolean contains(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    public int calculateFreeFieldsOutsideEquator(){
        int freeFields = 0;
        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
                if (grasses.get(new Vector2d(i, j)) == null && !this.equator.contains(new Vector2d(i, j))) {
                    freeFields++;
                }
            }
        }
        return freeFields;
    }

    public int calculateFreeFieldsInsideEquator(){
        int freeFields = 0;
        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
                if (grasses.get(new Vector2d(i, j)) == null && this.equator.contains(new Vector2d(i, j))) {
                    freeFields++;
                }
            }
        }
        return freeFields;
    }

    public int calculateEmptyFields(){
        int emptyFields = 0;
        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
                if (this.objectAt(new Vector2d(i, j)).size() == 0) {
                    emptyFields++;
                }
            }
        }
        return emptyFields;
    }
    
    @Override
    public String toString() {
        return visualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(this.lowerLeft, this.upperRight);
    }
}
