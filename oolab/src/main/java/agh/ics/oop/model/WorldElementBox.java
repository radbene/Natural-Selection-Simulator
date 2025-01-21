package agh.ics.oop.model;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class WorldElementBox {
    private StackPane container;
    private WorldElement element;
    private String position;

    private static final double LOW_ENERGY_THRESHOLD = 0.25;
    private static final double HIGH_ENERGY_THRESHOLD = 0.75;

    private static final int MAX_ENERGY = 100; // TODO: change to sth dynamic

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

        Text text = new Text(element.getResourceName());

        if (element instanceof Animal) {
            Animal animal = (Animal) element;
            ProgressBar energyBar = createEnergyBar(animal);

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.BOTTOM_CENTER);
            vBox.setSpacing(2);

            vBox.getChildren().addAll(text, energyBar);

            pane.getChildren().addAll(rect, vBox);
        } else {
            pane.getChildren().addAll(rect, text);
        }

        return pane;
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
