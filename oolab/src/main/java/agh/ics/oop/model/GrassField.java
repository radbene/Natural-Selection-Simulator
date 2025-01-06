package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

public class GrassField extends AbstractWorldMap {

    public GrassField(int width, int height, int n) {
        Animal animal = new Animal();
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
        animal.setBorder(lowerLeft, upperRight);
        animal = null;
        this.addObserver(new ConsoleMapDisplay());
        this.grassSpawner.spawnGrass(n);
    }

    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && !(objectAt(position) instanceof Animal);
    }

    public WorldElement objectAt(Vector2d position) {
        WorldElement object = super.objectAt(position);
        if (object != null)
            return object;
        return grasses.get(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        minMax(animals, grasses);
        return new Boundary(this.lowerLeft, this.upperRight);
    }

}
