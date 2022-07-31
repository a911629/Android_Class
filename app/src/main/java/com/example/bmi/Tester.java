package com.example.bmi;

import hello.Person;
import hello.Student;

public class Tester {
    public static void main(String[] args) {
        Student stu = new Student ("001", "Hank", 60, 80);
        Student stu1 = new Student ("004", "Eric", 35, 60);
        stu.print ();
        stu1.print ();
//        stu.setId ("001");
//        stu.setName ("Hank");
//        stu.setMath (60);
//        stu.setEnglish (80);

//        Person person = new Person ();
//        person.hello ();
//        person.hello ("Calvin");
//
//        以下將weight & height 以set的方式修改，而不直接更動class的屬性(field)
//        person.weight = 68;
//        person.height = 1.85f;
//
//        person.setWeight (68);
//        person.setHeight (1.85f);
//        System.out.println (person.bmi ());
    }
}
