package hello;

public class Person {
    float weight;
    float height;
    public float bmi(){
        float bmi = weight / (height * height);
        return bmi;
    }
    public void hello(){
        System.out.println ("Hello");
    }
    public void hello(String name){
        System.out.println (name + " hello");
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
