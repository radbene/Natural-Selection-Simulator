package agh.ics.oop.model;

import static agh.ics.oop.model.RectangularMap.*;

public class Animal implements WorldElement {
    private Vector2d position;
    private static MoveValidator validator = new RectangularMap(5, 5);
    private Genome genome;
    private int energy;

    private MapDirection direction;

    public MapDirection getDirection() {
        return direction;
    }

    private int index = 0;

    static private Vector2d border_lowerleft;
    static private Vector2d border_upperright;

    public void setBorder(Vector2d lowerleft, Vector2d upperright) {
        border_lowerleft = lowerleft;
        border_upperright = upperright;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Animal() {
        this(new Vector2d(2, 2), MapDirection.NORTH);
    }

    public Animal(Vector2d position) {
        this(position, MapDirection.NORTH);
    }

    public Animal(Vector2d position, MapDirection direction) {
        /*
         * if (position.getX() < 0 || position.getY() < 0 || position.getX() > 4 ||
         * position.getY() > 4) {
         * throw new IllegalArgumentException("Position must be between 0 and 4");
         * }
         */
        this.position = position;
        this.direction = direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    // public boolean canMoveTo(Vector2d position) {
    // return position.follows(border_lowerleft) &&
    // position.precedes(border_upperright);
    // }

    public void move() {
        // TODO: Implement
        return;
    }

    // TODO: Remove this method
    public void move(MoveDirection dir) {
        switch (dir) {
            case FORWARD:
                Vector2d new_position1 = this.direction.toUnitVector().add(this.position);
                if (validator.canMoveTo(new_position1)) {
                    this.position = new_position1;
                }
                break;
            case BACKWARD:
                Vector2d new_position2 = this.position.subtract(this.direction.toUnitVector());
                if (validator.canMoveTo(new_position2)) {
                    this.position = new_position2;
                }
                break;
            case RIGHT:
                this.direction = this.direction.next(this.direction);
                break;
            case LEFT:
                this.direction = this.direction.previous(this.direction);
                break;

        }
    }

    public boolean isDead() {
        // TODO: Implement
        return false;
    }

    public void eatGrass(Grass grass) {
        // TODO: Implement
        return;
    }

    public boolean canReproduce() {
        // TODO: Implement
        return false;
    }

    Animal reproduce(Animal partner) {
        // TODO: Implement
        return new Animal();
    }

    Genome getGenome() {
        return this.genome;
    }

    int getEnergy() {
        return this.energy;
    }

    int getLifespan() {
        // TODO: Implement
        return 0;
    }

    int getChildren() {
        // TODO: Implement
        return 0;
    }
    
    @Override
    public String toString() {
        return switch (this.direction) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }
}
