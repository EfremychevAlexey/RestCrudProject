package org.example.repositoryDAO.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.exception.RepositoryException;
import org.example.model.Course;
import org.example.model.CourseTeacher;
import org.example.model.Teacher;
import org.example.repositoryDAO.CourseTeacherDAO;
import org.example.repositoryDAO.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseTeacherDAOImpl implements CourseTeacherDAO {
    private static final ConnectionManager dbConnectionManager = ConnectionManagerImpl.getInstance();
    private static final CourseDAOImpl courseDAOImpl = CourseDAOImpl.getInstance();
    private static final TeacherDAO teacherDAO = TeacherDAOImpl.getInstance();
    private static CourseTeacherDAOImpl instance = null;

    static final String SAVE_SQL = "INSERT INTO school.courses_teachers(course_id, teacher_id) VALUES(?, ?)";
    static final String UPDATE_SQL = "UPDATE school.courses_teachers " +
            "SET course_id = ?, teacher_id = ? WHERE id = ?";
    static final String DELETE_SQL = "DELETE FROM school.courses_teachers " +
            "course_id = ? AND teacher_id = ?";
    static final String FIND_BY_ID_SQL = "SELECT id, course_id, teacher_id FROM school.courses_teachers " +
            "WHERE id = ? LIMIT 1";
    static final String FIND_ALL_SQL = "SELECT id, course_id, teacher_id FROM school.courses_teachers";
    static final String FIND_ALL_BY_COURSE_ID_SQL = "SELECT id, course_id, teacher_id " +
            "FROM school.courses_teachers WHERE course_id = ?";
    static final String FIND_ALL_BY_TEACHER_ID_SQL = "SELECT id, course_id, teacher_id " +
            "FROM school.courses_teachers WHERE teacher_id = ?";
    static final String FIND_ALL_BY_COURSE_ID_AND_TEACHER_ID = "SELECT id, course_id, teacher_id " +
            "FROM school.courses_teachers WHERE course_id = ? AND teacher_id = ? LIMIT 1";
    static final String DELETE_BY_COURSE_ID_SQL = "DELETE FROM school.courses_teachers WHERE course_id = ?";
    static final String DELETE_BY_TEACHER_ID_SQL = "DELETE FROM school.courses_teachers WHERE teacher_id = ?";
    static final String EXIST_BY_ID_SQL = "SELECT exists (SELECT 1" +
            "FROM school.courses_teachers WHERE id = ? LIMIT 1)";

    private CourseTeacherDAOImpl() {
    }

    public static synchronized CourseTeacherDAOImpl getInstance() {
        if (instance == null) {
            instance = new CourseTeacherDAOImpl();
        }
        return instance;
    }

    private static CourseTeacher createCourseTeacher(ResultSet resultSet) throws SQLException {
        CourseTeacher courseTeacher;
        courseTeacher = new CourseTeacher(
                resultSet.getLong("id"),
                resultSet.getLong("course_id"),
                resultSet.getLong("teacher_id")
        );
        return courseTeacher;
    }

    @Override
    public CourseTeacher save(CourseTeacher coursesTeachers) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {

            preparedStatement.setLong(1, coursesTeachers.getCourseId());
            preparedStatement.setLong(2, coursesTeachers.getTeacherId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                coursesTeachers = new CourseTeacher(
                        resultSet.getLong("id"),
                        coursesTeachers.getCourseId(),
                        coursesTeachers.getTeacherId()
                );
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return coursesTeachers;
    }

    @Override
    public void update(CourseTeacher coursesTeachers) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setLong(1, coursesTeachers.getCourseId());
            preparedStatement.setLong(2, coursesTeachers.getTeacherId());
            preparedStatement.setLong(3, coursesTeachers.getId());

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
    public boolean deleteByCourseId(Long courseId) {
        boolean deleteResult;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_COURSE_ID_SQL);) {

            preparedStatement.setLong(1, courseId);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return deleteResult;
    }

    @Override
    public boolean deleteByTeacherId(Long teacherId) {
        boolean deleteResult;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_TEACHER_ID_SQL);) {

            preparedStatement.setLong(1, teacherId);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return deleteResult;
    }

    @Override
    public Optional<CourseTeacher> findById(Long id) {
        CourseTeacher courseTeacher = null;
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                courseTeacher = createCourseTeacher(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return Optional.ofNullable(courseTeacher);
    }

    @Override
    public List<CourseTeacher> findAll() {
        List<CourseTeacher> courseTeacherList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseTeacherList.add(createCourseTeacher(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return courseTeacherList;
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

    @Override
    public List<CourseTeacher> findAllByCourseId(Long courseId) {
        List<CourseTeacher> courseTeacherList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_COURSE_ID_SQL)) {

            preparedStatement.setLong(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseTeacherList.add(createCourseTeacher(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return courseTeacherList;
    }

    @Override
    public List<CourseTeacher> findAllByTeacherId(Long teacherId) {
        List<CourseTeacher> courseTeacherList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_TEACHER_ID_SQL)) {

            preparedStatement.setLong(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseTeacherList.add(createCourseTeacher(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return courseTeacherList;
    }

    @Override
    public List<Course> findCoursesByTeacherId(Long teacherId) {
        List<Course> courseList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_TEACHER_ID_SQL)) {

            preparedStatement.setLong(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long courseId = resultSet.getLong("course_id");
                Optional<Course> optionalCourse = courseDAOImpl.findById(courseId);
                if (optionalCourse.isPresent()) {
                    courseList.add(optionalCourse.get());
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return courseList;
    }

    @Override
    public List<Teacher> findTeachersByCourseId(Long courseId) {
        List<Teacher> teacherList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_COURSE_ID_SQL)) {

            preparedStatement.setLong(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long teacherId = resultSet.getLong("teacher_id");
                Optional<Teacher> optionalTeacher = teacherDAO.findById(teacherId);
                if (optionalTeacher.isPresent()) {
                    teacherList.add(optionalTeacher.get());
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return teacherList;
    }

    @Override
    public Optional<CourseTeacher> findByCourseIdAndTeacherId(Long courseId, Long teacherId) {
        Optional<CourseTeacher> courseTeacher = Optional.empty();
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_COURSE_ID_AND_TEACHER_ID)) {

            preparedStatement.setLong(1, courseId);
            preparedStatement.setLong(2, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                courseTeacher = Optional.of(createCourseTeacher(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return courseTeacher;
    }
}
