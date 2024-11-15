package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;
import java.util.List;

public class OptionsParser  {
    //public static MoveDirections[] parse(String[] args) {
    public static List<MoveDirection> parse(String[] args) {
            MoveDirection[] Moves = new MoveDirection[args.length];
            int i = 0;
            int a = 0;
            while(i < args.length) {
                switch(args[i]){
                    case "f":
                        Moves[a] = MoveDirection.FORWARD;
                        a++;
                        break;
                    case "b":
                        Moves[a] = MoveDirection.BACKWARD;
                        a++;
                        break;
                    case "l":
                        Moves[a] = MoveDirection.LEFT;
                        a++;
                        break;
                    case "r":
                        Moves[a] = MoveDirection.RIGHT;
                        a++;
                        break;
                    default:
                        break;
                }
                i++;
            }
            //return Arrays.copyOfRange(Moves, 0, a);
        List<MoveDirection> list_of_moves = Arrays.asList(Arrays.copyOfRange(Moves, 0, a));
            return list_of_moves;
    }
}
