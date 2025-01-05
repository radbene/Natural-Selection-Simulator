package agh.ics.oop.model;

public interface WorldElement {

    public Vector2d getPosition();

    public boolean isAt(Vector2d position);

    public String toString();
}
