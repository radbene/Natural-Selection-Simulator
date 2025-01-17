package agh.ics.oop.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileMapDisplay implements MapChangeListener {
    private final UUID mapId;

    public FileMapDisplay(UUID mapId) {
        this.mapId = mapId;
    }

    @Override
    public void mapChanged(WorldMap map, String message) {
        String filename = map.getId() + ".log"; // Nazwa pliku to map_id.log

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(message); // Zapisujemy wiadomość (np. ruch, zmiana mapy)
            writer.newLine(); // Dodajemy nową linię
            writer.write(map.toString()); // Zapisujemy aktualny wygląd mapy
            writer.newLine(); // Dodajemy nową linię
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }

}

