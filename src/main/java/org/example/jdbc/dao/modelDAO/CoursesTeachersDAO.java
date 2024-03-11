package org.example.jdbc.dao.modelDAO;

import org.example.jdbc.DBConnection;
import org.example.jdbc.dao.CoursesTeachersDAOInterface;
import org.example.model.Course;
import org.example.model.CoursesTeachers;
import org.example.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesTeachersDAO implements CoursesTeachersDAOInterface {
    private static CoursesTeachersDAO instance = null;

    static final String CREATE = "INSERT INTO school.courses_teachers(course_id, teacher_id) VALUES(?, ?)";
    static final String READ_ALL_COURSES_BY_TEACHER = "SELECT c.id AS course_id, c.course_name " +
            "FROM school.courses AS c " +
            "JOIN school.courses_teachers AS ct ON ct.course_id = c.id " +
            "JOIN school.teachers AS t ON t.id = ct.teacher_id " +
            "WHERE t.id = ?";
    static final String READ_ALL_TEACHERS_BY_COURSE = "SELECT t.id AS teacher_id, t.teacher_name " +
            "FROM school.courses AS c " +
            "JOIN school.courses_teachers AS ct ON ct.course_id = c.id " +
            "JOIN school.teachers AS t ON t.id = ct.teacher_id " +
            "WHERE c.id = ?";
    static final String UPDATE = "UPDATE school.courses_teachers " +
            "SET course_id = ?, teacher_id = ?";
    static final String DELETE = "DELETE FROM school.courses_teachers " +
            "course_id = ? AND teacher_id = ?";

    private CoursesTeachersDAO() {
    }

    public static CoursesTeachersDAO getInstance() {
        if (instance == null) {
            instance = new CoursesTeachersDAO();
        }
        return instance;
    }

    @Override
    public CoursesTeachers create(CoursesTeachers coursesTeachers) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            preparedStatement.setInt(1, coursesTeachers.getCourse().getId());
            preparedStatement.setInt(2, coursesTeachers.getTeacher().getId());

            if (preparedStatement.execute()) {
                return coursesTeachers;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Course> readAllCoursesByTeacher(Teacher teacher) {
        ArrayList<Course> courses = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_COURSES_BY_TEACHER)) {

            preparedStatement.setInt(1, teacher.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                courses.add(new Course(courseId, courseName));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public ArrayList<Teacher> readAllTeachersByCourses(Course course) {
        ArrayList<Teacher> teachers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_TEACHERS_BY_COURSE)) {

            preparedStatement.setInt(1, course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int teacherId = resultSet.getInt("teacher_id");
                String teacherName = resultSet.getString("teacher_name");
                teachers.add(new Teacher(teacherId, teacherName));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    @Override
    public CoursesTeachers update(CoursesTeachers coursesTeachers) {

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setInt(1, coursesTeachers.getCourse().getId());
            preparedStatement.setInt(2, coursesTeachers.getTeacher().getId());

            if (preparedStatement.execute()) {
                return  coursesTeachers;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public CoursesTeachers delete(CoursesTeachers coursesTeachers) {

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(2, coursesTeachers.getCourse().getId());
            preparedStatement.setInt(3, coursesTeachers.getTeacher().getId());

            if (preparedStatement.execute()) {
                return coursesTeachers;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
