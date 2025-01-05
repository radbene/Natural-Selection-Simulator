package agh.ics.oop.model;

public class Globe {
    private final Vector2d size; // 0,0 to size.x, size.y

    public Globe(Vector2d size) {
        this.size = size;
    }

    public Move nextPosition(Move mv) {
        Vector2d newPos = mv.getPosition().add(mv.getDirection());
        MapDirection newDir = mv.getOrientation();
        if(newPos.getY() < 0) {
            return new Move(new Vector2d(newPos.getX(), 0), new Vector2d(0, 0), newDir.opposite());
        }
        if(newPos.getY() > size.getY()) {
            return new Move(new Vector2d(newPos.getX(), size.getY()), new Vector2d(0, 0), newDir.opposite());
        }
        if(newPos.getX() < 0) {
            return new Move(new Vector2d(size.getX(), newPos.getY()), new Vector2d(0, 0), newDir);
        }
        if(newPos.getX() > size.getX()) {
            return new Move(new Vector2d(0, newPos.getY()), new Vector2d(0, 0), newDir);
        }
        return new Move(newPos, new Vector2d(0, 0), newDir);
    }
}
