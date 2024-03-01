package org.example.model;

import java.util.Objects;

public class CoursesTeachers {
    private int id;
    private int courseID;
    private int teacherID;


    public CoursesTeachers(int courseID, int teacherID) {
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public CoursesTeachers(int id, int courseID, int teacherID) {
        this.id = id;
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursesTeachers that = (CoursesTeachers) o;
        return id == that.id && courseID == that.courseID && teacherID == that.teacherID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseID, teacherID);
    }

    @Override
    public String toString() {
        return "CoursesTeachers{" +
                "id=" + id +
                ", courseID=" + courseID +
                ", teacherID=" + teacherID +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
