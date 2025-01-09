package agh.ics.oop.model;

public enum MapDirection {
    NORTH("N ", new Vector2d(0, 1)),
    NORTHEAST("NE", new Vector2d(1, 1)),
    EAST("E ", new Vector2d(1, 0)),
    SOUTHEAST("SE", new Vector2d(1, -1)),
    SOUTH("S ", new Vector2d(0, -1)),
    SOUTHWEST("SW", new Vector2d(-1, -1)),
    WEST("W ", new Vector2d(-1, 0)),
    NORTHWEST("NW", new Vector2d(-1, 1));

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
