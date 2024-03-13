package org.example.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The department where the user works and the student studies
 * Relation:
 * One To Many: Course <- Student
 * Many To Many: Course <-> Teacher
 */

public class Course {
    //private static final CoursesTeachersDAO//////
    private Long id;
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;

    public Course() {
    }

    public Course(Long id, String name, ArrayList<Student> students, ArrayList<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }
}
