package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private Animal animal;
    private WorldConfig config;
    private Globe globe;

    @BeforeEach
    void setUp() {
        config = new WorldConfig.Builder()
                .initialAnimalEnergy(100)
                .plantEnergy(10)
                .energyToReproduce(50)
                .build();

        globe = new AbstractWorldMap(10, 10) {
            @Override
            public Move nextPosition(Move mv) {
                return mv;
            }
        };

        animal = new Animal(new Vector2d(2, 2), config, globe);
    }

    @Test
    void testAnimalInitialization() {
        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(100, animal.getEnergy());
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertFalse(animal.isDead());
    }


    @Test
    void testAnimalEatGrass() {
        animal.eatGrass();
        assertEquals(110, animal.getEnergy()); // Energia powinna wzrosnąć o 10 (plantEnergy)
    }

    @Test
    void testAnimalReproduce() {
        Animal partner = new Animal(new Vector2d(2, 2), config, globe);
        Animal child = animal.reproduce(partner);

        assertNotNull(child);
        assertEquals(new Vector2d(2, 2), child.getPosition());
        assertEquals(50, animal.getEnergy()); // Energia rodzica powinna się zmniejszyć o połowę
        assertEquals(50, partner.getEnergy()); // Energia partnera powinna się zmniejszyć o połowę
        assertEquals(1, animal.getChildren()); // Liczba dzieci rodzica powinna wzrosnąć o 1
        assertEquals(1, partner.getChildren()); // Liczba dzieci partnera powinna wzrosnąć o 1
    }

    @Test
    void testAnimalIsDead() {
        animal.setEnergy(0);
        assertTrue(animal.isDead());
    }

    @Test
    void testAnimalCanReproduce() {
        assertTrue(animal.canReproduce()); // Energia początkowa to 100, a energia do reprodukcji to 50
        animal.setEnergy(40);
        assertFalse(animal.canReproduce()); // Energia poniżej progu reprodukcji
    }

    @Test
    void testAnimalGenome() {
        Genome genome = animal.getGenome();
        assertNotNull(genome);
        assertTrue(genome.getCurrentGene() >= 0 && genome.getCurrentGene() <= 7); // Sprawdzenie, czy gen jest w zakresie
    }

    @Test
    void testAnimalStats() {
        AnimalStats stats = animal.getStats();
        assertNotNull(stats);
        assertEquals(0, stats.getGrassEaten()); // Na początku zwierzę nie zjadło żadnej trawy
    }
}