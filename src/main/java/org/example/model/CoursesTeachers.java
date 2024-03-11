package org.example.model;

import java.util.Objects;

public class CoursesTeachers {
    private Course course;
    private Teacher teacher;

    public CoursesTeachers() {
    }

    public CoursesTeachers(Course course, Teacher teacher) {
        this.course = course;
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesTeachers that = (CoursesTeachers) o;
        return Objects.equals(course, that.course) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, teacher);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
