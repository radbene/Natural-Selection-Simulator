package agh.ics.oop;

import java.util.Objects;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WorldElementBox {
    private StackPane container;
    private WorldElement element;
    private String position;

    private static final double LOW_ENERGY_THRESHOLD = 0.25;
    private static final double HIGH_ENERGY_THRESHOLD = 0.75;

    private static final int MAX_ENERGY = 100;

    public WorldElementBox(WorldElement element, String position) {
        this.element = element;
        this.position = position;
        this.container = createBox();
    }

    private StackPane createBox() {
        StackPane pane = new StackPane();
        pane.setPrefSize(50, 50);

        Rectangle rect = new Rectangle(50, 50);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);

        if (element instanceof Animal) {
            System.out.println("Animal");
            Animal animal = (Animal) element;

            ImageView imageView = createImageView(element.getResourceName());
            ProgressBar energyBar = createEnergyBar(animal);

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.BOTTOM_CENTER);
            vBox.setSpacing(2);
            vBox.getChildren().addAll(imageView, energyBar);

            pane.getChildren().addAll(rect, vBox);
        } else {
            ImageView imageView = createImageView(element.getResourceName());
            pane.getChildren().addAll(rect, imageView);
        }

        return pane;
    }

    private ImageView createImageView(String resourceName) {
        ImageView imageView;
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(resourceName)));
            imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Image not found: " + resourceName);
            imageView = new ImageView();
        }
        return imageView;
    }

    private ProgressBar createEnergyBar(Animal animal) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(40);
        progressBar.setPrefHeight(5);
        progressBar.setProgress(calculateEnergyProgress(animal));

        setEnergyBarColor(progressBar, animal.getEnergy());

        animal.energyProperty().addListener((observable, oldValue, newValue) -> {
            double progress = calculateEnergyProgress(animal);
            progressBar.setProgress(progress);
            setEnergyBarColor(progressBar, newValue.intValue());
        });

        return progressBar;
    }

    private double calculateEnergyProgress(Animal animal) {
        return Math.min((double) animal.getEnergy() / MAX_ENERGY, 1.0);
    }

    private void setEnergyBarColor(ProgressBar progressBar, int energy) {
        double energyRatio = (double) energy / MAX_ENERGY;

        progressBar.getStyleClass().removeAll("low-energy", "medium-energy", "high-energy");

        if (energyRatio < LOW_ENERGY_THRESHOLD) {
            progressBar.getStyleClass().add("low-energy");
        } else if (energyRatio < HIGH_ENERGY_THRESHOLD) {
            progressBar.getStyleClass().add("medium-energy");
        } else {
            progressBar.getStyleClass().add("high-energy");
        }
    }

    public Node getContainer() {
        return container;
    }
}
