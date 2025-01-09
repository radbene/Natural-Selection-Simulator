package agh.ics.oop.model;

import static agh.ics.oop.model.RectangularMap.*;

public class Animal implements WorldElement {
    private Vector2d position;
    private WorldConfig config;
    private static MoveValidator validator = new RectangularMap(5, 5);
    private Genome genome;
    private int energy;
    private Globe globe;

    private MapDirection direction;

    public MapDirection getDirection() {
        return direction;
    }

    private int index = 0;
    private int childrenCount = 0;
    private int daysLived = 0;

    static private Vector2d border_lowerleft;
    static private Vector2d border_upperright;

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public void setBorder(Vector2d lowerleft, Vector2d upperright) {
        border_lowerleft = lowerleft;
        border_upperright = upperright;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Animal(Vector2d position, WorldConfig config) {
        this(position,MapDirection.NORTH, config );
    }

    public Animal(Vector2d position, MapDirection direction,WorldConfig config) {
        this(position,direction,config,Genome.randomGenome(config));
    }

    public Animal(Vector2d position, MapDirection direction,WorldConfig config,Genome genome) {
        this.position = position;
        this.direction = direction;
        this.config = config;
        this.genome = genome;

        this.energy = config.getInitialAnimalEnergy();
        this.globe = new Globe(new Vector2d(config.getMapWidth(), config.getMapHeight()));  //used to correct going out of bounds
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
        Move mv = new Move(position, direction);
        Move finalMove = globe.nextPosition(mv);

        position = finalMove.getPosition();
        direction = finalMove.getDirection();

        daysLived++;
        energy--;
        return;
    }

//    public void move(MoveDirection dir) {
//        switch (dir) {
//            case FORWARD:
//                Vector2d new_position1 = this.direction.toUnitVector().add(this.position);
//                if (validator.canMoveTo(new_position1)) {
//                    this.position = new_position1;
//                }
//                break;
//            case BACKWARD:
//                Vector2d new_position2 = this.position.subtract(this.direction.toUnitVector());
//                if (validator.canMoveTo(new_position2)) {
//                    this.position = new_position2;
//                }
//                break;
//            case RIGHT:
//                this.direction = this.direction.next(this.direction);
//                break;
//            case LEFT:
//                this.direction = this.direction.previous(this.direction);
//                break;
//
//        }
//    }

    public boolean isDead() {
        return energy <= 0;
    }

    public void eatGrass(Grass grass) {
        energy += config.getPlantEnergy();
        return;
    }

    public boolean canReproduce() {
        return energy >= config.getEnergyToReproduce();
    }

    Animal reproduce(Animal partner) {
        Genome childGenome = new Genome(config).reproductionGenome(this,partner);
        Animal child = new Animal(position,config);
        child.setGenome(childGenome);
        return child;
    }

    Genome getGenome() {
        return this.genome;
    }

    int getEnergy() {
        return this.energy;
    }

    int getLifespan() {
        return daysLived;
    }

    int getChildren() {
        return childrenCount;
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
