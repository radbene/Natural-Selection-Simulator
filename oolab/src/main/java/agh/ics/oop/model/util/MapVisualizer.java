package agh.ics.oop.model.util;

import java.util.ArrayList;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.WorldElementBox;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.Animal;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private final GridPane gridPane;

    /**
     * Initializes the MapVisualizer with an instance of map to visualize.
     *
     * @param map
     */
    public MapVisualizer(WorldMap map) {
        this.map = map;
        this.gridPane = new GridPane();
    }

    public void addElementToGrid(WorldElement element, int x, int y) {
        String positionText = "(" + x + ", " + y + ")";
        WorldElementBox elementBox = new WorldElementBox(element, positionText);

        gridPane.add(elementBox.getContainer(), x, y);
    }


    public void addImageToGrid(String imagePath, int x, int y) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(40); // Adjust as needed
        imageView.setFitHeight(40);
        gridPane.add(imageView, x, y);
    }


    public GridPane getGridPane() {
        return gridPane;
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
        if (this.map.isOccupied(currentPosition)) {
            ArrayList<WorldElement> obj = this.map.objectAt(currentPosition);
            if (obj.stream().anyMatch(o -> o instanceof Animal)) {
                Animal animal = (Animal) obj.stream().filter(o -> o instanceof Animal).findFirst().get();
                return animal.toString();
            }
            return obj.stream().filter(o -> o instanceof Animal == false).map(o -> o.toString()).findFirst().get();
        }
        return EMPTY_CELL;
    }

}
