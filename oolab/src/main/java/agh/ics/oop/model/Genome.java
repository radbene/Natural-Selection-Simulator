package agh.ics.oop.model;

import agh.ics.oop.model.variants.EMutationVariant;

import static java.lang.Math.round;

public class Genome {
    WorldConfig config;
    private int currentGene = 0;
    private int length = 0;

    public int getCurrentGene() {
        int r = genome[currentGene];
        currentGene = (currentGene + 1) % length;
        return r;
    }

    public int getLength() {
        return length;
    }

    public int[] getGenome() {
        return genome;
    }

    public int getMinGeneValue() {
        return minGeneValue;
    }

    public int getMaxGeneValue() {
        return maxGeneValue;
    }

    private int[] genome;
    private final int minGeneValue = 0;
    private final int maxGeneValue = 7;

    public Genome(WorldConfig config) {
        this.config = config;

        this.length = config.getGenomeLength();
        genome = new int[length];
    }

    public Genome reproductionGenome(Animal animal1, Animal animal2) {
        Genome genome1;
        Genome genome2;
        int energy1;
        int energy2;
        if (animal1.getEnergy() > animal2.getEnergy()){
            genome1 = animal1.getGenome();
            genome2 = animal2.getGenome();
            energy1 = animal1.getEnergy();
            energy2 = animal2.getEnergy();
        }
        else {
            genome2 = animal1.getGenome();
            genome1 = animal2.getGenome();
            energy2 = animal1.getEnergy();
            energy1 = animal2.getEnergy();
        }

        //genome1 and energy1  belong to the stronger animal
        Genome childrenGenome = new Genome(config);
        int cutIndex = round((float)(energy1) / (float)(energy1 + energy2) * length);

        if (Math.random() < 0.5) {  //left part of genome of the stronger animal is dominant
            for (int i = 0; i < cutIndex; i++) {
                childrenGenome.genome[i] = genome1.genome[i];
            }
            for (int i = cutIndex; i < length; i++) {
                childrenGenome.genome[i] = genome2.genome[i];
            }
        }
        else {
            cutIndex = length - cutIndex;
            for (int i = 0; i < cutIndex; i++) {
                childrenGenome.genome[i] = genome2.genome[i];
            }
            for (int i = cutIndex; i < length; i++) {
                childrenGenome.genome[i] = genome1.genome[i];
            }
        }
        return mutation(childrenGenome);

    }

    public static Genome randomGenome(WorldConfig config){
        Genome genome = new Genome(config);
        for (int i = 0; i < genome.length; i++){
            genome.genome[i] = (int) ((Math.random() * (genome.maxGeneValue - genome.minGeneValue)) + genome.minGeneValue);
        }
        return genome;
    }

    private Genome mutation(Genome genome) {
        int numMutations = getRandomNumberInRange(config.getMinMutations(), config.getMaxMutations());

        for (int i = 0; i < numMutations; i++) {
            int geneIndex = getRandomNumberInRange(0, length - 1); // Losowy indeks genu do zmutowania

            if (config.getMutationVariant() == EMutationVariant.STANDARD) {
                genome.genome[geneIndex] = getRandomNumberInRange(minGeneValue, maxGeneValue);
            } else {
                int change = Math.random() < 0.5 ? 1 : -1; // Losowo +1 lub -1
                int newValue = genome.genome[geneIndex] + change;

                if (newValue < minGeneValue) {
                    newValue = maxGeneValue;
                } else if (newValue > maxGeneValue) {
                    newValue = minGeneValue;
                }

                genome.genome[geneIndex] = newValue;
            }
        }

        return genome;
    }

    private int getRandomNumberInRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(genome[i]);
        }
        return sb.toString();
    }
}




