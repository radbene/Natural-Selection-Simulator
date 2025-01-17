package agh.ics.oop.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.Animal;

/**
 * The map visualizer converts the {@link WorldMap} map into a string
 * representation.
 *
 * @author apohllo, idzik
 */
public class MapVisualizer {
    private static final String EMPTY_CELL = "  ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
    private final WorldMap map;

    /**
     * Initializes the MapVisualizer with an instance of map to visualize.
     *
     * @param map
     */
    public MapVisualizer(WorldMap map) {
        this.map = map;
    }

    /**
     * Convert selected region of the map into a string. It is assumed that the
     * indices of the map will have no more than two characters (including the
     * sign).
     *
     * @param lowerLeft  The lower left corner of the region that is drawn.
     * @param upperRight The upper right corner of the region that is drawn.
     * @return String representation of the selected region of the map.
     */
    public String draw(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.getY() + 1; i >= lowerLeft.getY() - 1; i--) {
            if (i == upperRight.getY() + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.getX(); j <= upperRight.getX() + 1; j++) {
                if (i < lowerLeft.getY() || i > upperRight.getY()) {
                    builder.append(drawFrame(j <= upperRight.getX()));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.getX()) {
                        builder.append(drawObject(new Vector2d(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.getX(); j < upperRight.getX() + 1; j++) {
            builder.append(String.format("%2d ", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String drawObject(Vector2d currentPosition) {
        // Get the Optional containing the list of WorldElement objects at the position
        Optional<List<WorldElement>> objectsAtPosition = this.map.objectAt(currentPosition);

        // Check if the Optional is present and contains elements
        if (objectsAtPosition.isPresent()) {
            List<WorldElement> obj = objectsAtPosition.get();  // Get the list of objects at that position

            // Look for an animal in the list
            Optional<Animal> animal = obj.stream()
                    .filter(o -> o instanceof Animal)
                    .map(o -> (Animal) o)
                    .findFirst();

            if (animal.isPresent()) {
                return animal.get().toString();  // Return the animal's string representation
            }

            // If no animal is found, return the first non-animal object's string representation
            return obj.stream()
                    .filter(o -> !(o instanceof Animal))
                    .map(WorldElement::toString)
                    .findFirst()
                    .orElse(EMPTY_CELL);  // If no non-animal object, return EMPTY_CELL
        }

        // If the position is not occupied (Optional is empty), return EMPTY_CELL
        return EMPTY_CELL;
    }





}
