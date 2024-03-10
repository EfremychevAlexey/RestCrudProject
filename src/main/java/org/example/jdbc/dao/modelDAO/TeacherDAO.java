package org.example.jdbc.dao.modelDAO;

import org.example.jdbc.DBConnection;
import org.example.jdbc.dao.DAO;
import org.example.model.Course;
import org.example.model.Teacher;

import java.sql.*;
import java.util.ArrayList;

public class TeacherDAO implements DAO<Teacher, Integer> {

    static final String CREATE = "INSERT INTO school.teachers(teacher_name) VALUES(?) RETURNING id";
    static final String READ = "SELECT t.id, t.teacher_name, c.id AS course_id, c.course_name " +
            "FROM school.courses AS c " +
            "JOIN school.courses_teachers AS ct ON ct.course_id = c.id " +
            "JOIN school.teachers AS t ON t.id = ct.teacher_id WHERE t.id = ?;";

    static final String UPDATE = "UPDATE school.teachers SET teacher_name = ? WHERE id = ? RETURNING id";
    static final String DELETE = "DELETE FROM school.teachers WHERE id = ? AND teacher_name = ? RETURNING id";

    @Override
    public int create(Teacher teacher) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            preparedStatement.setString(1, teacher.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
                teacher.setId(result);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Teacher read(Integer id) {
        Teacher teacher = new Teacher();
        ArrayList<Course> courses = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt("id") == id) {
                    String teacherName = resultSet.getString("teacher_name");
                    teacher.setId(id);
                    teacher.setName(teacherName);
                }

                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                courses.add(new Course(courseId, courseName));
            }
            teacher.setCourses(courses);
            return teacher;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int update(Teacher teacher) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setInt(2, teacher.getId());
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
    public int delete(Teacher teacher) {
        int result = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, teacher.getName());
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
