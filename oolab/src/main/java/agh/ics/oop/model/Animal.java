package agh.ics.oop.model;

import static agh.ics.oop.model.RectangularMap.*;

public class Animal{
    private Vector2d position;
    private static MoveValidator validator = new RectangularMap(5,5);


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
        this(new Vector2d(2,2),MapDirection.NORTH);
    }

    public Animal(Vector2d position) {
        this(position,MapDirection.NORTH);
    }

    public Animal(Vector2d position, MapDirection direction) {
        /*if (position.getX() < 0 || position.getY() < 0 || position.getX() > 4 || position.getY() > 4) {
            throw new IllegalArgumentException("Position must be between 0 and 4");
        }*/
        this.position = position;
        this.direction = direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    //public boolean canMoveTo(Vector2d position) {
    //    return position.follows(border_lowerleft) && position.precedes(border_upperright);
    //}

    public void move(MoveDirection dir){
        switch (dir){
            case FORWARD:
                Vector2d new_position1 = this.direction.toUnitVector().add(this.position);
                if(validator.canMoveTo(new_position1)){
                    this.position = new_position1;
                }
                break;
            case BACKWARD:
                Vector2d new_position2 = this.position.subtract(this.direction.toUnitVector());
                if(validator.canMoveTo(new_position2)) {
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

    @Override
    public String toString() {
        return switch(this.direction){
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }
}
