package agh.ics.oop.model;

import agh.ics.oop.model.variants.EMapVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimulationHelperTest {

    private FireWorldMap spyFireMap;

    @Mock
    private WorldConfig mockConfig;

    private SimulationHelper simulationHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spyFireMap = spy(new FireWorldMap(10, 10, 10));
        when(mockConfig.getDailyGrassGrowth()).thenReturn(2);
        when(mockConfig.getFireMaxAge()).thenReturn(3);
        when(mockConfig.getFireFreq()).thenReturn(2);
        when(mockConfig.getMapVariant()).thenReturn(EMapVariant.FIRE);
        simulationHelper = new SimulationHelper(spyFireMap, mockConfig);
    }

    @Test
    void testRunEpochWithFireWorldMap() {
        simulationHelper.runEpoch();

        verify(spyFireMap, times(1)).spreadFire(eq(3), eq(false));
        verify(spyFireMap, times(1)).notifyObservers(anyString());
    }

    @Test
    void testRunEpochWithRegularMap() {
        when(mockConfig.getMapVariant()).thenReturn(EMapVariant.STANDARD);
        simulationHelper.runEpoch();
        verify(spyFireMap, never()).spreadFire(anyInt(), anyBoolean());
        verify(spyFireMap, times(1)).notifyObservers(anyString());
    }

    @Test
    void testGenerateStartingPositions() {
        int initialAnimalsCount = 5;
        List<Vector2d> positions = simulationHelper.generateStartingPositions(initialAnimalsCount);

        assertEquals(initialAnimalsCount, positions.size(), "Should generate the correct number of positions");
        assertFalse(positions.isEmpty(), "Positions list should not be empty");

        for (Vector2d pos : positions) {
            assertTrue(spyFireMap.getAnimalsMap().containsKey(pos), "Map should contain position: " + pos);
            assertEquals(1, spyFireMap.getAnimalsMap().get(pos).size(), "Each position should have one animal");
        }
    }

    @Test
    void testEatGrassCallsRemoveOnGrasses() {
        Vector2d sharedPos = new Vector2d(2, 3);
        Animal mockAnimal = mock(Animal.class);
        List<Animal> animalList = new ArrayList<>();
        animalList.add(mockAnimal);
        spyFireMap.getAnimalsMap().put(sharedPos, new ArrayList<>(animalList));

        Grass mockGrass = mock(Grass.class);
        spyFireMap.getGrassesMap().put(sharedPos, mockGrass);
        assertTrue(spyFireMap.getGrassesMap().containsKey(sharedPos), "Grass should exist at sharedPos before epoch");
        simulationHelper.runEpoch();
        assertTrue(spyFireMap.getGrassesMap().containsKey(sharedPos),
                "Grass should have been removed from the map after animal moves");
    }
}
