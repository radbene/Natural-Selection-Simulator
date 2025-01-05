package agh.ics.oop.model;

// TODO: rework enum to work on indexes

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;
    public String toString(){
        return switch(this){
            case NORTH -> "Północ";
            case NORTHWEST -> "Północny zachód";
            case EAST -> "Wschód";
            case NORTHEAST -> "Północny wschód";
            case SOUTH -> "Południe";
            case SOUTHEAST -> "Południowy wschód";
            case WEST -> "Zachód";
            case SOUTHWEST -> "Południowy zachód";
        };
    }

    public MapDirection next(MapDirection direction){
            return MapDirection.values()[(direction.ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection previous(MapDirection direction) {
        return MapDirection.values()[(direction.ordinal() + MapDirection.values().length-1) % MapDirection.values().length];
    }

    public Vector2d toUnitVector(){
        Vector2d vector2d_north = new Vector2d(0, 1);
        Vector2d vector2d_east = new Vector2d(1, 0);
        Vector2d vector2d_south = new Vector2d(0, -1);
        Vector2d vector2d_west = new Vector2d(-1, 0);
        Vector2d vector2d_NE = new Vector2d(1, 1);
        Vector2d vector2d_SE = new Vector2d(1, -1);
        Vector2d vector2d_SW = new Vector2d(-1, -1);
        Vector2d vector2d_NW = new Vector2d(-1, 1);
        return switch (this) {
            case NORTH -> vector2d_north;
            case EAST -> vector2d_east;
            case SOUTH -> vector2d_south;
            case WEST -> vector2d_west;
            case NORTHEAST -> vector2d_NE;
            case SOUTHEAST -> vector2d_SE;
            case SOUTHWEST -> vector2d_SW;
            case NORTHWEST -> vector2d_NW;
        };
    }

    public MapDirection opposite(){
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case NORTHEAST -> SOUTHWEST;
            case SOUTHEAST -> NORTHWEST;
            case SOUTHWEST -> NORTHEAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }
}
