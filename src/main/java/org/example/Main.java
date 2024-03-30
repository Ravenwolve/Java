package org.example;

import java.lang.reflect.InvocationTargetException;

class Phone {
    long ph;
    char ch;
}
enum Sex {
    Male,
    Female
}
class Person {
    //Sex sex;
    int a;
    protected double b;
    Integer[] c;
    Phone ph;
    String str;
     Person() {
        this.a = 5;
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        Person a = RandomObject.Generate(Person.class);
    }
}