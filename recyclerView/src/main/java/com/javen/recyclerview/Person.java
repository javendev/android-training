package com.javen.recyclerview;

/**
 * Created by Javen on 2016-03-01.
 */
public class Person {


    /**
     * Id : 1
     * Name : Javen
     * Age : 16
     */

    private int Id;
    private String Name;
    private int Age;

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getAge() {
        return Age;
    }
}
