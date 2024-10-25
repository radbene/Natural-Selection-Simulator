package agh.ics.oop;

import agh.ics.oop.model.MoveDirections;

import java.util.Arrays;

public class OptionsParser  {
    public static MoveDirections[] parse(String[] args) {
            MoveDirections[] Moves = new MoveDirections[args.length];
            int i = 0;
            int a = 0;
            while(i < args.length) {
                switch(args[i]){
                    case "f":
                        Moves[a] = MoveDirections.FORWARD;
                        a++;
                        break;
                    case "b":
                        Moves[a] = MoveDirections.BACKWARD;
                        a++;
                        break;
                    case "l":
                        Moves[a] = MoveDirections.LEFT;
                        a++;
                        break;
                    case "r":
                        Moves[a] = MoveDirections.RIGHT;
                        a++;
                        break;
                    default:
                        break;
                }
                i++;
            }
            return Arrays.copyOfRange(Moves, 0, a);
    }
}
