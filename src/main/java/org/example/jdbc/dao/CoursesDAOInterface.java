package org.example.jdbc.dao;

import org.example.model.Course;

public interface CoursesDAOInterface {

    int create(Course course);
    Course read(String name);
    Course read(int id);
    void update(int courseId, String courseName);
    void delete(int id);
}
