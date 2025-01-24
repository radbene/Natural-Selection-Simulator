package agh.ics.oop;

import javafx.application.Application;

public class WorldGUI {
    public static void main(String[] args) {
        System.out.println("System wystartowal");
        try {
            Application.launch(SimulationConfiguration.class, args);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        System.out.println("System zakonczyl dzialanie");
    }
}
