package agh.ics.oop.model;

public class Animal {
    private Vector2d position;

    private MapDirection direction;
    public MapDirection getDirection() {
        return direction;
    }

    private int index = 0;


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

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirections dir){
        switch (dir){
            case FORWARD:
                Vector2d new_position1 = this.direction.toUnitVector(this.direction).add(this.position);
                if(new_position1.getX() >= 0 && new_position1.getY() >= 0 && new_position1.getX() <= 4 && new_position1.getY() <=4){
                    this.position = new_position1;
                }
                break;
            case BACKWARD:
                Vector2d new_position2 = this.position.subtract(this.direction.toUnitVector(this.direction));
                if(new_position2.getX() >= 0 && new_position2.getY() >= 0 && new_position2.getX() <= 4 && new_position2.getY() <=4){
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
        return "Animal: " + index +
                "\nposition = " + position +
                ", direction = " + direction;
    }
}
