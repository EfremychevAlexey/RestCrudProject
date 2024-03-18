package org.example.repositoryDAO.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Course;
import org.example.repositoryDAO.CourseDAO;
import org.example.repositoryDAO.CourseTeacherDAO;
import org.example.repositoryDAO.StudentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс описывающий взаимодействие Course entity с базой даннных
 */
public class CourseDAOImpl implements CourseDAO {
    private static CourseDAOImpl instance;
    private final ConnectionManager dbConnectionManager = ConnectionManagerImpl.getInstance();
    private static final StudentDAO studentDao = StudentDAOImpl.getInstance();
    private static final CourseTeacherDAO courseTeacherDAO = CourseTeacherDAOImpl.getInstance();

    static final String SAVE_SQL = "INSERT INTO school.courses(course_name) VALUES(?)";
    static final String UPDATE_SQL = "UPDATE school.courses SET course_name = ? WHERE id = ?";
    static final String DELETE_SQL = "DELETE FROM school.courses WHERE id = ?";
    static final String FIND_BY_ID_SQL = "SELECT id, course_name FROM school.courses WHERE id = ?";
    static final String FIND_BY_COURSE_NAME_SQL = "SELECT id, course_name FROM school.courses WHERE course_name = ?";
    static final String FIND_ALL_SQL = "SELECT id, course_name FROM school.courses";
    static final String EXIST_BY_ID_SQL = "SELECT exists (SELECT 1 FROM school.courses WHERE id = ? LIMIT 1)";

    private CourseDAOImpl() {
    }

    public static synchronized CourseDAOImpl getInstance() {
        if (instance == null) {
            instance = new CourseDAOImpl();
        }
        return instance;
    }

    private static Course createCourse(ResultSet resultSet) throws SQLException {
        Course course;
        course = new Course(
                resultSet.getLong("id"),
                resultSet.getString("course_name"),
                null,
                null);
        return course;
    }

    @Override
    public Course save(Course course) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                course = new Course(
                        resultSet.getLong("id"),
                        course.getName(),
                        null,
                        null
                );
                course.getStudents();
                course.getTeachers();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @Override
    public void update(Course course) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.setLong(2, course.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult = true;
        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {


            preparedStatement.setLong(1, id);

            studentDao.deleteByCourseId(id);
            courseTeacherDAO.deleteByCourseId(id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deleteResult;
    }

    @Override
    public Optional<Course> findById(Long id) {
        Course course = null;
        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course = createCourse(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> findByName(String name) {
        Course course = null;
        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COURSE_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course = createCourse(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  Optional.ofNullable(course);
    }

    @Override
    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                courseList.add(createCourse(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    @Override
    public boolean existById(Long id) {
        boolean isExists = false;
        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExists;
    }
}
