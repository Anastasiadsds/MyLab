package com.metanit;
import java.util.ArrayList;
import java.util.Objects;


public class Gamer extends Creature {
    private String name;
    private String  level;
    private int age;

    private ArrayList<Skill> skills = new ArrayList<Skill>();

    public Gamer(String name, String level) {
        super("Human");
        this.name = name;
        this.level = level;
    }

    public Gamer(String name, int age) {
        super("Human");
        this.name = name;
        this.age = age;
    }

    public Gamer(String name) {
        super("Human");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String  getLevel() {
        return level;
    }


    public void welcome(){
        if (this.getName() != null) {
            System.out.println("---------------------------------");
            System.out.println("Вы добавили нового игрока");
            System.out.println("---------------------------------");
        }else{
            System.out.println("---------------------------------");
            System.out.println("Вы добавили безликого игрока");
            System.out.println("---------------------------------");}
    }

    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    public boolean addSkill(Skill skill) {
        if (skills.add(skill)) {
            System.out.println("Объект - " + this.getName() + " - получил способность " + skill.getName());
            return true;
        } else {
            System.out.println("При добавлении способности произошла ошибка");
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gamer gamer = (Gamer) o;
        return Objects.equals(gamer.name, gamer.level) &&
                skills.equals(gamer.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, skills, level);
    }

}

