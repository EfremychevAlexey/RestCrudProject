package org.example.servlet.dto;

public class StudentUpdateDto {
    private Long id;
    private String name;
    private CourseUpdateDto course;

    public StudentUpdateDto() {
    }

    public StudentUpdateDto(Long id, String name, CourseUpdateDto course) {
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

    @Override
    public String toString() {
        return "StudentUpdateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
