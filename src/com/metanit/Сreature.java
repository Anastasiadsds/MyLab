package com.metanit;

import java.util.Objects;

abstract class Creature {
    private String name;
    private int age;
    private String type;

    public Creature (String name, int age, String type){
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public Creature (String name, String type){
        this.name = name;
        this.type = type;
    }

    public Creature(String type){
        this.type = type;
    }


    public int getAge() {
        return this.age;
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return (getName() + " -- " + getType() + " -- " + getAge());
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, age, type);
    }
}
