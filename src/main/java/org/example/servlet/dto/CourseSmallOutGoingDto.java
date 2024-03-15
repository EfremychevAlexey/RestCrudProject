package org.example.servlet.dto;

public class CourseSmallOutGoingDto {
    private Long id;
    private String name;

    public CourseSmallOutGoingDto() {
    }

    public CourseSmallOutGoingDto(Long id, String name) {
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
        return "CourseSmallOutGoingDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
