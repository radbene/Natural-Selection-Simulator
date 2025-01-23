package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationConfiguration;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.variants.EMapVariant;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    private Simulation simulation;
    private SimulationEngine engine;
    private boolean isPaused = false;

    @FXML
    private GridPane mapGrid;

    @FXML
    private TextField moveListTextField;

    @FXML
    private Label moveDescriptionLabel;

    @FXML
    private VBox statsContainer;

    @FXML
    private LineChart<Number, Number> animalsGrassChart;

    @FXML
    private LineChart<Number, Number> energyLifespanChart;

    @FXML
    private VBox trackedAnimalContainer;

    @FXML
    private Label trackedAnimalIdLabel;

    @FXML
    private Label trackedAnimalEnergyLabel;

    @FXML
    private Label trackedAnimalLifespanLabel;

    @FXML
    private Label trackedAnimalChildrenLabel;

    @FXML
    private Label trackedAnimalPositionLabel;

    @FXML
    private Label trackedAnimalDirectionLabel;

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int mapWidth;
    private int mapHeight;
    private final int width = 50;
    private final int height = 50;

    // Chart Series
    private XYChart.Series<Number, Number> animalsSeries;
    private XYChart.Series<Number, Number> grassSeries;
    private XYChart.Series<Number, Number> energySeries;
    private XYChart.Series<Number, Number> lifespanSeries;

    // Maximum number of data points to display
    private static final int MAX_DATA_POINTS = 100;

    // Tracked Animal Reference
    private Animal trackedAnimal = null;

    @FXML
    private Button pauseResumeButton;

    @FXML
    private void initialize() {
        initializeCharts();
        initializeTrackedAnimalUI();
    }

    private void initializeTrackedAnimalUI() {
        clearTrackedAnimalUI();
    }

    private void clearTrackedAnimalUI() {
        trackedAnimalIdLabel.setText("ID: ");
        trackedAnimalEnergyLabel.setText("Energy: ");
        trackedAnimalLifespanLabel.setText("Lifespan: ");
        trackedAnimalChildrenLabel.setText("Children: ");
        trackedAnimalPositionLabel.setText("Position: ");
        trackedAnimalDirectionLabel.setText("Direction: ");
    }

    @FXML
    public void handlePauseSimulation() {
        if (engine == null) {
            System.err.println("SimulationEngine is not initialized.");
            return;
        }
        isPaused = !isPaused;
        if (isPaused) {
            engine.pause();
            pauseResumeButton.setText("Resume Simulation");
            System.out.println("Simulation paused.");
        } else {
            engine.resume();
            pauseResumeButton.setText("Pause Simulation");
            System.out.println("Simulation resumed.");
        }
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    private void initializeCharts() {
        // Configure Animals vs Grass Chart
        animalsGrassChart.getXAxis().setLabel("Day");
        animalsGrassChart.getYAxis().setLabel("Number of Animals/Grass");
        animalsGrassChart.setTitle("Animals vs Grass");
        // The stylesheet will handle additional styles

        // Initialize Series
        animalsSeries = new XYChart.Series<>();
        animalsSeries.setName("Animals");
        grassSeries = new XYChart.Series<>();
        grassSeries.setName("Grass");

        // Add Series to Chart
        animalsGrassChart.getData().addAll(animalsSeries, grassSeries);

        // Configure Energy vs Lifespan Chart
        energyLifespanChart.getXAxis().setLabel("Day");
        energyLifespanChart.getYAxis().setLabel("Avg Energy/Average Lifespan");
        energyLifespanChart.setTitle("Avg Energy vs Avg Lifespan");

        // Initialize Series
        energySeries = new XYChart.Series<>();
        energySeries.setName("Avg Animal Energy");
        lifespanSeries = new XYChart.Series<>();
        lifespanSeries.setName("Avg Animal Lifespan");

        // Add Series to Chart
        energyLifespanChart.getData().addAll(energySeries, lifespanSeries);
    }

    private void updateCharts(int day, int animals, int grass, double avgEnergy, double avgLifespan) {
        // Add data to Animals vs Grass Chart
        animalsSeries.getData().add(new XYChart.Data<>(day, animals));
        grassSeries.getData().add(new XYChart.Data<>(day, grass));

        // Add data to Energy vs Lifespan Chart
        energySeries.getData().add(new XYChart.Data<>(day, avgEnergy));
        lifespanSeries.getData().add(new XYChart.Data<>(day, avgLifespan));

        // Limit the number of data points to MAX_DATA_POINTS
        if (animalsSeries.getData().size() > MAX_DATA_POINTS) {
            animalsSeries.getData().remove(0);
            grassSeries.getData().remove(0);
            energySeries.getData().remove(0);
            lifespanSeries.getData().remove(0);
        }
    }

    private void updateStatsDisplay(Simulation simulation) {
        Map<String, Object> stats = simulation.getStats();
        statsContainer.getChildren().clear();
        statsContainer.setSpacing(5);

        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            HBox statLine = new HBox();
            statLine.setSpacing(10);

            Label keyLabel = new Label(entry.getKey() + ":");
            keyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label valueLabel = new Label(entry.getValue().toString());
            valueLabel.setStyle("-fx-font-size: 14px;");

            statLine.getChildren().addAll(keyLabel, valueLabel);
            statsContainer.getChildren().add(statLine);
        }
    }

    public void updateBounds() {
        xMin = map.getCurrentBounds().lowerLeft().getX();
        yMin = map.getCurrentBounds().lowerLeft().getY();
        xMax = map.getCurrentBounds().upperRight().getX();
        yMax = map.getCurrentBounds().upperRight().getY();
        mapWidth = xMax - xMin + 1;
        mapHeight = yMax - yMin + 1;
    }

    public void columnsFunction() {
        for (int i = 0; i < mapWidth; i++) {
            Label label = new Label(Integer.toString(xMin + i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i + 1, 0);
        }
    }

    public void rowsFunction() {
        for (int i = 0; i < mapHeight; i++) {
            Label label = new Label(Integer.toString(yMax - i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i + 1);
        }
    }

    public void addElements() {
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i, j);

                if (map.isOccupied(pos)) {
                    List<WorldElement> elementsAtPos = map.objectAt(pos);

                    // Use the first element as the representative for this position
                    WorldElement representativeElement = elementsAtPos.getFirst();
                    WorldElementBox elementBox = new WorldElementBox(representativeElement, pos.toString());

                    // Set click event for the representative element
                    elementBox.getContainer().setOnMouseClicked(event -> {
                        if (isPaused && event.getButton() == MouseButton.PRIMARY) {
                            if (representativeElement instanceof Animal) {
                                setTrackedAnimal((Animal) representativeElement);
                            }
                        }
                    });

                    // Add the representative element to the grid
                    mapGrid.add(elementBox.getContainer(), i - xMin + 1, yMax - j + 1);
                } else {
                    // Handle empty positions
                    Label emptyLabel = new Label(" ");
                    emptyLabel.setOnMouseClicked(event -> {
                        if (isPaused && event.getButton() == MouseButton.PRIMARY) {
                            clearTrackedAnimal();
                        }
                    });
                    mapGrid.add(emptyLabel, i - xMin + 1, yMax - j + 1);
                }

                // Center the content in the grid cell
                GridPane.setHalignment(mapGrid.getChildren().getLast(), HPos.CENTER);
            }
        }
    }

    private void drawMap() {
        updateBounds();
        columnsFunction();
        rowsFunction();
        addElements();
        mapGrid.setGridLinesVisible(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().clear();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap map, String message) {
        setWorldMap(map);
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            moveDescriptionLabel.setText(message);
            updateStatsDisplay(simulation);

            if (simulation != null) {
                int currentDay = (Integer) simulation.getStats().get("Epoch");
                int animals = (Integer) simulation.getStats().get("Total Animals");
                int grass = (Integer) simulation.getStats().get("Total Grass");
                double avgEnergy = (Double) simulation.getStats().get("Average Energy");
                double avgLifespan = (Double) simulation.getStats().get("Average Lifespan");

                updateCharts(currentDay, animals, grass, avgEnergy, avgLifespan);
            }

            if (trackedAnimal != null) {
                if (!trackedAnimal.isDead()) {
                    updateTrackedAnimalUI(trackedAnimal);
                } else {
                    showTrackedAnimalDeathNotification();
                    clearTrackedAnimal();
                }
            }
        });
    }

    @FXML
    private void startSimulation() {
        WorldConfig.Builder builder = new WorldConfig.Builder();
        WorldConfig config = builder.build();
        Simulation sim = new Simulation(config);
        this.simulation = sim;
        sim.addObserver(this);
        this.engine = new SimulationEngine(List.of(sim));
        new Thread(engine::run).start();
    }

    @FXML
    private void newGame() {
        SimulationConfiguration simulationConfig = new SimulationConfiguration();
        try {
            simulationConfig.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTrackedAnimal(Animal animal) {
        this.trackedAnimal = animal;
        updateTrackedAnimalUI(animal);
    }

    private void clearTrackedAnimal() {
        this.trackedAnimal = null;
        clearTrackedAnimalUI();
    }

    private void updateTrackedAnimalUI(Animal animal) {
        trackedAnimalIdLabel.setText("ID: " + animal.getId());
        trackedAnimalEnergyLabel.setText("Energy: " + animal.getEnergy());
        trackedAnimalLifespanLabel.setText("Lifespan: " + animal.getDaysLived());
        // FIXME: Add method
        trackedAnimalChildrenLabel.setText("Children: " + animal.getChildren());
        trackedAnimalPositionLabel.setText("Position: " + animal.getPosition().toString());
        trackedAnimalDirectionLabel.setText("Direction: " + animal.getDirection().toString());
    }

    private void showTrackedAnimalDeathNotification() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Animal Died");
        alert.setHeaderText(null);
        alert.setContentText("The tracked animal has died.");
        alert.showAndWait();
    }
}
