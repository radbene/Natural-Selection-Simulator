package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FireWorldMap extends AbstractWorldMap {

    private Map<Vector2d, Fire> fires = new HashMap<>();

    public FireWorldMap(int width, int height, int n) {
        super(width, height);
        this.addObserver(new ConsoleMapDisplay());
        this.addObserver(new FileMapDisplay(this.uuid));
    }

    public ArrayList<WorldElement> objectAt(Vector2d position) {
        List<WorldElement> objects = super.objectAt(position);
        List<WorldElement> fireObjects = new ArrayList<>();
        if(fires.get(position) != null) {
            fireObjects.add(fires.get(position));
        }
        return Stream.concat(fireObjects.stream(),objects.stream()).collect(Collectors.toCollection(ArrayList::new));
    }

    public void spreadFire(int maxAge, boolean start) {
        List<Vector2d> neighbours = Arrays.asList(
                new Vector2d(1, 0),
                new Vector2d(-1, 0),
                new Vector2d(0, 1),
                new Vector2d(0, -1)
        );
        List<Vector2d> positionsToRemove = new ArrayList<>();
        List<Vector2d> positionsToAdd = new ArrayList<>();

        fires.forEach((position, fire) -> {
            if (fire.burn() > maxAge) {
                positionsToRemove.add(position);
            } else {
                for (Vector2d neighbour : neighbours) {
                    positionsToAdd.add(position.add(neighbour));
//                notifyObservers("Fire at " + position + " has burned out");
                }
            }
        });

        positionsToRemove.forEach(fires::remove);
        positionsToAdd.forEach(this::addFire);
        if (start) {
            startFire();
        }
    }

    public void addFire(Vector2d position) {
        if (!contains(position)) {
            return;
        }
        if (fires.get(position) != null || grasses.get(position) == null) {
            return;
        }
        Fire fire = new Fire(position);
        fires.put(position, fire);
        grasses.remove(position);
        if(animals.get(position) != null && !animals.get(position).isEmpty()){
            this.deadAnimals.addAll(animals.get(position));
            animals.remove(position);
            animals.put(position,new ArrayList<>());
        }
//        notifyObservers("Fire added at " + position);
    }

    public void startFire(){
        ArrayList<Vector2d> grassPositions = new ArrayList<>(grasses.keySet());
        if(grassPositions.isEmpty()){
            return;
        }
        Vector2d randomPosition = grassPositions.get(new Random().nextInt(grassPositions.size()));
        addFire(randomPosition);
    }

    @Override
    public int calculateFreeFieldsOutsideEquator() {
        int freeFields = super.calculateFreeFieldsOutsideEquator();
        freeFields -= (int)fires.keySet().stream().filter(position -> !this.equator.contains(position)).count();
        return freeFields;
    }

    @Override
    public int calculateFreeFieldsInsideEquator() {
        int freeFields = super.calculateFreeFieldsInsideEquator();
        freeFields -= (int)fires.keySet().stream().filter(this.equator::contains).count();
        return freeFields;
    }
}
