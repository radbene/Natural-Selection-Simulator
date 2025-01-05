package agh.ics.oop.model;

public class Move {
    private final Vector2d position;
    private final Vector2d direction;
    private final MapDirection orientation;

    public Move(Vector2d position, Vector2d direction, MapDirection orientation) {
        this.position = position;
        this.direction = direction;
        this.orientation = orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getDirection() {
        return direction;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getNewPosition() {
        return this.position.add(this.direction);
    }
}
