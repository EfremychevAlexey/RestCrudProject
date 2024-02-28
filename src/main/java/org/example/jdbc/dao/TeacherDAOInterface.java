package org.example.jdbc.dao;

import org.example.model.Course;
import org.example.model.Teacher;

public interface TeacherDAOInterface {

    int create(Teacher teacher);
    Teacher read(String name);
    Teacher read(int id);
    void update(int teacherId, String teacherName);
    void delete(int id);
}
