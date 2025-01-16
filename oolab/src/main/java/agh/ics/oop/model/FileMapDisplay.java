package agh.ics.oop.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileMapDisplay implements MapChangeListener {
    private final UUID mapId;
    private int updateCount = 0;

    public FileMapDisplay(UUID mapId) {
        this.mapId = mapId;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String logFileName = "map_" + mapId + ".log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write("(Map " + worldMap.getId() + ") Update #" + (++updateCount) + ": " + message);
            writer.newLine();
            writer.write(worldMap.toString());
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}
