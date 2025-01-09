package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {

    public static void main(String[] args)
    {
        WorldConfig.Builder builder = new WorldConfig.Builder();
        WorldConfig config = builder.build();
        Simulation sim = new Simulation(config);
        sim.run();
    }
}
