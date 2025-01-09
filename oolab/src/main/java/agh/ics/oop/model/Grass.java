package agh.ics.oop.model;

public class Grass implements WorldElement {
    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public String toString() {
        return "* ";
    }

    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

}
