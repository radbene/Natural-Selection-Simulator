package agh.ics.oop.model;

public abstract class Globe implements WorldMap {
    // FIXME: This class should be abstract
    private Vector2d lowerLeft, upperRight; // 0,0 to size.x, size.y

    public Move nextPosition(Move mv) {
        Vector2d newPos = mv.getPosition().add(mv.getDirection());
        MapDirection newDir = mv.getOrientation();
        if(newPos.getY() < 0) {
            return new Move(new Vector2d(newPos.getX(), 0), new Vector2d(0, 0), newDir.opposite());
        }
        if(newPos.getY() > upperRight.getY()) {
            return new Move(new Vector2d(newPos.getX(), upperRight.getY()), new Vector2d(0, 0), newDir.opposite());
        }
        if(newPos.getX() < 0) {
            return new Move(new Vector2d(upperRight.getX(), newPos.getY()), new Vector2d(0, 0), newDir);
        }
        if(newPos.getX() > upperRight.getX()) {
            return new Move(new Vector2d(0, newPos.getY()), new Vector2d(0, 0), newDir);
        }
        return new Move(newPos, new Vector2d(0, 0), newDir);
    }
}
