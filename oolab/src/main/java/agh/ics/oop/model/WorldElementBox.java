package agh.ics.oop.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class WorldElementBox {

    private static final double IMAGE_SIZE = 20.0;  // Ustalony rozmiar obrazka 20x20

    public static VBox createAnimalBox(Vector2d position, String orientation) {
        // Wczytanie odpowiedniej tekstury w zależności od orientacji zwierzęcia
        String imagePath = getAnimalImagePath(orientation);

        // Tworzenie instancji Image
        Image animalImage = new Image(imagePath);

        // Tworzenie ImageView i ustawienie rozmiaru
        ImageView animalImageView = new ImageView(animalImage);
        animalImageView.setFitWidth(IMAGE_SIZE);
        animalImageView.setFitHeight(IMAGE_SIZE);

        // Tworzenie etykiety z pozycją
        Label positionLabel = new Label("Position: " + position.toString());

        // Tworzenie VBox, do którego dodajemy obrazek i etykietę
        VBox animalBox = new VBox(5); // Odstęp 5 pikseli między obrazkiem a etykietą
        animalBox.getChildren().addAll(animalImageView, positionLabel);

        // Wyśrodkowanie elementów w VBox
        animalBox.setStyle("-fx-alignment: center;");

        return animalBox;
    }

    public static VBox createGrassBox(Vector2d position) {
        // Wczytanie tekstury trawy
        String imagePath = "/resources/grass.png"; // Załóżmy, że trawa jest w resources
        Image grassImage = new Image(imagePath);

        // Tworzenie ImageView i ustawienie rozmiaru
        ImageView grassImageView = new ImageView(grassImage);
        grassImageView.setFitWidth(IMAGE_SIZE);
        grassImageView.setFitHeight(IMAGE_SIZE);

        // Tworzenie etykiety z pozycją
        Label positionLabel = new Label("Position: " + position.toString());

        // Tworzenie VBox, do którego dodajemy obrazek i etykietę
        VBox grassBox = new VBox(5); // Odstęp 5 pikseli między obrazkiem a etykietą
        grassBox.getChildren().addAll(grassImageView, positionLabel);

        // Wyśrodkowanie elementów w VBox
        grassBox.setStyle("-fx-alignment: center;");

        return grassBox;
    }

    // Metoda do wyboru odpowiedniej tekstury na podstawie orientacji zwierzęcia
    private static String getAnimalImagePath(String orientation) {
        switch (orientation.toLowerCase()) {
            case "north":
                return "/resources/animal_north.png";
            case "south":
                return "/resources/animal_south.png";
            case "east":
                return "/resources/animal_east.png";
            case "west":
                return "/resources/animal_west.png";
            default:
                return "/resources/animal_default.png";  // Domyślna tekstura w razie błędu
        }
    }
}

