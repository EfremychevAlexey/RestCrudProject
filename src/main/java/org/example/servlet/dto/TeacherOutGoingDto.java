package org.example.servlet.dto;

import org.example.model.Course;

import java.util.List;

public class TeacherOutGoingDto {
    private Long id;
    private String name;
    private List<CourseOutGoingDto> courseList;

    public TeacherOutGoingDto() {
    }

    public TeacherOutGoingDto(Long id, String name, List<CourseOutGoingDto> courseList) {
        this.id = id;
        this.name = name;
        this.courseList = courseList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseOutGoingDto> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseOutGoingDto> courseList) {
        this.courseList = courseList;
    }
}
