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
}

