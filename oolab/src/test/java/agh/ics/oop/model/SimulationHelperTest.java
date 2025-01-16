package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;

import agh.ics.oop.model.util.Boundary;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SimulationHelperTest {

    @Mock
    private AbstractWorldMap mockMap;

    @Mock
    private FireWorldMap mockFireMap;

    @Mock
    private WorldConfig mockConfig;

    @InjectMocks
    private SimulationHelper simulationHelper;
    
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Stub the config methods
        when(mockConfig.getDailyGrassGrowth()).thenReturn(2);
        when(mockConfig.getFireMaxAge()).thenReturn(3);
        when(mockConfig.getFireFreq()).thenReturn(2);

        // Define map boundaries
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(10, 10);
        Boundary boundaries = new Boundary(lowerLeft, upperRight);
        when(mockMap.getCurrentBounds()).thenReturn(boundaries);

        // Stub map's animals and grasses maps
        // To allow modification in tests, use a real HashMap instead of mocking
        when(mockMap.getAllAnimals()).thenReturn((List<Animal>) new HashMap<>());
        when(mockMap.getGrass()).thenReturn((List<Grass>) new HashMap<>());

        // If AbstractWorldMap has methods like 'contains', stub them as needed
        when(mockMap.contains(any(Vector2d.class))).thenAnswer(invocation -> {
            Vector2d pos = invocation.getArgument(0);
            return pos.getX() >= 0 && pos.getX() <= 10 && pos.getY() >= 0 && pos.getY() <= 10;
        });

        // Initialize SimulationHelper with mocks
        simulationHelper = new SimulationHelper(mockMap, mockConfig);
    }

    @Test
    void testRunEpochThrowsWhenEpochLimitReached() {
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(simulationHelper::runEpoch);
        }
        Executable run11thTime = simulationHelper::runEpoch;
        assertThrows(RuntimeException.class, run11thTime, "Expected a RuntimeException for epoch > 10");
    }

    @Test
    void testRunEpochWithFireWorldMap() {
        simulationHelper = new SimulationHelper(mockFireMap, mockConfig);

        simulationHelper.runEpoch();
        verify(mockFireMap, times(1)).spreadFire(eq(3), eq(false));
        verify(mockFireMap, times(1)).notifyObservers(anyString());
    }

     @Test
     void testRunEpochWithRegularMap() {
         simulationHelper.runEpoch();
         verify(mockMap, never()).spawnGrass(anyInt());
         verify(mockMap, times(1)).notifyObservers(anyString());
     }

    @Test
    void testGenerateStartingPositions() {
        int initialAnimalsCount = 5;
        List<Vector2d> positions = simulationHelper.generateStartingPositions(initialAnimalsCount);

        assertEquals(initialAnimalsCount, positions.size());
        assertFalse(positions.isEmpty(), "Positions list should not be empty");
    }

    @Test
    void testEatGrassCallsRemoveOnGrasses() {
        Vector2d sharedPos = new Vector2d(2, 3);
        Animal mockAnimal = mock(Animal.class);
        Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
        animals.put(sharedPos, new ArrayList<>(List.of(mockAnimal)));

        Grass mockGrass = mock(Grass.class);
        Map<Vector2d, Grass> grasses = new HashMap<>();
        grasses.put(sharedPos, mockGrass);

        when(mockMap.animals).thenReturn(animals);
        when(mockMap.grasses).thenReturn(grasses);

        simulationHelper.runEpoch();

        assertFalse(mockMap.grasses.containsKey(sharedPos), 
            "Grass should have been removed from the map after being eaten");
        verify(mockAnimal, times(1)).eatGrass();
    }
}

