package org.example.jdbc.dao.modelDAO;

import org.example.jdbc.DBConnection;
import org.example.jdbc.dao.DAO;
import org.example.model.CoursesTeachers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursesTeachersDAO implements DAO<CoursesTeachers, Integer> {

    static final String CREATE = "INSERT INTO school.courses_teachers(course_id, teacher_id) VALUES(?, ?) RETURNING id";
    static final String READ = "SELECT course_id, teacher_id FROM school.courses_teachers WHERE id = ?";
    static final String UPDATE = "UPDATE school.courses_teachers " +
            "SET course_id = ?, teacher_id = ? WHERE id = ? RETURNING id";
    static final String DELETE = "DELETE FROM school.courses_teachers " +
            "WHERE id = ? AND course_id = ? AND teacher_id = ? RETURNING id";


    @Override
    public int create(CoursesTeachers coursesTeachers) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            preparedStatement.setInt(1, coursesTeachers.getCourseID());
            preparedStatement.setInt(2, coursesTeachers.getTeacherID());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public CoursesTeachers read(Integer id) {
        CoursesTeachers coursesTeachers = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                int teacherId = resultSet.getInt("teacher_id");
                coursesTeachers = new CoursesTeachers(id, courseId, teacherId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coursesTeachers;
    }

    @Override
    public int update(CoursesTeachers coursesTeachers) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setInt(1, coursesTeachers.getCourseID());
            preparedStatement.setInt(2, coursesTeachers.getTeacherID());
            preparedStatement.setInt(3, coursesTeachers.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int delete(CoursesTeachers coursesTeachers) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, coursesTeachers.getId());
            preparedStatement.setInt(2, coursesTeachers.getCourseID());
            preparedStatement.setInt(3, coursesTeachers.getTeacherID());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

