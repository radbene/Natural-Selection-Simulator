package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FireWorldMapTest {

    private FireWorldMap fireWorldMap;
    private ConsoleMapDisplay mockObserver;

    private final int width = 5;
    private final int height = 5;
    private final int initialGrassCount = 3;

    @BeforeEach
    void setUp() {
        fireWorldMap = new FireWorldMap(width, height, initialGrassCount);

//        mockObserver = (ConsoleMapDisplay) mock(MapChangeListener.class);
//        fireWorldMap.addObserver(mockObserver);
    }

    @Test
    void testConstructorInitialGrass() {
        assertEquals(initialGrassCount, fireWorldMap.grasses.size(),
                "Grass map should contain exactly " + initialGrassCount + " entries.");
    }

    @Test
    void testAddFireOnGrassField() {
        Vector2d grassPosition = fireWorldMap.grasses.keySet().iterator().next();

        assertTrue(fireWorldMap.grasses.containsKey(grassPosition),
                "Expected grass at " + grassPosition);

        fireWorldMap.addFire(grassPosition);
        assertFalse(fireWorldMap.grasses.containsKey(grassPosition),
                "Grass should have been removed at " + grassPosition + " after adding fire.");
        ArrayList<WorldElement> elements = fireWorldMap.objectAt(grassPosition);
        assertTrue(elements.stream().anyMatch(e -> e instanceof Fire),
                "Expected to find a Fire object at position " + grassPosition);
//        verify(mockObserver, times(1)).mapChanged(fireWorldMap, "Fire added at");
    }

    @Test
    void testAddFireOnNonGrassField() {
        Vector2d noGrassPos = new Vector2d(0, 0);
        fireWorldMap.grasses.remove(noGrassPos);
        fireWorldMap.addFire(noGrassPos);

        ArrayList<WorldElement> elements = fireWorldMap.objectAt(noGrassPos);
        assertTrue(elements.isEmpty() || elements.stream().noneMatch(e -> e instanceof Fire),
                "No Fire should be added at a position with no grass");
//        verify(mockObserver, never()).mapChanged(fireWorldMap, "Fire added at");
    }

    @Test
    void testStartFire() {
        fireWorldMap.startFire();

        if (initialGrassCount > 0) {
            assertEquals(initialGrassCount - 1, fireWorldMap.grasses.size(),
                    "startFire should remove one grass tile and replace it with fire");
//            verify(mockObserver, times(1)).mapChanged(fireWorldMap, "Fire added at");
        }
    }

    @Test
    void testSpreadFire_BasicBehavior() {
        Vector2d centerPos = new Vector2d(width / 2, height / 2);
        fireWorldMap.grasses.put(centerPos, new Grass(centerPos));
        fireWorldMap.addFire(centerPos);

        int maxAge = 3;
        fireWorldMap.spreadFire(maxAge, false);

        fireWorldMap.grasses.clear();
        centerPos = new Vector2d(width / 2, height / 2);
        fireWorldMap.grasses.put(centerPos, new Grass(centerPos));
        Vector2d[] neighbors = {
            centerPos.add(new Vector2d(1, 0)),
            centerPos.add(new Vector2d(-1, 0)),
            centerPos.add(new Vector2d(0, 1)),
            centerPos.add(new Vector2d(0, -1))
        };
        for (Vector2d neighbor : neighbors) {
            if (fireWorldMap.contains(neighbor)) {
                fireWorldMap.grasses.put(neighbor, new Grass(neighbor));
            }
        }

        fireWorldMap.addFire(centerPos);
//        clearInvocations(mockObserver);

        fireWorldMap.spreadFire(3, false);
        for (Vector2d neighbor : neighbors) {
            if (fireWorldMap.contains(neighbor)) {
                ArrayList<WorldElement> neighborElems = fireWorldMap.objectAt(neighbor);
                assertTrue(neighborElems.stream().anyMatch(e -> e instanceof Fire),
                        "Expected Fire at neighbor " + neighbor);
            }
        }

//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        verify(mockObserver, atLeast(1)).mapChanged(fireWorldMap, captor.capture());
//        List<String> allMessages = captor.getAllValues();
//
//        long fireAddCount = allMessages.stream()
//                .filter(msg -> msg.contains("Fire added at"))
//                .count();
//
//        assertTrue(fireAddCount >= 1 && fireAddCount <= 4,
//                "Expected 1 to 4 new 'Fire added at' messages, but found " + fireAddCount);
    }

    @Test
    void testSpreadFire_RemoveOldFires() {
        Vector2d pos = new Vector2d(2, 2);
        fireWorldMap.grasses.put(pos, new Grass(pos));
        fireWorldMap.addFire(pos);

        int maxAge = 2;
        for (int i = 0; i < maxAge; i++) {
            fireWorldMap.spreadFire(maxAge, false);
            ArrayList<WorldElement> elems = fireWorldMap.objectAt(pos);
            assertFalse(elems.isEmpty(), "Fire should still exist at burn < maxAge");
        }

        fireWorldMap.spreadFire(maxAge, false);
        ArrayList<WorldElement> elems = fireWorldMap.objectAt(pos);
        assertTrue(elems.isEmpty(), "Fire should have been removed after reaching max age.");
//        verify(mockObserver, atLeastOnce()).mapChanged(fireWorldMap, "has burned out");
    }

    @Test
    void testObjectAtCombinesGrassAndFire() {
        Vector2d pos = new Vector2d(1, 1);
        Grass grass = new Grass(pos);
        fireWorldMap.grasses.put(pos, grass);
        fireWorldMap.addFire(pos);

        ArrayList<WorldElement> objects = fireWorldMap.objectAt(pos);
        assertEquals(1, objects.size(),
                "Expected 1 objects (Fire burnt grass) at position " + pos);

        assertFalse(objects.contains(grass), "Expected Grass in objectAt(...) result");
        assertTrue(objects.stream().anyMatch(e -> e instanceof Fire),
                "Expected Fire in objectAt(...) result");
    }

    @Test
    void testCalculateFreeFieldsOutsideEquatorAndInsideEquator() {
        int initialOutside = fireWorldMap.calculateFreeFieldsOutsideEquator();
        int initialInside = fireWorldMap.calculateFreeFieldsInsideEquator();

        Vector2d posEquator = new Vector2d(2, 2);
        Vector2d posOutside = new Vector2d(2, 1);

        fireWorldMap.grasses.put(posEquator, new Grass(posEquator));
        fireWorldMap.grasses.put(posOutside, new Grass(posOutside));
        fireWorldMap.addFire(posEquator);
        fireWorldMap.addFire(posOutside);

        int afterOutside = fireWorldMap.calculateFreeFieldsOutsideEquator();
        int afterInside = fireWorldMap.calculateFreeFieldsInsideEquator();

        assertEquals(initialOutside - 1, afterOutside,
                "Expected outside free fields to decrease by 1 after adding fire outside equator");
        assertEquals(initialInside - 1, afterInside,
                "Expected inside free fields to decrease by 1 after adding fire inside");
    }
};