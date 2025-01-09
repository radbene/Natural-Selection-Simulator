package agh.ics.oop.model;

import java.util.*;

public class FireWorldMap extends AbstractWorldMap {

    private Map<Vector2d, Fire> fires = new HashMap<>();

    public FireWorldMap(int width, int height, int n) {
        super(width, height);
        this.addObserver(new ConsoleMapDisplay());
        this.grassSpawner.spawnGrass(n);
    }

    public ArrayList<WorldElement> objectAt(Vector2d position) {
        List<WorldElement> objects = super.objectAt(position);
        List<WorldElement> fireObjects = new ArrayList<>();
        if(fires.get(position) != null) {
            fireObjects.add(fires.get(position));
        }
        return (ArrayList<WorldElement>)new ArrayList<>(List.of(objects, fireObjects)).stream().flatMap(List::stream).toList();
    }

    public void spreadFire(int maxAge) {
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
    }

    public void addFire(Vector2d position) {
        if (!contains(position)) {
            return;
        }
        if (fires.get(position) != null) {
            return;
        }
        Fire fire = new Fire(position);
        fires.put(position, fire);
        notifyObservers("Fire added at " + position);
    }
}
