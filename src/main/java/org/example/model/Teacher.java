package org.example.model;

import java.util.ArrayList;
import java.util.Objects;

public class Teacher {
    private int id;
    private String name;
    private ArrayList<Course> courses;

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
        courses = new ArrayList<>();
    }

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
        courses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teachers = (Teacher) o;
        return id == teachers.id && Objects.equals(name, teachers.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Teachers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
