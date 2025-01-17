package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FireWorldMap extends AbstractWorldMap {

    private Map<Vector2d, Fire> fires = new HashMap<>();

    public FireWorldMap(int width, int height, int n) {
        super(width, height);
        this.addObserver(new ConsoleMapDisplay());
        this.grassSpawner.spawnGrass(n);
    }

    public Optional<List<WorldElement>> objectAt(Vector2d position) {
        // Get objects at the given position using the superclass method
        Optional<List<WorldElement>> objects = super.objectAt(position);

        // Use Optional to handle the fires and return an empty list if no fire is present
        List<Fire> fireObjects = Optional.ofNullable(fires.get(position))
                .map(Collections::singletonList) // If fire exists, wrap it in a singleton list
                .orElse(Collections.emptyList()); // If no fire, return an empty list

        // Combine both lists into one and return an Optional containing the combined list
        return objects.map(objList ->
                Stream.concat(objList.stream(), fireObjects.stream())  // Concatenate the two streams
                        .collect(Collectors.toList())  // Collect the combined stream into a List
        );
    }




    public void spreadFire(int maxAge, boolean start) {
        List<Vector2d> neighbours = Arrays.asList(
            new Vector2d(1, 0),
            new Vector2d(-1, 0),
            new Vector2d(0, 1),
            new Vector2d(0, -1)
        );
        fires.forEach((position, fire) -> {
            if (fire.burn() >= maxAge) {
                fires.remove(position);
                notifyObservers("Fire at " + position + " has burned out");
            } else {
                for (Vector2d neighbour : neighbours) {
                    addFire(position.add(neighbour));
                }
            }
        });
        if(start){
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
        //TODO: add killed animals to deadAnimals

        animals.remove(position);
        animals.put(position,new ArrayList<>());
        notifyObservers("Fire added at " + position);
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
