package org.example.jdbc.dao;

import org.example.jdbc.DBConnection;
import org.example.model.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentsDAO implements StudentsDAOInterface<Student, String> {

    public StudentsDAO() {
    }

    @Override
    public int create(Student student) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStudent.CREATE.QUERY)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getCourseId());
            preparedStatement.execute();

            PreparedStatement resultStatement = connection.prepareStatement("SELECT id FROM school.students WHERE student_name = ? AND course_id = ?;");
            resultStatement.setString(1, student.getName());
            resultStatement.setInt(2, student.getCourseId());
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
    public ArrayList<Student> read(String name) {
        ArrayList<Student> students = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStudent.READ_BY_NAME.QUERY)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String student_name = resultSet.getString("student_name");
                int course_id = resultSet.getInt("course_id");

                students.add(new Student(id, student_name, course_id));
            }
            return students;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student read(int studentId) {
        Student student = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLStudent.READ_BY_ID.QUERY)) {

            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String student_name = resultSet.getString("student_name");
                int course_id = resultSet.getInt("course_id");

                student = new Student(id, student_name, course_id);
            }
            return student;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Student> readAll() {
        ArrayList<Student> students = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM school.students");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String student_name = resultSet.getString("student_name");
                int course_id = resultSet.getInt("course_id");

                students.add(new Student(id, student_name, course_id));
            }
            return students;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int studentId, int courseId) {

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStudent.UPDATE.QUERY)) {

            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int studentId) {

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLStudent.DELETE.QUERY)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    enum SQLStudent {
        CREATE("INSERT INTO school.students(student_name, course_id) VALUES(?, ?);"),
        READ_BY_NAME("SELECT * FROM school.students WHERE student_name = ?;"),
        READ_BY_ID("SELECT * FROM school.students WHERE id = ?;"),
        UPDATE("UPDATE school.students SET course_id = ? WHERE id = ?;"),
        DELETE("DELETE FROM school.students WHERE id = ?;");

        final String QUERY;

        SQLStudent(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
