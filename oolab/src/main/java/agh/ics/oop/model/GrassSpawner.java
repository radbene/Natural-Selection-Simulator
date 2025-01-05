package agh.ics.oop.model;

import java.util.Iterator;

public class GrassSpawner {
    private final AbstractWorldMap map;
    private final Equator equator;
    private final RandomPositionGenerator randomPositionGenerator;

    public GrassSpawner(AbstractWorldMap map, Equator equator, RandomPositionGenerator randomPositionGenerator) {
        this.map = map;
        this.equator = equator;
        this.randomPositionGenerator = randomPositionGenerator;
    }

    public void spawnGrass(int n) {
        int equatorGrass = (int) Math.round(n * 0.8);
        int otherGrass = n - equatorGrass;

        spawnGrassFromIterator(equatorGrass, randomPositionGenerator.generateInEquator(equator));
        spawnGrassFromIterator(otherGrass, randomPositionGenerator.generateOutsideEquator(map, equator));
    }

    private void spawnGrassFromIterator(int count, Iterator<Vector2d> iterator) {
        int attempts = 0;
        while (count > 0 && attempts < 10 * count) {
            Vector2d position = iterator.next();
            if (!map.isOccupied(position)) {
                map.addGrass(new Grass(position));
                count--;
            }
            attempts++;
        }
    }
}
