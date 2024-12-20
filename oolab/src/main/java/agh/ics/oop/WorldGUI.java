package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.List;
import java.util.ArrayList;



public class WorldGUI {

    public static void main(String[] args){
        System.out.println("system wystartowal");
        try {
            Application.launch(SimulationApp.class, args);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("system zakonczyl dzialanie");
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
