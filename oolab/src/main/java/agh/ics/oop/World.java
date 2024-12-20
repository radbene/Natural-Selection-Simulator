package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.List;
import java.util.ArrayList;

public class World {

    public static void main(String[] args)
    {
        System.out.println("system wystartował");
        //run(args)
        run(OptionsParser.parse(args));

        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        Animal spuchacz = new Animal();
        spuchacz.toString();
        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
            List<Simulation> simulations = new ArrayList<>();
            //for (int i = 0; i < 100; i++){
                //AbstractWorldMap map1 = new RectangularMap(5,5);
                AbstractWorldMap map2 = new GrassField(30);
                //simulations.add(new Simulation(positions,directions,map1));
                simulations.add(new Simulation(positions,directions,map2));
            //}
            //map.addObserver(new ConsoleMapDisplay());
            //Simulation simulation = new Simulation(positions, directions,map);
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsync();
            //engine.runAsync();
            //engine.runAsyncInThreadPool();
            //simulation.run();
            //Application.launch(SimulationApp.class, args);


            engine.awaitSimulationsEnd();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        //GrassField field = new GrassField(10);
        //field.addObserver(new ConsoleMapDisplay());
        //field.toString();
        System.out.println("system zakończył działanie");
    }

    //static public void run(String[] args)
    //public static void run(MoveDirections[] moves)
    public static void run(List<MoveDirection> moves)
    {
        //System.out.println("zwierzak idzie do przodu");
        /*for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i < args.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("");
        */
        System.out.println("Start");
        //for (int i = 0; i < moves.length; i++) {
            //switch (moves[i]) {
        for (int i = 0; i < moves.size(); i++) {
            switch (moves.get(i)) {
                /*case "f" -> System.out.println("zwierzak idzie do przodu");
                case "b" -> System.out.println("zwierzak idzie do tyłu");
                case "r" -> System.out.println("zwierzak skręca w prawo");
                case "l" -> System.out.println("zwierzak skręca w lewo");
                 */
                case FORWARD -> System.out.println("zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("zwierzak skręca w prawo");
                case LEFT -> System.out.println("zwierzak skręca w lewo");
            }
        }
        System.out.println("Stop");
    }
}
