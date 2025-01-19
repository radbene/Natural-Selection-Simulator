package agh.ics.oop.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;

public class WorldElementBox {
    private static final Map<String, Image> imageCache = new HashMap<>();
    private final VBox container;

    public WorldElementBox(WorldElement element, String positionText) {
        String resourceName = element.getResourceName();
        Image image = imageCache.computeIfAbsent(resourceName, key -> {
            var stream = getClass().getResourceAsStream("/" + key);
            if (stream == null) {
                throw new IllegalArgumentException("Resource not found: " + key);
            }
            return new Image(stream, 50, 50, true, true);
        });

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        Label positionLabel = new Label(positionText);

        container = new VBox(5, imageView);
        container.setAlignment(Pos.CENTER);
    }

    public VBox getContainer() {
        return container;
    }
}
