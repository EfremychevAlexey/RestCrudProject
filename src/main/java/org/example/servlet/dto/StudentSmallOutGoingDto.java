package org.example.servlet.dto;

public class StudentSmallOutGoingDto {
    private Long id;
    private String name;

    public StudentSmallOutGoingDto() {
    }

    public StudentSmallOutGoingDto(Long id, String name) {
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
        return "StudentSmallOutGoingDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
