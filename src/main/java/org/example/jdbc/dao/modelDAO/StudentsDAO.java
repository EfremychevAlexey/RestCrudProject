package org.example.jdbc.dao.modelDAO;

import org.example.jdbc.DBConnection;
import org.example.jdbc.dao.StudentDAOInterface;
import org.example.model.Course;
import org.example.model.Student;

import java.sql.*;
import java.util.ArrayList;

public class StudentsDAO implements StudentDAOInterface {
    private static StudentsDAO instance = null;

    static final String CREATE = "INSERT INTO school.students(student_name, course_id) VALUES(?, ?) RETURNING id";
    static final String READ = "SELECT s.id, s.student_name, c.id AS course_id, c.course_name" +
            " FROM school.students AS s LEFT JOIN school.courses AS c ON s.course_id = c.id WHERE s.id = ?";
    static final String READ_ALL_BY_COURSE = "SELECT id, student_name FROM school.students WHERE course_id = ?";
    static final String UPDATE = "UPDATE school.students SET course_id = ? WHERE id = ? RETURNING id";
    static final String DELETE = "DELETE FROM school.students WHERE id = ? AND student_name = ? AND course_id = ? RETURNING id";

    private StudentsDAO() {
    }

    public static StudentsDAO getInstance() {
        if (instance == null) {
            instance = new StudentsDAO();
        }
        return instance;
    }

    @Override
    public int create(Student student) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getCourse().getId());
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
    public Student read(Integer id) {
        CoursesDAO coursesDAO = CoursesDAO.getInstance();
        Student result = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String studentName = resultSet.getString("student_name");
                int courseId = resultSet.getInt("course_id");
                result = new Student(id, studentName, coursesDAO.read(courseId));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Student> readAllByCourse(Course course) {
        ArrayList<Student> students = new ArrayList<>();
        CoursesDAO coursesDAO = CoursesDAO.getInstance();

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_BY_COURSE)) {

            preparedStatement.setInt(1, course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String studentName = resultSet.getString("student_name");
                students.add(new Student(studentId, studentName, course));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public int update(Student student) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE)) {

            preparedStatement.setInt(1, student.getCourse().getId());
            preparedStatement.setInt(2, student.getId());
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
    public int delete(Student student) {
        int result = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(DELETE)) {

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getCourse().getId());
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
