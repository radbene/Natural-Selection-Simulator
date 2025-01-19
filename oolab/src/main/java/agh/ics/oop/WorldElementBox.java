package agh.ics.oop;

import agh.ics.oop.model.WorldElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class WorldElementBox {
    private static final Map<String, Image> imageCache = new HashMap<>();
    private final VBox container;

    public WorldElementBox(WorldElement element, String positionText) {
        String resourceName = element.getResourceName();
        Image image = imageCache.computeIfAbsent(resourceName, key -> 
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + key)))
        );

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

