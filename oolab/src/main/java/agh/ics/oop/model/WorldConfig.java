package agh.ics.oop.model;

import agh.ics.oop.model.variants.EMutationVariant;
import agh.ics.oop.model.variants.EMapVariant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorldConfig {

    private final int mapWidth;
    private final int mapHeight;
    private final EMapVariant mapVariant;
    private final int initialPlantCount;
    private final int plantEnergy;
    private final int dailyGrassGrowth;
    private final int initialAnimalCount;
    private final int initialAnimalEnergy;
    private final int energyToReproduce;
    private final int parentEnergyCost;
    private final int minMutations;
    private final int maxMutations;
    private final EMutationVariant mutationVariant;
    private final int genomeLength;
    private final int fireMaxAge;
    private final int fireFreq;

    private WorldConfig(
        int mapWidth, int mapHeight, EMapVariant mapVariant, int initialPlantCount, int plantEnergy,
        int dailyGrassGrowth, int initialAnimalCount, int initialAnimalEnergy, int energyToReproduce,
        int parentEnergyCost, int minMutations, int maxMutations, EMutationVariant mutationVariant,
        int genomeLength, int fireMaxAge, int fireFreq
    ) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapVariant = mapVariant;
        this.initialPlantCount = initialPlantCount;
        this.plantEnergy = plantEnergy;
        this.dailyGrassGrowth = dailyGrassGrowth;
        this.initialAnimalCount = initialAnimalCount;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.energyToReproduce = energyToReproduce;
        this.parentEnergyCost = parentEnergyCost;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.mutationVariant = mutationVariant;
        this.genomeLength = genomeLength;
        this.fireMaxAge = fireMaxAge;
        this.fireFreq = fireFreq;
    }

    public static WorldConfig loadFromFile(String filePath) throws IOException {
        Builder builder = new Builder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length != 2) continue;

                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "mapWidth":
                        builder.mapWidth(Integer.parseInt(value));
                        break;
                    case "mapHeight":
                        builder.mapHeight(Integer.parseInt(value));
                        break;
                    case "mapVariant":
                        builder.mapVariant(EMapVariant.valueOf(value));
                        break;
                    case "initialPlantCount":
                        builder.initialPlantCount(Integer.parseInt(value));
                        break;
                    case "plantEnergy":
                        builder.plantEnergy(Integer.parseInt(value));
                        break;
                    case "dailyGrassGrowth":
                        builder.dailyGrassGrowth(Integer.parseInt(value));
                        break;
                    case "initialAnimalCount":
                        builder.initialAnimalCount(Integer.parseInt(value));
                        break;
                    case "initialAnimalEnergy":
                        builder.initialAnimalEnergy(Integer.parseInt(value));
                        break;
                    case "energyToReproduce":
                        builder.energyToReproduce(Integer.parseInt(value));
                        break;
                    case "parentEnergyCost":
                        builder.parentEnergyCost(Integer.parseInt(value));
                        break;
                    case "minMutations":
                        builder.minMutations(Integer.parseInt(value));
                        break;
                    case "maxMutations":
                        builder.maxMutations(Integer.parseInt(value));
                        break;
                    case "mutationVariant":
                        builder.mutationVariant(EMutationVariant.valueOf(value));
                        break;
                    case "genomeLength":
                        builder.genomeLength(Integer.parseInt(value));
                        break;
                    case "fireMaxAge":
                        builder.fireMaxAge(Integer.parseInt(value));
                        break;
                    case "fireFreq":
                        builder.fireFreq(Integer.parseInt(value));
                        break;
                    default:
                        System.err.println("Unknown configuration key: " + key);
                }
            }
        }

        return builder.build();
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public EMapVariant getMapVariant() {
        return mapVariant;
    }

    public int getInitialPlantCount() {
        return initialPlantCount;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getDailyGrassGrowth() {
        return dailyGrassGrowth;
    }

    public int getInitialAnimalCount() {
        return initialAnimalCount;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public int getEnergyToReproduce() {
        return energyToReproduce;
    }

    public int getParentEnergyCost() {
        return parentEnergyCost;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public EMutationVariant getMutationVariant() {
        return mutationVariant;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getFireMaxAge() {
        return fireMaxAge;
    }

    public int getFireFreq() {
        return fireFreq;
    }

    public static class Builder {
        private static int mapWidth = 10;
        private static int mapHeight = 10;
        private static EMapVariant mapVariant = EMapVariant.STANDARD;
        private static int initialPlantCount = 10;
        private static int plantEnergy = 10;
        private static int dailyGrassGrowth = 5;
        private static int initialAnimalCount = 6;
        private static int initialAnimalEnergy = 50;
        private static int energyToReproduce = 40;
        private static int parentEnergyCost = 10;
        private static int minMutations = 0;
        private static int maxMutations = 2;
        private static EMutationVariant mutationVariant = EMutationVariant.STANDARD;
        private static int genomeLength = 5;
        private static int fireMaxAge = 0;
        private static int fireFreq = 0;

        public Builder mapWidth(int mapWidth) {
            validatePositive(mapWidth, "Map Width");
            this.mapWidth = mapWidth;
            return this;
        }

        public Builder mapHeight(int mapHeight) {
            validatePositive(mapHeight, "Map Height");
            this.mapHeight = mapHeight;
            return this;
        }

        public Builder mapVariant(EMapVariant mapVariant) {
            validateEnumValue(mapVariant, "Map Variant");
            this.mapVariant = mapVariant;
            return this;
        }

        public Builder initialPlantCount(int initialPlantCount) {
            validatePositive(initialPlantCount, "Initial Plant Count");
            this.initialPlantCount = initialPlantCount;
            return this;
        }

        public Builder plantEnergy(int plantEnergy) {
            validatePositive(plantEnergy, "Plant Energy");
            this.plantEnergy = plantEnergy;
            return this;
        }

        public Builder dailyGrassGrowth(int dailyGrassGrowth) {
            validatePositive(dailyGrassGrowth, "Daily Grass Growth");
            this.dailyGrassGrowth = dailyGrassGrowth;
            return this;
        }

        public Builder initialAnimalCount(int initialAnimalCount) {
            validatePositive(initialAnimalCount, "Initial Animal Count");
            this.initialAnimalCount = initialAnimalCount;
            return this;
        }

        public Builder initialAnimalEnergy(int initialAnimalEnergy) {
            validatePositive(initialAnimalEnergy, "Initial Animal Energy");
            this.initialAnimalEnergy = initialAnimalEnergy;
            return this;
        }

        public Builder energyToReproduce(int energyToReproduce) {
            validatePositive(energyToReproduce, "Energy to Reproduce");
            this.energyToReproduce = energyToReproduce;
            return this;
        }

        public Builder parentEnergyCost(int parentEnergyCost) {
            validatePositive(parentEnergyCost, "Parent Energy Cost");
            this.parentEnergyCost = parentEnergyCost;
            return this;
        }

        public Builder minMutations(int minMutations) {
            if (minMutations < 0) {
                throw new IllegalArgumentException(minMutations + " must be positive.");
            }
            this.minMutations = minMutations;
            return this;
        }

        public Builder maxMutations(int maxMutations) {
            if (maxMutations < 0) {
                throw new IllegalArgumentException(maxMutations + " must be positive.");
            }
            this.maxMutations = maxMutations;
            return this;
        }

        public Builder mutationVariant(EMutationVariant mutationVariant) {
            validateEnumValue(mutationVariant, "Mutation Variant");
            this.mutationVariant = mutationVariant;
            return this;
        }

        public Builder genomeLength(int genomeLength) {
            validatePositive(genomeLength, "Genome Length");
            this.genomeLength = genomeLength;
            return this;
        }

        public Builder fireMaxAge(int fireMaxAge) {
            if(this.mapVariant != EMapVariant.FIRE) {
                this.fireMaxAge = 0;
                return this;
            }
            validatePositive(fireMaxAge, "Fire Max Age");
            this.fireMaxAge = fireMaxAge;
            return this;
        }

        public Builder fireFreq(int fireFreq) {
            if(this.mapVariant != EMapVariant.FIRE) {
                this.fireFreq = 0;
                return this;
            }
            validatePositive(fireFreq, "Fire Frequency");
            this.fireFreq = fireFreq;
            return this;
        }

        public WorldConfig build() {
            return new WorldConfig(
                mapWidth, mapHeight, mapVariant, initialPlantCount, plantEnergy, dailyGrassGrowth,
                initialAnimalCount, initialAnimalEnergy, energyToReproduce, parentEnergyCost,
                minMutations, maxMutations, mutationVariant, genomeLength,
                fireMaxAge, fireFreq
            );
        }

        private void validatePositive(int value, String fieldName) {
            if (value <= 0) {
                throw new IllegalArgumentException(fieldName + " must be positive.");
            }
        }

        private void validateEnumValue(Object value, String fieldName) {
            if (value == null) {
                throw new IllegalArgumentException(fieldName + " cannot be null.");
            }
        }
    }
}
