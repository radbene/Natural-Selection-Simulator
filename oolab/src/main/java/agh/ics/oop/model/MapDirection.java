package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;
    public String toString(){
        return switch(this){
            case NORTH -> "Północ";
            case EAST -> "Wschód";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
        };
    }

    public MapDirection next(MapDirection direction){
            return MapDirection.values()[(direction.ordinal() + 1) % 4];
    }

    public MapDirection previous(MapDirection direction) {
        return MapDirection.values()[(direction.ordinal() + 3) % 4];
    }

    public Vector2d toUnitVector(MapDirection direction){
        Vector2d vector2d_north = new Vector2d(0, 1);
        Vector2d vector2d_east = new Vector2d(1, 0);
        Vector2d vector2d_south = new Vector2d(0, -1);
        Vector2d vector2d_west = new Vector2d(-1, 0);
        return switch (direction) {
            case NORTH -> vector2d_north;
            case EAST -> vector2d_east;
            case SOUTH -> vector2d_south;
            case WEST -> vector2d_west;
        };
    }
}
