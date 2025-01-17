package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrassField extends AbstractWorldMap {

    public GrassField(int width, int height, int n) {
        super(width, height);
        this.addObserver(new ConsoleMapDisplay());
        this.grassSpawner.spawnGrass(n);
    }

    public Optional<List<WorldElement>> objectAt(Vector2d position) {
        return super.objectAt(position);   
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position);
    }
}
