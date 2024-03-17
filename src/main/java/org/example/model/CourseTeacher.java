package org.example.model;

/**
 * Таблица связей Course - Teacher
 * ManyToMany
 * Course <-> Teacher
 */
public class CourseTeacher {
    private Long id;
    private Long courseId;
    private Long teacherId;

    public CourseTeacher() {
    }

    public CourseTeacher(Long id, Long courseId, Long teacherId) {
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
