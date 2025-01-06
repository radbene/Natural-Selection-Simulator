package agh.ics.oop.model;

public class AnimalStats {

    private int children = 0;
    private int descendants = 0;
    private int grassEaten = 0;
    private final int dayOfBirth;
    private int dayOfDeath = -1;
    private final AnimalStats parent1;
    private final AnimalStats parent2;
    private int lastDescendantID = -1; // to prevent double counting of descendants
    private Genome genome;
    private int activeGenome;


    public AnimalStats(AnimalStats parent1, AnimalStats parent2, int day) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.dayOfBirth = day;
    }

    public void addDescendant(int id){
        if(id == this.lastDescendantID) return;
        this.lastDescendantID = id;
        this.descendants++;
    }   

    public void addChild(int id){
        this.children++;
        if (this.parent1 != null) this.parent1.addDescendant(id);
        if (this.parent2 != null) this.parent2.addDescendant(id);
    }

    public void eatGrass(){
        this.grassEaten++;
    }

    public void die(int day){
        this.dayOfDeath = day;
    }

    public int getChildren() {
        return children;
    }

    public int getDescendants() {
        return descendants;
    }

    public int getGrassEaten() {
        return grassEaten;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    // TODO: Add genome methods

    public void printAnimalStats(){
        System.out.println("Day of birth: " + this.dayOfBirth);
        System.out.println("Day of death: " + this.dayOfDeath);
        System.out.println("Children: " + this.children);
        System.out.println("Descendants: " + this.descendants);
        System.out.println("Grass eaten: " + this.grassEaten);
        // TODO: Add genome printing
    }
}
