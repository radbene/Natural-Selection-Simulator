package agh.ics.oop;

import agh.ics.oop.model.MoveDirections;
import agh.ics.oop.model.Vector2d;

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

        System.out.println("system zakończył działanie");
    }

    //static public void run(String[] args)
    public static void run(MoveDirections[] moves)
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
        for (int i = 0; i < moves.length; i++) {
            switch (moves[i]) {
                /*case "f" -> System.out.println("zwierzak idzie do przodu");
                case "b" -> System.out.println("zwierzak idzie do tyłu");
                case "r" -> System.out.println("zwierzak skręca w prawo");
                case "l" -> System.out.println("zwierzak skręca w lewo");
                 */
                case MoveDirections.FORWARD -> System.out.println("zwierzak idzie do przodu");
                case MoveDirections.BACKWARD -> System.out.println("zwierzak idzie do tyłu");
                case MoveDirections.RIGHT -> System.out.println("zwierzak skręca w prawo");
                case MoveDirections.LEFT -> System.out.println("zwierzak skręca w lewo");
            }
        }
        System.out.println("Stop");
    }
}
