package agh.ics.oop.model;

public class Fire implements WorldElement{

    private Vector2d position;
    private int age;

    public Fire(Vector2d position) {
        this.position = position;
        this.age = 1;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public int burn(){
        return this.age++;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return "^";
    }

    @Override
    public String getResourceName() {
        return "pozar.png";
    }
}
