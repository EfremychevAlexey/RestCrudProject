package org.example.servlet.dto;

public class TeacherSmallOutGoingDto {
    private Long id;
    private String name;

    public TeacherSmallOutGoingDto() {
    }

    public TeacherSmallOutGoingDto(Long id, String name) {
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
        return "TeacherSmallOutGoingDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
