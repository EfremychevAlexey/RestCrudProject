package org.example.servlet.dto;

import java.util.List;

public class TeacherUpdateDto {
    private Long id;
    private String name;
    private CourseUpdateDto course;

    public TeacherUpdateDto() {
    }

    public TeacherUpdateDto(Long id, String name, CourseUpdateDto course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CourseUpdateDto getCourse() {
        return course;
    }
}
