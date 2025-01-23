package agh.ics.oop.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Animal implements WorldElement {
    private Vector2d position;
    private WorldConfig config;
    private static MoveValidator validator;
    private Genome genome;
    // private int energy;
    private AnimalStats stats;

    private Globe globe;
    private MapDirection direction;

    private IntegerProperty energy;

    public MapDirection getDirection() {
        return direction;
    }

    private static int idCounter = 0;
    private int id;

    public int getId() {
        return id;
    }

    private int childrenCount = 0;

    public int getDaysLived() {
        return daysLived;
    }

    public IntegerProperty energyProperty() {
        return energy;
    }

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

    // TODO: Add MoveValidator according to config, you can use MapBuilder
    public Animal(Vector2d position, WorldConfig config, Globe globe) {
        this(position,MapDirection.NORTH, config, globe);
    }

    public Animal(Vector2d position, MapDirection direction,WorldConfig config, Globe globe) {
        this(position,direction,config,Genome.randomGenome(config), globe);
    }

    public Animal(Vector2d position, MapDirection direction,WorldConfig config,Genome genome, Globe globe) {
        this.position = position;
        this.direction = direction;
        this.config = config;
        this.genome = genome;
        this.id = idCounter++;

        this.globe = globe;  //used to correct going out of bounds
        // this.energy = config.getInitialAnimalEnergy();
        this.energy = new SimpleIntegerProperty(config.getInitialAnimalEnergy());
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
        int gene = this.genome.getCurrentGene();
        direction = direction.useGene(gene);
        Move mv = new Move(position, direction);
        Move finalMove = globe.nextPosition(mv);

        position = finalMove.getPosition();
        direction = finalMove.getDirection();

        daysLived++;
        // energy--;
        energy.set(energy.get() - 1);
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
        return energy.get() <= 0;
    }

    public void eatGrass() {
        energy.set(energy.get() + config.getPlantEnergy());
    }

    public boolean canReproduce() {
        return energy.get() >= config.getEnergyToReproduce();
    }

    Animal reproduce(Animal partner) {
        Genome childGenome = new Genome(config).reproductionGenome(this,partner);
        Animal child = new Animal(position,config, globe);
        child.setGenome(childGenome);
        return child;
    }

    Genome getGenome() {
        return this.genome;
    }

    public int getEnergy() {
        // return this.energy;
        return energy.get();
    }

    public int getLifespan() {
        return daysLived;
    }

    int getChildren() {
        return childrenCount;
    }

    @Override
    public String toString() {
        return this.direction.toString();
    }

    @Override
    public String getResourceName() {
        switch (direction) {
            case NORTH: return "0.png";
            case NORTHEAST: return "1.png";
            case EAST: return "2.png";
            case SOUTHEAST: return "3.png";
            case SOUTH: return "4.png";
            case SOUTHWEST: return "5.png";
            case WEST: return "6.png";
            case NORTHWEST: return "7.png";
            default: return "0.png";
        }
    }
}
