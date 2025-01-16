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
            System.out.println("Loading image: " + key);
            return new Image(stream);
        });

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        Label positionLabel = new Label(positionText);

        container = new VBox(5, imageView, positionLabel);
        container.setAlignment(Pos.CENTER);
    }

    public VBox getContainer() {
        return container;
    }
}