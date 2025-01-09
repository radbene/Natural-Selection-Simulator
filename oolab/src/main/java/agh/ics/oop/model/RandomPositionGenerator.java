package agh.ics.oop.model;

import java.util.Iterator;
import java.util.Random;

public class RandomPositionGenerator {
    private final Random random;

    public RandomPositionGenerator() {
        this.random = new Random();
    }

    public Iterator<Vector2d> generateInEquator(Equator equator) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Vector2d next() {
                Vector2d lowerLeft = equator.getLowerLeft();
                Vector2d upperRight = equator.getUpperRight();
                return generateRandomPosition(lowerLeft, upperRight);
            }
        };
    }

    public Iterator<Vector2d> generateOutsideEquator(AbstractWorldMap map, Equator equator) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Vector2d next() {
                Vector2d mapLowerLeft = map.getCurrentBounds().lowerLeft();
                Vector2d mapUpperRight = map.getCurrentBounds().upperRight();

                while (true) {
                    Vector2d position = generateRandomPosition(mapLowerLeft, mapUpperRight);
                    if (!equator.contains(position)) {
                        return position;
                    }
                }
            }
        };
    }

    private Vector2d generateRandomPosition(Vector2d lowerLeft, Vector2d upperRight) {
        System.out.println("lowerLeft: " + lowerLeft + " upperRight: " + upperRight);
        int x = random.nextInt(upperRight.getX() - lowerLeft.getX() + 1) + lowerLeft.getX();
        int y = random.nextInt(upperRight.getY() - lowerLeft.getY() + 1) + lowerLeft.getY();
        return new Vector2d(x, y);
    }
}
