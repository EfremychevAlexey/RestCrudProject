package org.example.servlet.dto;

import java.util.List;

public class CourseUpdateDto {
    private Long id;
    private String name;

    public CourseUpdateDto() {
    }

    public CourseUpdateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CourseUpdateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
