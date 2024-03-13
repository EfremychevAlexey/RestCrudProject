package org.example.repositoryDAO.impl;

import org.example.db.DBConnectionManager;
import org.example.db.DBConnectionManagerImpl;
import org.example.exception.RepositoryException;
import org.example.model.Course;
import org.example.model.Teacher;
import org.example.repositoryDAO.CourseDAO;
import org.example.repositoryDAO.CoursesTeachersDAO;
import org.example.repositoryDAO.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeachersDAOImpl implements DAO<Teacher, Long> {
    private static TeachersDAOImpl instance;
    private final DBConnectionManager dbConnectionManager= DBConnectionManagerImpl.getInstance();
    private final CoursesTeachersDAO coursesTeachersDAO = CoursesTeachersDAOImpl.getInstance();
    private final CourseDAO courseDAO = CourseDAOImpl.getInstance();

    static final String SAVE_SQL = "INSERT INTO school.teachers(teacher_name) " +
            "VALUES(?)";
    static final String UPDATE_SQL = "UPDATE school.teachers SET teacher_name = ? WHERE id = ?";
    static final String DELETE_SQL = "DELETE FROM school.teachers WHERE id = ?";
    static final String FIND_BY_ID_SQL = "SELECT id, teacher_name FROM school.teachers WHERE id = ? LIMIT 1";
    static final String FIND_ALL_SQL = "SELECT id, teacher_name FROM school.teachers";
    static final String EXIST_BY_ID_SQL = "SELECT exists (SELECT 1 FROM school.teachers WHERE id = ? LIMIT 1)";


    private TeachersDAOImpl() {
    }

    public static synchronized TeachersDAOImpl getInstance() {
        if (instance == null) {
            instance = new TeachersDAOImpl();
        }
        return instance;
    }

    private Teacher createTeacher(ResultSet resultSet) throws SQLException {
        Long teacherId = resultSet.getLong("id");
        String teacherName = resultSet.getString("teacher_name");

        return new Teacher(teacherId, teacherName, null);
    }

    @Override
    public Teacher save(Teacher teacher) {
        try (Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                teacher = new Teacher(
                        resultSet.getLong("id"),
                        teacher.getName(),
                        null
                );
            }
            teacher.getCourses();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return teacher;
    }

    @Override
    public void update(Teacher teacher) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setLong(2, teacher.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {

            coursesTeachersDAO.deleteByTeacherId(id);

            preparedStatement.setLong(1, id);
            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return deleteResult;
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        Teacher teacher = null;
        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacher = createTeacher(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teacherList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacherList.add(createTeacher(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return teacherList;
    }

    @Override
    public boolean existById(Long id) {
        boolean isExists = false;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return isExists;
    }
}
