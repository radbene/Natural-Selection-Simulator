package agh.ics.oop;

import agh.ics.oop.model.WorldConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import agh.ics.oop.model.variants.EMapVariant;
import agh.ics.oop.model.variants.EMutationVariant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import agh.ics.oop.model.WorldConfig.Builder;

public class SimulationConfiguration extends Application {

    // Declare UI components as instance variables
    private TextField mapWidthField;
    private TextField mapHeightField;
    private ComboBox<EMapVariant> mapVariantComboBox;
    private TextField initialPlantCountField;
    private TextField plantEnergyField;
    private TextField dailyGrassGrowthField;
    private TextField initialAnimalCountField;
    private TextField initialAnimalEnergyField;
    private TextField energyToReproduceField;
    private TextField parentEnergyCostField;
    private TextField minMutationsField;
    private TextField maxMutationsField;
    private ComboBox<EMutationVariant> mutationVariantComboBox;
    private TextField genomeLengthField;
    private TextField fireMaxAgeField;
    private TextField fireFreqField;

    @Override
    public void start(Stage primaryStage) {
        // Set the title of the window
        primaryStage.setTitle("Simulation Configuration");

        // Enable fullscreen mode
        primaryStage.setFullScreen(true);

        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal spacing between columns
        gridPane.setVgap(10); // Vertical spacing between rows

        // Load the CSS file
        gridPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/configStyles.css")).toExternalForm());
        gridPane.getStyleClass().add("grid-pane"); // Apply the grid-pane style

        // Initialize UI components
        mapWidthField = new TextField();
        mapHeightField = new TextField();
        mapVariantComboBox = new ComboBox<>();
        mapVariantComboBox.getItems().addAll(EMapVariant.values());
        mapVariantComboBox.setValue(EMapVariant.STANDARD); // Set default value

        initialPlantCountField = new TextField();
        plantEnergyField = new TextField();
        dailyGrassGrowthField = new TextField();
        initialAnimalCountField = new TextField();
        initialAnimalEnergyField = new TextField();
        energyToReproduceField = new TextField();
        parentEnergyCostField = new TextField();
        minMutationsField = new TextField();
        maxMutationsField = new TextField();
        mutationVariantComboBox = new ComboBox<>();
        mutationVariantComboBox.getItems().addAll(EMutationVariant.values());
        mutationVariantComboBox.setValue(EMutationVariant.STANDARD); // Set default value
        genomeLengthField = new TextField();
        fireMaxAgeField = new TextField();
        fireFreqField = new TextField();

        // Bind visibility of fire-related fields to the selected map variant
        fireMaxAgeField.visibleProperty().bind(
                mapVariantComboBox.valueProperty().isEqualTo(EMapVariant.FIRE)
        );
        fireFreqField.visibleProperty().bind(
                mapVariantComboBox.valueProperty().isEqualTo(EMapVariant.FIRE)
        );

        // Bind visibility of fire-related labels to the selected map variant
        Label fireMaxAgeLabel = new Label("Fire Max Age:");
        Label fireFreqLabel = new Label("Fire Frequency:");
        fireMaxAgeLabel.visibleProperty().bind(
                mapVariantComboBox.valueProperty().isEqualTo(EMapVariant.FIRE)
        );
        fireFreqLabel.visibleProperty().bind(
                mapVariantComboBox.valueProperty().isEqualTo(EMapVariant.FIRE)
        );

        // Add components to the grid
        int row = 0;
        gridPane.add(new Label("Map Width:"), 0, row);
        gridPane.add(mapWidthField, 1, row++);

        gridPane.add(new Label("Map Height:"), 0, row);
        gridPane.add(mapHeightField, 1, row++);

        gridPane.add(new Label("Map Variant:"), 0, row);
        gridPane.add(mapVariantComboBox, 1, row++);

        gridPane.add(new Label("Initial Plant Count:"), 0, row);
        gridPane.add(initialPlantCountField, 1, row++);

        gridPane.add(new Label("Plant Energy:"), 0, row);
        gridPane.add(plantEnergyField, 1, row++);

        gridPane.add(new Label("Daily Grass Growth:"), 0, row);
        gridPane.add(dailyGrassGrowthField, 1, row++);

        gridPane.add(new Label("Initial Animal Count:"), 0, row);
        gridPane.add(initialAnimalCountField, 1, row++);

        // Right Column
        row = 0; // Reset row to 0 for the right column
        gridPane.add(new Label("Initial Animal Energy:"), 2, row);
        gridPane.add(initialAnimalEnergyField, 3, row++);

        gridPane.add(new Label("Energy to Reproduce:"), 2, row);
        gridPane.add(energyToReproduceField, 3, row++);

        gridPane.add(new Label("Parent Energy Cost:"), 2, row);
        gridPane.add(parentEnergyCostField, 3, row++);

        gridPane.add(new Label("Min Mutations:"), 2, row);
        gridPane.add(minMutationsField, 3, row++);

        gridPane.add(new Label("Max Mutations:"), 2, row);
        gridPane.add(maxMutationsField, 3, row++);

        gridPane.add(new Label("Mutation Variant:"), 2, row);
        gridPane.add(mutationVariantComboBox, 3, row++);

        gridPane.add(new Label("Genome Length:"), 2, row);
        gridPane.add(genomeLengthField, 3, row++);

        // Add fire-related fields to the grid
        gridPane.add(fireMaxAgeLabel, 2, row);
        gridPane.add(fireMaxAgeField, 3, row++);
        gridPane.add(fireFreqLabel, 2, row);
        gridPane.add(fireFreqField, 3, row++);

        // Create buttons
        Button saveButton = new Button("Save Configuration");
        Button startButton = new Button("Start Simulation");
        Button loadConfigButton = new Button("Load Configuration");

        // Add buttons to the grid
        gridPane.add(saveButton, 0, row, 2, 1);
        gridPane.add(startButton, 2, row, 2, 1);
        gridPane.add(loadConfigButton, 0, row + 1, 2, 1);

        // Set button actions
        saveButton.setOnAction(event -> saveConfiguration());
        startButton.setOnAction(event -> startSimulation(primaryStage));
        loadConfigButton.setOnAction(event -> openLoadConfigurationWindow());

        // Create a scene and set it to the stage
        Scene scene = new Scene(gridPane, 700, 500); // Adjusted window size
        primaryStage.setScene(scene);

        loadConfigurationFromFile("configs/default.txt");
        // Show the stage
        primaryStage.show();
    }

    /**
     * Validates a field to ensure it contains a number between 1 and 999.
     *
     * @param value     The value to validate.
     * @param fieldName The name of the field (for error messages).
     * @throws IllegalArgumentException If the value is invalid.
     */
    private void validateValue(int value, String fieldName) throws IllegalArgumentException {
        if (value <= 0 || value > 100) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0 and less than 100.");
        }
    }

    /**
     * Saves the current configuration to a file.
     */
    private void saveConfiguration() {
        try {
            // Parse and validate every field
            int mapWidth = Integer.parseInt(mapWidthField.getText());
            validateValue(mapWidth, "Map Width");

            int mapHeight = Integer.parseInt(mapHeightField.getText());
            validateValue(mapHeight, "Map Height");

            int initialPlantCount = Integer.parseInt(initialPlantCountField.getText());
            validateValue(initialPlantCount, "Initial Plant Count");

            int plantEnergy = Integer.parseInt(plantEnergyField.getText());
            validateValue(plantEnergy, "Plant Energy");

            int dailyGrassGrowth = Integer.parseInt(dailyGrassGrowthField.getText());
            validateValue(dailyGrassGrowth, "Daily Grass Growth");

            int initialAnimalCount = Integer.parseInt(initialAnimalCountField.getText());
            validateValue(initialAnimalCount, "Initial Animal Count");

            int initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyField.getText());
            validateValue(initialAnimalEnergy, "Initial Animal Energy");

            int energyToReproduce = Integer.parseInt(energyToReproduceField.getText());
            validateValue(energyToReproduce, "Energy to Reproduce");

            int parentEnergyCost = Integer.parseInt(parentEnergyCostField.getText());
            validateValue(parentEnergyCost, "Parent Energy Cost");

            int minMutations = Integer.parseInt(minMutationsField.getText());
            if(minMutations < 0){throw new IllegalArgumentException("minMutations" + " must be at least 0.");}

            int maxMutations = Integer.parseInt(maxMutationsField.getText());
            if(maxMutations < 0){throw new IllegalArgumentException("maxMutations" + " must be at least 0.");}

            int genomeLength = Integer.parseInt(genomeLengthField.getText());
            validateValue(genomeLength, "Genome Length");

            // Validate fire-related fields only if they are visible
            if (mapVariantComboBox.getValue() == EMapVariant.FIRE) {
                int fireMaxAge = Integer.parseInt(fireMaxAgeField.getText());
                validateValue(fireMaxAge, "Fire Max Age");

                int fireFreq = Integer.parseInt(fireFreqField.getText());
                validateValue(fireFreq, "Fire Frequency");
            }

            // Additional validation for mutations and genome length
            if (minMutations > maxMutations) {
                showError("Invalid Input", "Min Mutations must be less than or equal to Max Mutations.");
                return;
            }
            if (maxMutations >= genomeLength) {
                showError("Invalid Input", "Max Mutations must be less than Genome Length.");
                return;
            }

            // Collect configuration data as a string
            String configuration = String.format(
                    "Map Width: %d\nMap Height: %d\nMap Variant: %s\nInitial Plant Count: %d\n" +
                            "Plant Energy: %d\nDaily Grass Growth: %d\nInitial Animal Count: %d\n" +
                            "Initial Animal Energy: %d\nEnergy to Reproduce: %d\nParent Energy Cost: %d\n" +
                            "Min Mutations: %d\nMax Mutations: %d\nMutation Variant: %s\n" +
                            "Genome Length: %d\nFire Max Age: %d\nFire Frequency: %d",
                    mapWidth, mapHeight, mapVariantComboBox.getValue(), initialPlantCount, plantEnergy,
                    dailyGrassGrowth, initialAnimalCount, initialAnimalEnergy, energyToReproduce,
                    parentEnergyCost, minMutations, maxMutations, mutationVariantComboBox.getValue(),
                    genomeLength,
                    mapVariantComboBox.getValue() == EMapVariant.FIRE ? Integer.parseInt(fireMaxAgeField.getText()) : 0,
                    mapVariantComboBox.getValue() == EMapVariant.FIRE ? Integer.parseInt(fireFreqField.getText()) : 0
            );

            // Open the save configuration window
            openSaveConfigurationWindow(configuration);

        } catch (NumberFormatException e) {
            showError("Invalid Input", "Please enter valid numbers in all fields.");
        } catch (IllegalArgumentException e) {
            showError("Invalid Input", e.getMessage());
        }
    }

    /**
     * Starts the simulation with the current configuration.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void startSimulation(Stage primaryStage) {
        try {
            // Parse and validate every field
            int mapWidth = Integer.parseInt(mapWidthField.getText());
            validateValue(mapWidth, "Map Width");

            int mapHeight = Integer.parseInt(mapHeightField.getText());
            validateValue(mapHeight, "Map Height");

            int initialPlantCount = Integer.parseInt(initialPlantCountField.getText());
            validateValue(initialPlantCount, "Initial Plant Count");

            int plantEnergy = Integer.parseInt(plantEnergyField.getText());
            validateValue(plantEnergy, "Plant Energy");

            int dailyGrassGrowth = Integer.parseInt(dailyGrassGrowthField.getText());
            validateValue(dailyGrassGrowth, "Daily Grass Growth");

            int initialAnimalCount = Integer.parseInt(initialAnimalCountField.getText());
            validateValue(initialAnimalCount, "Initial Animal Count");

            int initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyField.getText());
            validateValue(initialAnimalEnergy, "Initial Animal Energy");

            int energyToReproduce = Integer.parseInt(energyToReproduceField.getText());
            validateValue(energyToReproduce, "Energy to Reproduce");

            int parentEnergyCost = Integer.parseInt(parentEnergyCostField.getText());
            validateValue(parentEnergyCost, "Parent Energy Cost");

            int minMutations = Integer.parseInt(minMutationsField.getText());
            if(minMutations < 0){throw new IllegalArgumentException("minMutations" + " must be at least 0.");}

            int maxMutations = Integer.parseInt(maxMutationsField.getText());
            if(maxMutations < 0){throw new IllegalArgumentException("maxMutations" + " must be at least 0.");}

            int genomeLength = Integer.parseInt(genomeLengthField.getText());
            validateValue(genomeLength, "Genome Length");

            int fireMaxAge = 0;
            int fireFreq = 0;
            if (mapVariantComboBox.getValue() == EMapVariant.FIRE) {
                fireMaxAge = Integer.parseInt(fireMaxAgeField.getText());
                validateValue(fireMaxAge, "Fire Max Age");

                fireFreq = Integer.parseInt(fireFreqField.getText());
                validateValue(fireFreq, "Fire Frequency");
            }
            // Additional validation for mutations and genome length
            if (minMutations > maxMutations) {
                showError("Invalid Input", "Min Mutations must be less than or equal to Max Mutations.");
                return;
            }
            if (maxMutations >= genomeLength) {
                showError("Invalid Input", "Max Mutations must be less than Genome Length.");
                return;
            }

            //Change WorldConfig Builder values
            Builder builder = new Builder();
            builder.mapWidth(mapWidth);
            builder.mapHeight(mapHeight);
            builder.initialPlantCount(initialPlantCount);
            builder.plantEnergy(plantEnergy);
            builder.dailyGrassGrowth(dailyGrassGrowth);
            builder.initialAnimalCount(initialAnimalCount);
            builder.initialAnimalEnergy(initialAnimalEnergy);
            builder.energyToReproduce(energyToReproduce);
            builder.parentEnergyCost(parentEnergyCost);
            builder.minMutations(minMutations);
            builder.maxMutations(maxMutations);
            builder.genomeLength(genomeLength);
            if (mapVariantComboBox.getValue() == EMapVariant.FIRE) {
                builder.fireMaxAge(fireMaxAge);
                builder.fireFreq(fireFreq);
            }
            builder.mutationVariant(mutationVariantComboBox.getValue());
            builder.mapVariant(mapVariantComboBox.getValue());


            // Close the configuration window
            primaryStage.close();

            // Launch the SimulationApp
            SimulationApp simulationApp = new SimulationApp();
            Stage simulationStage = new Stage();
            simulationApp.start(simulationStage);

        } catch (NumberFormatException e) {
            showError("Invalid Input", "Please enter valid numbers in all fields.");
        } catch (IllegalArgumentException e) {
            showError("Invalid Input", e.getMessage());
        } catch (IOException e) {
            showError("Error", "An error occurred while starting the simulation: " + e.getMessage());
        }
    }

    /**
     * Opens a new window to save the configuration to a file.
     *
     * @param configuration The configuration data to save.
     */
    private void openSaveConfigurationWindow(String configuration) {
        // Create a new stage (window)
        Stage saveStage = new Stage();
        saveStage.setTitle("Save Configuration");

        // Create a layout for the new window
        GridPane saveGrid = new GridPane();
        saveGrid.setHgap(10);
        saveGrid.setVgap(10);

        // Add a label and text field for the configuration name
        Label nameLabel = new Label("Configuration Name:");
        TextField nameField = new TextField();
        saveGrid.add(nameLabel, 0, 0);
        saveGrid.add(nameField, 1, 0);

        // Add a Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            String configName = nameField.getText().trim();
            if (configName.isEmpty()) {
                showError("Invalid Input", "Configuration name cannot be empty.");
                return;
            }

            // Save the configuration to a file
            saveConfigurationToFile(configName, configuration);

            // Close the save window
            saveStage.close();
        });
        saveGrid.add(saveButton, 1, 1);

        // Create a scene and set it to the stage
        Scene saveScene = new Scene(saveGrid, 300, 100);
        saveStage.setScene(saveScene);

        // Show the stage
        saveStage.show();
    }

    /**
     * Saves the configuration data to a file.
     *
     * @param configName    The name of the configuration.
     * @param configuration The configuration data to save.
     */
    private void saveConfigurationToFile(String configName, String configuration) {
        String fileName = "configs/" + configName + ".txt"; // Save in a "configs" folder
        try {
            // Ensure the "configs" directory exists
            File configDir = new File("configs");
            if (!configDir.exists()) {
                configDir.mkdir();
            }

            // Write the configuration to the file
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(configuration);
                showError("Success", "Configuration saved to " + fileName);
            }
        } catch (IOException e) {
            showError("Error", "Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Opens a new window to load a saved configuration.
     */
    private void openLoadConfigurationWindow() {
        // Create a new stage (window)
        Stage loadStage = new Stage();
        loadStage.setTitle("Load Configuration");

        // Create a layout for the new window
        GridPane loadGrid = new GridPane();
        loadGrid.setHgap(10);
        loadGrid.setVgap(10);

        // Add a label and combo box for the configuration files
        Label configLabel = new Label("Select Configuration:");
        ComboBox<String> configComboBox = new ComboBox<>();
        configComboBox.getItems().addAll(listSavedConfigurations());
        loadGrid.add(configLabel, 0, 0);
        loadGrid.add(configComboBox, 1, 0);

        // Add a Load button
        Button loadButton = new Button("Load");
        loadButton.setOnAction(event -> {
            String selectedFile = configComboBox.getValue();
            if (selectedFile == null || selectedFile.isEmpty()) {
                showError("Invalid Input", "Please select a configuration file.");
                return;
            }

            // Load the configuration from the selected file
            loadConfigurationFromFile("configs/" + selectedFile);

            // Close the load window
            loadStage.close();
        });
        loadGrid.add(loadButton, 1, 1);

        // Create a scene and set it to the stage
        Scene loadScene = new Scene(loadGrid, 300, 100);
        loadStage.setScene(loadScene);

        // Show the stage
        loadStage.show();
    }

    /**
     * Loads the configuration data from a file and populates the input fields.
     *
     * @param fileName The name of the configuration file to load.
     */
    private void loadConfigurationFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // Populate the corresponding input field
                    switch (key) {
                        case "Map Width" -> mapWidthField.setText(value);
                        case "Map Height" -> mapHeightField.setText(value);
                        case "Map Variant" -> mapVariantComboBox.setValue(EMapVariant.valueOf(value));
                        case "Initial Plant Count" -> initialPlantCountField.setText(value);
                        case "Plant Energy" -> plantEnergyField.setText(value);
                        case "Daily Grass Growth" -> dailyGrassGrowthField.setText(value);
                        case "Initial Animal Count" -> initialAnimalCountField.setText(value);
                        case "Initial Animal Energy" -> initialAnimalEnergyField.setText(value);
                        case "Energy to Reproduce" -> energyToReproduceField.setText(value);
                        case "Parent Energy Cost" -> parentEnergyCostField.setText(value);
                        case "Min Mutations" -> minMutationsField.setText(value);
                        case "Max Mutations" -> maxMutationsField.setText(value);
                        case "Mutation Variant" -> mutationVariantComboBox.setValue(EMutationVariant.valueOf(value));
                        case "Genome Length" -> genomeLengthField.setText(value);
                        case "Fire Max Age" -> {
                            if (mapVariantComboBox.getValue() == EMapVariant.FIRE) {
                                fireMaxAgeField.setText(value);
                            }
                        }
                        case "Fire Frequency" -> {
                            if (mapVariantComboBox.getValue() == EMapVariant.FIRE) {
                                fireFreqField.setText(value);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            showError("Error", "Failed to load configuration: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Error", "Invalid value in configuration file: " + e.getMessage());
        }
    }

    /**
     * Lists all saved configuration files in the "configs" directory.
     *
     * @return A list of configuration file names.
     */
    private List<String> listSavedConfigurations() {
        List<String> configFiles = new ArrayList<>();
        File configDir = new File("configs");
        if (configDir.exists() && configDir.isDirectory()) {
            File[] files = configDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    configFiles.add(file.getName());
                }
            }
        }
        return configFiles;
    }

    /**
     * Displays an error message dialog.
     *
     * @param title   The title of the error dialog.
     * @param message The error message to display.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}