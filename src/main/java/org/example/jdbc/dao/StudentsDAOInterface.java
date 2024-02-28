package org.example.jdbc.dao;

import org.example.model.Student;

import java.util.List;

public interface StudentsDAOInterface<Entity, Key> {
    int create(Student student);
    List<Entity> read(Key key);
    Student read(int id);
    void update(int studentId, int courseId);
    void delete(int id);
}
