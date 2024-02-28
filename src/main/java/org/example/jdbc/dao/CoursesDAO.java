package org.example.jdbc.dao;

import org.example.jdbc.DBConnection;
import org.example.model.Course;
import org.example.model.Student;

import java.sql.*;
import java.util.ArrayList;

public class CoursesDAO implements CoursesDAOInterface {

    @Override
    public int create(Course course) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCourses.CREATE.QUERY)) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.execute();

            PreparedStatement resultStatement = connection.prepareStatement("SELECT id FROM school.courses WHERE course_name = ?;");
            resultStatement.setString(1, course.getName());
            ResultSet resultSet = resultStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course read(String name) {
        Course course = null;
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM school.courses");

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String courseName = resultSet.getString("course_name");

                course = new Course(id, courseName);
            }
            return course;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course read(int id) {
        Course course = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCourses.READ.QUERY)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("course_name");
                course = new Course(id, name);
            }
            return course;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Course> readAll() {

        ArrayList<Course> courses = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM school.courses");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String courseName = resultSet.getString("course_name");

                courses.add(new Course(id, courseName));
            }
            return courses;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int courseId, String courseName) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCourses.UPDATE.QUERY)) {

            preparedStatement.setString(1, courseName);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLCourses.DELETE.QUERY)) {

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    enum SQLCourses {
        CREATE("INSERT INTO school.courses(course_name) VALUES(?);"),
        READ("SELECT * FROM school.courses WHERE id = ?;"),
        UPDATE("UPDATE school.courses SET course_name = ? WHERE id = ?;"),
        DELETE("DELETE FROM school.courses WHERE id = ?;");

        final String QUERY;

        SQLCourses(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
