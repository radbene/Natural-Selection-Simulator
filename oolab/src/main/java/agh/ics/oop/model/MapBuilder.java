package agh.ics.oop.model;

import agh.ics.oop.model.variants.EMapVariant;
public class MapBuilder {

    public MapBuilder() {
    }
    
    public AbstractWorldMap createMap(WorldConfig config) {
        if(config.getMapVariant() == EMapVariant.FIRE) {
            return new FireWorldMap(config.getMapWidth(), config.getMapHeight(), config.getInitialPlantCount());
        } else {
            return new GrassField(config.getMapWidth(), config.getMapHeight(), config.getInitialPlantCount());
        }
    }
}
