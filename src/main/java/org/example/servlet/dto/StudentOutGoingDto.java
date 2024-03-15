package org.example.servlet.dto;

public class StudentOutGoingDto {
    private Long id;
    private String name;
    private CourseSmallOutGoingDto course;

    public StudentOutGoingDto() {
    }

    public StudentOutGoingDto(Long id, String name, CourseSmallOutGoingDto course) {
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

    public CourseSmallOutGoingDto getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "StudentOutGoingDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
