package agh.ics.oop.model;

public enum MoveDirection {
    FORWARD,
    RIGHT,
    BACKWARD,
    LEFT;

    public MoveDirection opposite() {
        return switch (this) {
            case FORWARD -> BACKWARD;
            case RIGHT -> LEFT;
            case LEFT -> RIGHT;
            case BACKWARD -> FORWARD;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case FORWARD -> new Vector2d(0, 1);
            case RIGHT -> new Vector2d(1, 0);
            case LEFT -> new Vector2d(-1, 0);
            case BACKWARD -> new Vector2d(0, -1);
        };
    }
}

