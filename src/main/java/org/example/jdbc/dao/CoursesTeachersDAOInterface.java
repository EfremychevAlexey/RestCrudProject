package org.example.jdbc.dao;

import org.example.model.Course;
import org.example.model.CoursesTeachers;
import org.example.model.Teacher;

import java.util.ArrayList;

public interface CoursesTeachersDAOInterface {

    CoursesTeachers create(CoursesTeachers coursesTeachers);
    ArrayList<Course> readAllCoursesByTeacher(Teacher teacher);
    ArrayList<Teacher> readAllTeachersByCourses(Course course);
    CoursesTeachers update(CoursesTeachers coursesTeachers);
    CoursesTeachers delete(CoursesTeachers coursesTeachers);
}
