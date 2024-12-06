package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;


public class GrassField extends AbstractWorldMap{

    private Map<Vector2d, Animal> animals;
    private Map<Vector2d,Grass> grasses;
    private MapVisualizer visualizer;

    public Vector2d getLowerleft() {
        return lowerleft;
    }

    public Vector2d getUpperright() {
        return upperright;
    }

    private Vector2d lowerleft = new Vector2d(Integer.MIN_VALUE,Integer.MIN_VALUE);
    private Vector2d upperright = new Vector2d(Integer.MAX_VALUE,Integer.MAX_VALUE);

    private int noOfGrassFields = 0;

    public GrassField(int n){
        this.noOfGrassFields = n;
        animals = new HashMap<>();
        grasses = new HashMap<>();
        Animal animal = new Animal();
        animal.setBorder(lowerleft,upperright);
        animal = null;
        this.addObserver(new ConsoleMapDisplay());
        this.visualizer = new MapVisualizer(this);
        this.GenerateGrassFields();
    }
    private void GenerateGrassFields() {
        int n = this.noOfGrassFields;
        double upperlimit = Math.sqrt(n * 10);
        int i = 0;
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator((int)upperlimit, (int)upperlimit, n);
        Iterator<Vector2d> positionsIterator = randomPositionGenerator.iterator();

        while(positionsIterator.hasNext()) {
            Vector2d new_grass_position = positionsIterator.next();
            grasses.put(new_grass_position, new Grass(new_grass_position));
        }
    }
        /*while (i < noOfGrassFields) {
            int x = (int) (Math.random() * upperlimit);
            int y = (int) (Math.random() * upperlimit);
            boolean flag = false;
            for (Vector2d position : grasses.keySet()) {
                if (Objects.equals(position, new Vector2d(x, y))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                grasses.put(new Vector2d(x, y), new Grass(new Vector2d(x, y)));
                i++;
            }
        }
        System.out.println(grasses.size());
    }
    */

    private void minMax(Map<Vector2d,Animal> animals,Map<Vector2d, Grass> grasses){
        int xmin = Integer.MAX_VALUE;
        int xmax = Integer.MIN_VALUE;
        int ymin = Integer.MAX_VALUE;
        int ymax = Integer.MIN_VALUE;
        for(Vector2d v : animals.keySet()){
             xmin = Math.min(v.getX(),xmin);
             xmax = Math.max(v.getX(),xmax);
             ymin = Math.min(v.getY(),ymin);
             ymax = Math.max(v.getY(),ymax);
        }

        for(Vector2d v : grasses.keySet()){
            if(v.getX() < xmin) xmin = v.getX();
            if(v.getX() > xmax) xmax = v.getX();
            if(v.getY() < ymin) ymin = v.getY();
            if(v.getY() > ymax) ymax = v.getY();
        }
        this.lowerleft = new Vector2d(xmin,ymin);
        this.upperright = new Vector2d(xmax,ymax);
    }

    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerleft)  && !(objectAt(position) instanceof Animal);
    }

    public WorldElement objectAt(Vector2d position) {
        WorldElement object = super.objectAt(position);
        if(object != null) return object;
        return grasses.get(position);
    }



    @Override
    public Boundary getCurrentBounds() {
        minMax(animals,grasses);
        return new Boundary(this.lowerleft,this.upperright);
    }

   /* @Override
    public String toString() {
        minMax(animals,grasses);
        System.out.println(lowerleft);
        System.out.println(upperright);
        return visualizer.draw(lowerleft, upperright);
    }


    @Override
    public int hashCode() {
        return Objects.hash(noOfGrassFields, lowerleft, upperright, visualizer, animals, grasses);
    }
*/
    /*public boolean place(Animal animal){
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        MoveDirection oppositeDirection = direction.opposite();
        animal.move(direction);
        if(place(animal)){
            animals.remove(oldPosition);
        }
        else{
            if(!oldPosition.equals(animal.getPosition())) {
                animal.move(oppositeDirection);
            }
        }
    }


    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) != null) return animals.get(position);
        if (grasses.get(position) != null) return grasses.get(position);
        return null;
    }


    */
}
