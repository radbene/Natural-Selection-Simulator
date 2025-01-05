package agh.ics.oop.model;

public class Equator {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    
    public Equator(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public boolean contains(Vector2d position) {
        return position.precedes(this.upperRight) && position.follows(this.lowerLeft);
    }

    public Vector2d getLowerLeft() {
        return this.lowerLeft;
    }

    public Vector2d getUpperRight() {
        return this.upperRight;
    }
}
