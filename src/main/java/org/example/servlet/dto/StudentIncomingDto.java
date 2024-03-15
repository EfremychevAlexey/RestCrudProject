package org.example.servlet.dto;

import org.example.model.Course;

public class StudentIncomingDto {
    private String name;

    public StudentIncomingDto() {
    }

    public StudentIncomingDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
