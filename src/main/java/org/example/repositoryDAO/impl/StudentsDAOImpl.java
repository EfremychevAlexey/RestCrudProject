package org.example.repositoryDAO.impl;

import org.example.db.DBConnectionManager;
import org.example.db.DBConnectionManagerImpl;
import org.example.exception.RepositoryException;
import org.example.model.Course;
import org.example.model.Student;
import org.example.repositoryDAO.CourseDAO;
import org.example.repositoryDAO.StudentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentsDAOImpl implements StudentDAO {
    private static StudentsDAOImpl instance = null;
    private final DBConnectionManager dbConnectionManager = DBConnectionManagerImpl.getInstance();
    private final CourseDAO courseDAO = CourseDAOImpl.getInstance();


    static final String SAVE_SQL = "INSERT INTO school.students(student_name, course_id) VALUES(?, ?)";
    static final String UPDATE_SQL = "UPDATE school.students SET student_name = ?, course_id = ? WHERE id = ?";
    static final String DELETE_SQL = "DELETE FROM school.students WHERE id = ?";
    static final String FIND_BY_ID_SQL = "SELECT id, student_name, course_id FROM school.students WHERE id = ?";
    static final String FIND_ALL_SQL = "SELECT id, student_name, course_id FROM school.students";
    static final String EXIST_BY_ID = "SELECT exists (SELECT 1 FROM school.students WHERE id = ? LIMIT 1)";

    private StudentsDAOImpl() {
    }

    public static synchronized StudentsDAOImpl getInstance() {
        if (instance == null) {
            instance = new StudentsDAOImpl();
        }
        return instance;
    }

    private Student createStudent(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String studentName = resultSet.getString("student_name");
        Long courseId = resultSet.getLong("course_id");
        Course course = courseDAO.findById(courseId).orElse(null);
        return new Student(id, studentName, course);
    }

    @Override
    public Student save(Student student) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {

            preparedStatement.setString(1, student.getName());
            if (student.getCourse() == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setLong(2, student.getCourse().getId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                student = new Student(
                        resultSet.getLong("id"),
                        student.getName(),
                        student.getCourse()
                );
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return student;
    }

    @Override
    public void update(Student student) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, student.getName());
            if (student.getCourse() == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setLong(2, student.getCourse().getId());
            }
            preparedStatement.setLong(3, student.getCourse().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setLong(1, id);
            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return deleteResult;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Student student = null;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = createStudent(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findAll() {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(createStudent(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentList;
    }

    @Override
    public boolean existById(Long id) {
        boolean isExists = false;
        try (Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                isExists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExists;
    }
}
