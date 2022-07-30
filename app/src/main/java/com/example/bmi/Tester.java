package com.example.bmi;

public class Tester {
    public static void main(String[] args) {
//        System.out.println ("Hello world");
        Person person = new Person ();
        person.hello ();
        person.hello ("Calvin");
        person.weight = 68;
        person.height = 1.85f;
        System.out.println (person.bmi ());
    }
}
