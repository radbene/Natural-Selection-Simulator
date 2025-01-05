package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;


public class FireWorldMap extends AbstractWorldMap{

    public Vector2d getLowerleft() {
        return lowerleft;
    }

    public Vector2d getUpperright() {
        return upperright;
    }

    private Vector2d lowerleft = new Vector2d(Integer.MIN_VALUE,Integer.MIN_VALUE);
    private Vector2d upperright = new Vector2d(Integer.MAX_VALUE,Integer.MAX_VALUE);


    public FireWorldMap(int n){
        Animal animal = new Animal();
        animal.setBorder(lowerleft,upperright);
        animal = null;
        this.addObserver(new ConsoleMapDisplay());
        this.noOfGrassFields = n;
        this.grassSpawner.spawnGrass(n);
    }

    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerleft)  && !(objectAt(position) instanceof Animal);
    }

    public WorldElement objectAt(Vector2d position) {
        WorldElement object = super.objectAt(position);
        if(object != null) return object;
        return grasses.get(position);
    }

    public void spreadFire(){
        // TODO: Implement
    }

    @Override
    public Boundary getCurrentBounds() {
        minMax(animals,grasses);
        return new Boundary(this.lowerleft,this.upperright);
    }
}
