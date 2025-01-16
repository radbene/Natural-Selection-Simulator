package agh.ics.oop.model;

import java.util.ArrayList;

public class GrassField extends AbstractWorldMap {

    public GrassField(int width, int height, int n) {
        super(width, height);
        this.addObserver(new ConsoleMapDisplay());
        this.addObserver(new FileMapDisplay(this.uuid));
        this.grassSpawner.spawnGrass(n);
    }

    public ArrayList<WorldElement> objectAt(Vector2d position) {
        return super.objectAt(position);   
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position);
    }
}
