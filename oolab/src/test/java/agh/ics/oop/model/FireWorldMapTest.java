package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FireWorldMapTest {

    private FireWorldMap fireWorldMap;

    @Mock
    private MapChangeListener mockObserver1;

    @Mock
    private MapChangeListener mockObserver2;

    private final int width = 5;
    private final int height = 5;
    private final int initialGrassCount = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<MapChangeListener> mockObservers = Arrays.asList(mockObserver1, mockObserver2);
        fireWorldMap = spy(new FireWorldMap(width, height, initialGrassCount, mockObservers));
        fireWorldMap.spawnGrass(initialGrassCount);
    }

    @Test
    void testConstructorInitialGrass() {
        assertEquals(initialGrassCount, fireWorldMap.getGrassesMap().size(),
                "Grass map should contain exactly " + initialGrassCount + " entries.");
    }

    @Test
    void testAddFireOnGrassField() {
        Vector2d grassPosition = fireWorldMap.getGrassesMap().keySet().iterator().next();

        assertTrue(fireWorldMap.getGrassesMap().containsKey(grassPosition),
                "Expected grass at " + grassPosition);
        fireWorldMap.addFire(grassPosition);
        assertFalse(fireWorldMap.getGrassesMap().containsKey(grassPosition),
                "Grass should have been removed at " + grassPosition + " after adding fire.");

        ArrayList<WorldElement> elements = fireWorldMap.objectAt(grassPosition);
        assertTrue(elements.stream().anyMatch(e -> e instanceof Fire),
                "Expected to find a Fire object at position " + grassPosition);
        verify(mockObserver1, times(1)).mapChanged(fireWorldMap, "Fire added at " + grassPosition);
        verify(mockObserver2, times(1)).mapChanged(fireWorldMap, "Fire added at " + grassPosition);
    }

    @Test
    void testAddFireOnNonGrassField() {
        Vector2d noGrassPos = new Vector2d(0, 0);

        fireWorldMap.getGrassesMap().remove(noGrassPos);
        fireWorldMap.addFire(noGrassPos);
        ArrayList<WorldElement> elements = fireWorldMap.objectAt(noGrassPos);
        assertTrue(elements.isEmpty() || elements.stream().noneMatch(e -> e instanceof Fire),
                "No Fire should be added at a position with no grass");
        verify(mockObserver1, never()).mapChanged(eq(fireWorldMap), anyString());
        verify(mockObserver2, never()).mapChanged(eq(fireWorldMap), anyString());
    }

    @Test
    void testStartFire() {
        fireWorldMap.startFire();

        assertEquals(initialGrassCount - 1, fireWorldMap.getGrassesMap().size(),
                "startFire should remove one grass tile and replace it with fire");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockObserver1, times(1)).mapChanged(eq(fireWorldMap), captor.capture());
        verify(mockObserver2, times(1)).mapChanged(eq(fireWorldMap), captor.capture());

        List<String> allMessages = captor.getAllValues();
        assertTrue(allMessages.stream().allMatch(msg -> msg.startsWith("Fire added at ")),
                "All observer messages should indicate fire addition");
    }

    @Test
    void testSpreadFire_BasicBehavior() {
        Vector2d centerPos = new Vector2d(width / 2, height / 2);
        fireWorldMap.getGrassesMap().put(centerPos, new Grass(centerPos));
        fireWorldMap.addFire(centerPos);

        int maxAge = 3;
        fireWorldMap.spreadFire(maxAge, false);

        fireWorldMap.getGrassesMap().clear();
        centerPos = new Vector2d(width / 2, height / 2);
        fireWorldMap.getGrassesMap().put(centerPos, new Grass(centerPos));
        Vector2d[] neighbors = {
                centerPos.add(new Vector2d(1, 0)),
                centerPos.add(new Vector2d(-1, 0)),
                centerPos.add(new Vector2d(0, 1)),
                centerPos.add(new Vector2d(0, -1))
        };
        for (Vector2d neighbor : neighbors) {
            if (fireWorldMap.contains(neighbor)) {
                fireWorldMap.getGrassesMap().put(neighbor, new Grass(neighbor));
            }
        }

        fireWorldMap.addFire(centerPos);

        fireWorldMap.spreadFire(maxAge, false);
        for (Vector2d neighbor : neighbors) {
            if (fireWorldMap.contains(neighbor)) {
                ArrayList<WorldElement> neighborElems = fireWorldMap.objectAt(neighbor);
                assertTrue(neighborElems.stream().anyMatch(e -> e instanceof Fire),
                        "Expected Fire at neighbor " + neighbor);
            }
        }

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockObserver1, atLeast(1)).mapChanged(eq(fireWorldMap), captor.capture());
        verify(mockObserver2, atLeast(1)).mapChanged(eq(fireWorldMap), captor.capture());

        List<String> allMessages = captor.getAllValues();
        long fireAddCount = allMessages.stream()
                .filter(msg -> msg.startsWith("Fire added at "))
                .count();

        assertTrue(fireAddCount >= 1 && fireAddCount <= 12,
                "Expected 1 to 12 new 'Fire added at' messages, but found " + fireAddCount);
    }

    @Test
    void testSpreadFire_RemoveOldFires() {
        Vector2d pos = new Vector2d(2, 2);
        fireWorldMap.getGrassesMap().put(pos, new Grass(pos));
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
    }

    @Test
    void testObjectAtCombinesGrassAndFire() {
        Vector2d pos = new Vector2d(1, 1);
        Grass grass = new Grass(pos);
        fireWorldMap.getGrassesMap().put(pos, grass);
        fireWorldMap.addFire(pos);

        ArrayList<WorldElement> objects = fireWorldMap.objectAt(pos);
        assertEquals(1, objects.size(),
                "Expected 1 object (Fire burnt grass) at position " + pos);

        assertFalse(objects.contains(grass), "Expected Grass to be removed in objectAt(...) result");
        assertTrue(objects.stream().anyMatch(e -> e instanceof Fire),
                "Expected Fire in objectAt(...) result");
        verify(mockObserver1, times(1)).mapChanged(fireWorldMap, "Fire added at " + pos);
        verify(mockObserver2, times(1)).mapChanged(fireWorldMap, "Fire added at " + pos);
    }

    @Test
    void testCalculateFreeFieldsOutsideEquatorAndInsideEquator() {
        int initialOutside = fireWorldMap.calculateFreeFieldsOutsideEquator();
        int initialInside = fireWorldMap.calculateFreeFieldsInsideEquator();

        Vector2d posEquator = new Vector2d(2, 2);
        Vector2d posOutside = new Vector2d(2, 1);

        fireWorldMap.getGrassesMap().put(posEquator, new Grass(posEquator));
        fireWorldMap.getGrassesMap().put(posOutside, new Grass(posOutside));
        fireWorldMap.addFire(posEquator);
        fireWorldMap.addFire(posOutside);

        int afterOutside = fireWorldMap.calculateFreeFieldsOutsideEquator();
        int afterInside = fireWorldMap.calculateFreeFieldsInsideEquator();

        assertEquals(initialOutside - 1, afterOutside,
                "Expected outside free fields to decrease by 1 after adding fire outside equator");
        assert(abs(initialInside - afterInside) <= 1);

        verify(mockObserver1, times(2)).mapChanged(eq(fireWorldMap), anyString());
        verify(mockObserver2, times(2)).mapChanged(eq(fireWorldMap), anyString());
    }
}
