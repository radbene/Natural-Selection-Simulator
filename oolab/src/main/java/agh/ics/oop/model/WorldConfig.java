package agh.ics.oop.model;
import agh.ics.oop.model.variants.EMutationVariant;
import agh.ics.oop.model.variants.EMapVariant;

// TODO: check for negative values

public enum WorldConfig {
    INSTANCE;

    private int mapWidth;
    private int mapHeight;
    private EMapVariant mapVariant;
    private int initialPlantCount;
    private int plantEnergy;
    private int dailyGrassGrowth;
    private int initialAnimalCount;
    private int initialAnimalEnergy;
    private int energyToReproduce;
    private int parentEnergyCost;
    private int minMutations;
    private int maxMutations;
    private EMutationVariant mutationVariant;
    private int genomeLength;

    private WorldConfig() {
        this.mapWidth = 100;
        this.mapHeight = 100;
        this.mapVariant = EMapVariant.STANDARD;
        this.initialPlantCount = 50;
        this.plantEnergy = 10;
        this.dailyGrassGrowth = 5;
        this.initialAnimalCount = 20;
        this.initialAnimalEnergy = 50;
        this.energyToReproduce = 30;
        this.parentEnergyCost = 20;
        this.minMutations = 0;
        this.maxMutations = 2;
        this.mutationVariant = EMutationVariant.STANDARD;
        this.genomeLength = 64;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public EMapVariant getMapVariant() {
        return mapVariant;
    }

    public void setMapVariant(EMapVariant mapVariant) {
        this.mapVariant = mapVariant;
    }

    public int getInitialPlantCount() {
        return initialPlantCount;
    }

    public void setInitialPlantCount(int initialPlantCount) {
        this.initialPlantCount = initialPlantCount;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public void setPlantEnergy(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }

    public int getDailyGrassGrowth() {
        return dailyGrassGrowth;
    }

    public void setDailyGrassGrowth(int dailyGrassGrowth) {
        this.dailyGrassGrowth = dailyGrassGrowth;
    }

    public int getInitialAnimalCount() {
        return initialAnimalCount;
    }

    public void setInitialAnimalCount(int initialAnimalCount) {
        this.initialAnimalCount = initialAnimalCount;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public void setInitialAnimalEnergy(int initialAnimalEnergy) {
        this.initialAnimalEnergy = initialAnimalEnergy;
    }

    public int getEnergyToReproduce() {
        return energyToReproduce;
    }

    public void setEnergyToReproduce(int energyToReproduce) {
        this.energyToReproduce = energyToReproduce;
    }

    public int getParentEnergyCost() {
        return parentEnergyCost;
    }

    public void setParentEnergyCost(int parentEnergyCost) {
        this.parentEnergyCost = parentEnergyCost;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public EMutationVariant getMutationVariant() {
        return mutationVariant;
    }

    public void setMutationVariant(EMutationVariant mutationVariant) {
        this.mutationVariant = mutationVariant;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public void resetToDefaults() {
        this.mapWidth = 100;
        this.mapHeight = 100;
        this.mapVariant = EMapVariant.STANDARD;
        this.initialPlantCount = 50;
        this.plantEnergy = 10;
        this.dailyGrassGrowth = 5;
        this.initialAnimalCount = 20;
        this.initialAnimalEnergy = 50;
        this.energyToReproduce = 30;
        this.parentEnergyCost = 20;
        this.minMutations = 0;
        this.maxMutations = 2;
        this.mutationVariant = EMutationVariant.STANDARD;
        this.genomeLength = 64;
    }
}
