package org.example.servlet.dto;

import java.util.List;

public class TeacherUpdateDto {
    private Long id;
    private String name;
    private List<CourseUpdateDto> courseUpdateDtoList;

    public TeacherUpdateDto() {
    }

    public TeacherUpdateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CourseUpdateDto> getCourseUpdateDtoList() {
        return courseUpdateDtoList;
    }
}
