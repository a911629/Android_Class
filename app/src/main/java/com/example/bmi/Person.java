package com.example.bmi;

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
}
