package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationConfiguration;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.variants.EMapVariant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap map;
    private Simulation simulation;
    private SimulationEngine engine;
    private boolean isPaused = false;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label moveDescriptionLabel;

    @FXML
    private VBox statsContainer;

    @FXML
    private LineChart<Number, Number> animalsGrassChart;

    @FXML
    private LineChart<Number, Number> energyLifespanChart;

    @FXML
    private Label trackedAnimalIdLabel;

    @FXML
    private Label trackedAnimalEnergyLabel;

    @FXML
    private Label trackedAnimalGenomeLabel;

    @FXML
    private Label trackedAnimalCurGenLabel;

    @FXML
    private Label trackedAnimalPlantsEatenLabel;

    @FXML
    private Label trackedAnimalChildrenLabel;

    @FXML
    private Label trackedAnimalDescendantsLabel;

    @FXML
    private Label trackedAnimalAgeLabel;

    @FXML
    private Label trackedAnimalPositionLabel;

    @FXML
    private CheckBox dominantGenomeCheckBox;

    @FXML
    private CheckBox equatorCheckBox;

    @FXML
    private Button pauseResumeButton;

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int mapWidth;
    private int mapHeight;
    private final int width = 50;
    private final int height = 50;

    private XYChart.Series<Number, Number> animalsSeries;
    private XYChart.Series<Number, Number> grassSeries;
    private XYChart.Series<Number, Number> energySeries;
    private XYChart.Series<Number, Number> lifespanSeries;

    private static final int MAX_DATA_POINTS = 100;

    private Animal trackedAnimal = null;

    @FXML
    private ScrollPane mapScrollPane;

    @FXML
    private void initialize() {
        initializeCharts();
        initializeTrackedAnimalUI();

        mapScrollPane.setPannable(true);
        statsContainer.setMinHeight(200);
        statsContainer.setMinWidth(200);
        statsContainer.setPrefWidth(500);
        statsContainer.setPrefHeight(500);
    }

    private void initializeTrackedAnimalUI() {
        clearTrackedAnimalUI();
    }

    private void clearTrackedAnimalUI() {
        trackedAnimalIdLabel.setText("ID: ");
        trackedAnimalEnergyLabel.setText("Energy: ");
        trackedAnimalGenomeLabel.setText("Genome: ");
        trackedAnimalCurGenLabel.setText("Current Gen: ");
        trackedAnimalPlantsEatenLabel.setText("Plants Eaten: ");
        trackedAnimalChildrenLabel.setText("Children: ");
        trackedAnimalDescendantsLabel.setText("Descendants: ");
        trackedAnimalAgeLabel.setText("Age: ");
        trackedAnimalPositionLabel.setText("Position: ");
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

    @FXML
    private void toggleDominantGenome() {
        if (isPaused) {
            if (dominantGenomeCheckBox.isSelected()) {
                highlightDominantGenomes();
            } else {
                removeHighlight("highlight-dominant-genome");
            }
        }
    }

    @FXML
    private void toggleEquator() {
        if (isPaused) {
            if (equatorCheckBox.isSelected()) {
                highlightEquator();
            } else {
                removeHighlight("highlight-equator");
            }
        }
    }

    private void highlightDominantGenomes() {
        String dominantGenome = map.wObserver.findDominantGenome().toString();

        Platform.runLater(() -> {
            for (Region cell : getMapGridCells()) {
                WorldElementBox elementBox = getWorldElementBox(cell);
                if (elementBox == null) {
                    continue;
                }
                if (elementBox.getWorldElement() instanceof Animal animal) {
                    String cellGenome = animal.getGenome().toString();
                    if (dominantGenome.equals(cellGenome)) {
                        cell.getStyleClass().add("highlight-dominant-genome");
                    }
                }
            }
        });
    }

    private void highlightEquator() {
        Platform.runLater(() -> {
            for (Region cell : getMapGridCells()) {
                Vector2d position = getCellPosition(cell);
                if (isEquator(position) && position.getX() == 0) {
                    cell.getStyleClass().add("highlight-equator");
                }
            }
        });
    }

    private void removeHighlight(String styleClass) {
        Platform.runLater(() -> {
            for (Region cell : getMapGridCells()) {
                cell.getStyleClass().remove(styleClass);
            }
        });
    }

    private List<Region> getMapGridCells() {
        return mapGrid.getChildren().stream()
                .filter(node -> node instanceof Region)
                .map(node -> (Region) node)
                .toList();
    }

    private WorldElementBox getWorldElementBox(Region cell) {
        Object data = cell.getUserData();
        if (data instanceof WorldElementBox elementBox) {
            return elementBox;
        }
        return null;
    }

    private Vector2d getCellPosition(Region cell) {
        WorldElementBox elementBox = getWorldElementBox(cell);
        if (elementBox != null) {
            return elementBox.getPosition();
        }
        Integer columnIndex = GridPane.getColumnIndex(cell);
        Integer rowIndex = GridPane.getRowIndex(cell);
        if (columnIndex == null) columnIndex = 0;
        if (rowIndex == null) rowIndex = 0;
        int x = xMin + columnIndex;
        int y = yMax - rowIndex + 1;
        return new Vector2d(x, y);
    }

    private boolean isEquator(Vector2d position) {
        return map.equator.contains(position);
    }

    public void setWorldMap(WorldMap map) {
        this.map = (AbstractWorldMap) map;
    }

    private void initializeCharts() {
        animalsGrassChart.getXAxis().setLabel("Day");
        animalsGrassChart.getYAxis().setLabel("Number of Animals/Grass");
        animalsGrassChart.setTitle("Animals vs Grass");

        animalsSeries = new XYChart.Series<>();
        animalsSeries.setName("Animals");
        grassSeries = new XYChart.Series<>();
        grassSeries.setName("Grass");

        animalsGrassChart.getData().addAll(animalsSeries, grassSeries);

        energyLifespanChart.getXAxis().setLabel("Day");
        energyLifespanChart.getYAxis().setLabel("Avg Energy/Average Lifespan");
        energyLifespanChart.setTitle("Avg Energy vs Avg Lifespan");

        energySeries = new XYChart.Series<>();
        energySeries.setName("Avg Animal Energy");
        lifespanSeries = new XYChart.Series<>();
        lifespanSeries.setName("Avg Animal Lifespan");

        energyLifespanChart.getData().addAll(energySeries, lifespanSeries);
    }

    private void updateCharts(int day, int animals, int grass, double avgEnergy, double avgLifespan) {
        animalsSeries.getData().add(new XYChart.Data<>(day, animals));
        grassSeries.getData().add(new XYChart.Data<>(day, grass));

        energySeries.getData().add(new XYChart.Data<>(day, avgEnergy));
        lifespanSeries.getData().add(new XYChart.Data<>(day, avgLifespan));

        // if (animalsSeries.getData().size() > MAX_DATA_POINTS) {
        //     animalsSeries.getData().remove(0);
        //     grassSeries.getData().remove(0);
        //     energySeries.getData().remove(0);
        //     lifespanSeries.getData().remove(0);
        // }
    }

    private void updateStatsDisplay(Simulation simulation) {
        Map<String, Object> stats = simulation.getStats();

        saveStatsToCSV(simulation);

        statsContainer.getChildren().clear();
        statsContainer.setSpacing(5);

        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            HBox statLine = new HBox();
            statLine.setSpacing(10);

            Label keyLabel = new Label(entry.getKey() + ":");
            keyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            String value = (entry.getValue() != null) ? entry.getValue().toString() : "Brak danych";
            Label valueLabel = new Label(value);
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

                    if (elementsAtPos != null && !elementsAtPos.isEmpty()) {
                        WorldElement representativeElement = elementsAtPos.get(0);
                        WorldElementBox elementBox = new WorldElementBox(representativeElement, pos.toString());
                        elementBox.getContainer().setUserData(elementBox);

                        mapGrid.add(elementBox.getContainer(), i - xMin + 1, yMax - j + 1);

                        elementBox.getContainer().setOnMouseClicked(event -> {
                            if (isPaused && event.getButton() == MouseButton.PRIMARY) {
                                if (representativeElement instanceof Animal) {
                                    setTrackedAnimal((Animal) representativeElement);
                                }
                            }
                        });
                    }
                } else {
                    Label emptyLabel = new Label(" ");
                    emptyLabel.setOnMouseClicked(event -> {
                        if (isPaused && event.getButton() == MouseButton.PRIMARY) {
                            clearTrackedAnimal();
                        }
                    });
                    emptyLabel.setUserData(null);
                    mapGrid.add(emptyLabel, i - xMin + 1, yMax - j + 1);
                }

                GridPane.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size() - 1), HPos.CENTER);
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

        String csvFile = "simulation_stats.csv";
        try {
            if (Files.exists(Paths.get(csvFile))) {
                Files.delete(Paths.get(csvFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        trackedAnimalGenomeLabel.setText("Genome: " + animal.getGenome().toString());
        trackedAnimalCurGenLabel.setText("Current Gen: " + animal.getCurrentGen());
        trackedAnimalEnergyLabel.setText("Energy: " + animal.getEnergy());
        trackedAnimalPlantsEatenLabel.setText("Plants Eaten: " + animal.getPlantsEaten());
        trackedAnimalChildrenLabel.setText("Children: " + animal.getChildren());
        trackedAnimalDescendantsLabel.setText("Descendants: " + animal.getDescendants());
        trackedAnimalAgeLabel.setText("Age: " + animal.getDaysLived());
        trackedAnimalPositionLabel.setText("Position: " + animal.getPosition().toString());
    }

    private void saveStatsToCSV(Simulation simulation) {
        String csvFile = "simulation_stats.csv";
        boolean fileExists = new java.io.File(csvFile).exists();

        try (FileWriter writer = new FileWriter(csvFile, true)) { // true = append mode
            Map<String, Object> stats = simulation.getStats();

            if (!fileExists) {
                writer.append("Timestamp,");

                for (String key : stats.keySet()) {
                    writer.append(key).append(",");
                }

                writer.append("TrackedAnimalID,")
                        .append("TrackedAnimalEnergy,")
                        .append("TrackedAnimalLifespan,")
                        .append("TrackedAnimalChildren,")
                        .append("TrackedAnimalPosition,")
                        .append("TrackedAnimalDirection")
                        .append("\n");
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.append(timestamp).append(",");

            for (Object value : stats.values()) {
                writer.append(value != null ? value.toString() : "").append(",");
            }

            if (trackedAnimal != null) {
                writer.append(String.valueOf(trackedAnimal.getId())).append(",") // TrackedAnimalID
                        .append(String.valueOf(trackedAnimal.getEnergy())).append(",") // TrackedAnimalEnergy
                        .append(String.valueOf(trackedAnimal.getDaysLived())).append(",") // TrackedAnimalLifespan
                        .append(String.valueOf(trackedAnimal.getChildren())).append(",") // TrackedAnimalChildren
                        .append("\"").append(trackedAnimal.getPosition().toString()).append("\"").append(",") // TrackedAnimalPosition (enclosed in quotes)
                        .append(trackedAnimal.getDirection().toString()); // TrackedAnimalDirection
            } else {
                writer.append(",,,,,,");
            }
            writer.append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTrackedAnimalDeathNotification() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Animal Died");
        alert.setHeaderText(null);
        alert.setContentText("The tracked animal has died.");
        alert.show();
    }
}
