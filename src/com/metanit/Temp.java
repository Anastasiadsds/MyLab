package com.metanit;

public class Temp{
    String name ;
    int age;
    Skill skill;
    String disability;

    public Gamer createGamer(){

        Gamer gamer = age != 0 ? new Gamer(name, age): new Gamer(name);

        if (skill != null) {
            gamer.addSkill(skill);
        }
        return gamer;
    }
}
