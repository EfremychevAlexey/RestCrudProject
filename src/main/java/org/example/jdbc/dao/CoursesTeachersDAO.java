package org.example.jdbc.dao;

import org.example.model.CoursesTeachers;

public class CoursesTeachersDAO implements CoursesTeachersDAOInterface {
    @Override
    public int create(CoursesTeachers coursesTeachers) {
        return 0;
    }

    @Override
    public CoursesTeachers readByCourseId(int courseID) {
        return null;
    }

    @Override
    public CoursesTeachers readByTeacherId(int teacherID) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
