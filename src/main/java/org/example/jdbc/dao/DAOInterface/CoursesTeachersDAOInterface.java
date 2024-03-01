package org.example.jdbc.dao.DAOInterface;

import org.example.model.CoursesTeachers;
import org.example.model.Teacher;

public interface CoursesTeachersDAOInterface {

    int create(CoursesTeachers coursesTeachers);
    CoursesTeachers readByCourseId(int courseID);
    CoursesTeachers readByTeacherId(int teacherID);
    void delete(int id);
}

