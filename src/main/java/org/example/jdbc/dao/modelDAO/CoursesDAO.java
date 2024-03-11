package org.example.jdbc.dao.modelDAO;

import org.example.jdbc.DBConnection;
import org.example.jdbc.dao.DAO;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CoursesDAO implements DAO<Course, Integer> {

    static final String CREATE = "INSERT INTO school.courses(course_name) VALUES(?) RETURNING id";
    static final String READ = "SELECT * FROM school.courses WHERE id = ?";
    static final String UPDATE = "UPDATE school.courses SET course_name = ? WHERE id = ? RETURNING id";
    static final String DELETE = "DELETE FROM school.courses WHERE id = ? AND course_name = ? RETURNING id";

    @Override
    public int create(Course course) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            preparedStatement.setString(1, course.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
                course.setId(result);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course read(Integer id) {
        Course course = new Course();
        CoursesTeachersDAO coursesTeachersDAO = new CoursesTeachersDAO();
        StudentsDAO studentsDAO = new StudentsDAO();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                course.setId(id);
                course.setName(resultSet.getString("course_name"));
                course.setTeachers(coursesTeachersDAO.readAllTeachersByCourses(course));
                course.setStudents(studentsDAO.readAllByCourse(course));
            }

            return course;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int update(Course course) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Course course) {
        int result = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, course.getId());
            preparedStatement.setString(2, course.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
