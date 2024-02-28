package org.example.jdbc.dao;

import org.example.jdbc.DBConnection;
import org.example.model.Course;
import org.example.model.Teacher;

import java.sql.*;
import java.util.ArrayList;

public class TeacherDAO implements TeacherDAOInterface{
    @Override
    public int create(Teacher teacher) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherDAO.SQLTeacher.CREATE.QUERY)) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.execute();

            PreparedStatement resultStatement = connection.prepareStatement("SELECT id FROM school.teachers WHERE teacher_name = ?;");
            resultStatement.setString(1, teacher.getName());
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
    public Teacher read(String name) {
        Teacher teacher = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM school.teachers WHERE teacher_name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String teacherName = resultSet.getString("teacher_name");

                teacher = new Teacher(id, teacherName);
            }
            return teacher;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher read(int id) {
        Teacher teacher = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherDAO.SQLTeacher.READ.QUERY)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("teacher_name");
                teacher = new Teacher(id, name);
            }
            return teacher;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Teacher> readAll() {

        ArrayList<Teacher> teachers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM school.teachers");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String teacherName = resultSet.getString("teacher_name");

                teachers.add(new Teacher(id, teacherName));
            }
            return teachers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int teacherId, String teacherName) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherDAO.SQLTeacher.UPDATE.QUERY)) {

            preparedStatement.setString(1, teacherName);
            preparedStatement.setInt(2, teacherId);
            preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TeacherDAO.SQLTeacher.DELETE.QUERY)) {

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    enum SQLTeacher {
        CREATE("INSERT INTO school.teachers(teacher_name) VALUES(?);"),
        READ("SELECT * FROM school.teachers WHERE id = ?;"),
        UPDATE("UPDATE school.teachers SET teacher_name = ? WHERE id = ?;"),
        DELETE("DELETE FROM school.teachers WHERE id = ?;");

        final String QUERY;

        SQLTeacher(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
