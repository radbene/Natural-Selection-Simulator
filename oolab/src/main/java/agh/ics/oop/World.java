package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;

public class World {

    public static void main(String[] args)
    {
        // WorldConfig.Builder builder = new WorldConfig.Builder();
        // WorldConfig config = builder.build();
        // Simulation sim = new Simulation(config);
        // sim.run();
        Application.launch(SimulationApp.class, args);
    }
}
