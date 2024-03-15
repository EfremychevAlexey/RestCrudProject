package org.example.servlet.dto;

public class StudentUpdateDto {
    private Long id;
    private String name;
    private String courseName;

    public StudentUpdateDto() {
    }

    public StudentUpdateDto(Long id, String name, String courseName) {
        this.id = id;
        this.name = name;
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourseName() {
        return courseName;
    }
}
