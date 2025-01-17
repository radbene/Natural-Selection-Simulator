package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    @Test
    void testGetOrderedAnimals() {
        // Inicjalizacja mapy
        AbstractWorldMap worldMap = new GrassField(10, 10, 1);
        WorldConfig.Builder builder = new WorldConfig.Builder();
        WorldConfig config = builder.build();

        // Dodawanie zwierząt na mapę
        worldMap.animals.put(new Vector2d(1, 2), new ArrayList<>(List.of(new Animal(new Vector2d(1, 2), config, worldMap))));
        worldMap.animals.put(new Vector2d(0, 3), new ArrayList<>(List.of(new Animal(new Vector2d(0, 3), config, worldMap))));
        worldMap.animals.put(new Vector2d(1, 1), new ArrayList<>(List.of(new Animal(new Vector2d(1, 1), config, worldMap))));
        worldMap.animals.put(new Vector2d(0, 1), new ArrayList<>(List.of(new Animal(new Vector2d(0, 1), config, worldMap))));

        // Oczekiwany wynik: lista posortowana najpierw po X, potem po Y
        List<Vector2d> expectedPositions = List.of(
                new Vector2d(0, 1),
                new Vector2d(0, 3),
                new Vector2d(1, 1),
                new Vector2d(1, 2)
        );

        // Pobieramy pozycje zwierząt z metody getOrderedAnimals
        List<Vector2d> actualPositions = worldMap.getOrderedAnimals().stream()
                .map(Animal::getPosition) // Pobieramy pozycje
                .toList(); // Tworzymy listę

        // Sprawdzenie, czy pozycje są zgodne
        assertEquals(expectedPositions, actualPositions, "Pozycje zwierząt nie są poprawnie posortowane.");

    }
}