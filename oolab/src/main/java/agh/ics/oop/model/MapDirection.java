package agh.ics.oop.model;

public enum MapDirection {
    NORTH("1", new Vector2d(0, 1)),
    NORTHEAST("2", new Vector2d(1, 1)),
    EAST("3", new Vector2d(1, 0)),
    SOUTHEAST("4", new Vector2d(1, -1)),
    SOUTH("5", new Vector2d(0, -1)),
    SOUTHWEST("6", new Vector2d(-1, -1)),
    WEST("7", new Vector2d(-1, 0)),
    NORTHWEST("8", new Vector2d(-1, 1));

    private final String name;
    private final Vector2d unitVector;

    MapDirection(String name, Vector2d unitVector) {
        this.name = name;
        this.unitVector = unitVector;
    }

    @Override
    public String toString() {
        return name;
    }

    public MapDirection next() {
        MapDirection[] directions = MapDirection.values();
        return directions[(this.ordinal() + 1) % directions.length];
    }

    public MapDirection useGene(int x) {
        MapDirection[] directions = MapDirection.values();
        return directions[(this.ordinal() + x) % directions.length];
    }

    public MapDirection previous() {
        MapDirection[] directions = MapDirection.values();
        return directions[(this.ordinal() + directions.length - 1) % directions.length];
    }

    public Vector2d toUnitVector() {
        return unitVector;
    }

    public MapDirection opposite() {
        return MapDirection.values()[(this.ordinal() + 4) % MapDirection.values().length];
    }
}
