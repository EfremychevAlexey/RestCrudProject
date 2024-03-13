package org.example.model;

/**
 * ManyToMany
 * Course <-> Teacher
 */
public class CourseTeacher {
    private long id;
    private long courseId;
    private long teacherId;

    public CourseTeacher() {
    }

    public CourseTeacher(long id, long courseId, long teacherId) {
        this.id = id;
        this.courseId = courseId;
        this.teacherId = teacherId;
    }

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}
