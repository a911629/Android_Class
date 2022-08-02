package hello;

public class Student {
    public static int pass = 60;
    String id;
    String name;
    int math;
    int english;

    public Student(String id, String name, int math, int english) {
        this.id = id;
        this.name = name;
        this.math = math;
        this.english = english;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public void print(){
        System.out.print (id + "\t" + name + "\t" + math + "\t" + english + "\t" + getAverage ());
        if(getAverage () < pass) {
            System.out.println ("*");
        } else {
            System.out.println ();
        }
    }

    private int getAverage(){
        return (math + english)/2;
    }
}
