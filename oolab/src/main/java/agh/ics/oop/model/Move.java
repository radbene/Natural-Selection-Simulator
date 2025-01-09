package agh.ics.oop.model;

public class Move {
    private final Vector2d position;
    private final Vector2d orientation;
    private final MapDirection direction;

    public Move(Vector2d position, MapDirection direction) {
        this.position = position;
        this.direction = direction;
        this.orientation = direction.toUnitVector();
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getOrientation() {
        return orientation;
    }

    public Vector2d getNewPosition() {
        return this.position.add(this.orientation);
    }
}
