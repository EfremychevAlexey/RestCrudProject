package org.example.jdbc.dao;

import org.example.jdbc.DBConnection;
import org.example.jdbc.dao.DAOInterface.DAO;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CoursesDAO implements DAO<Course, Integer> {

    static final String CREATE = "INSERT INTO school.courses(course_name) VALUES(?) RETURNING id";
    static final String READ = "SELECT " +
            "c.id, c.course_name, t.id AS teacher_id, t.teacher_name, s.id AS student_id, s.student_name " +
            "FROM school.teachers AS t JOIN school.courses_teachers AS ct ON ct.teacher_id = t.id " +
            "JOIN school.courses AS c ON c.id = ct.course_id " +
            "LEFT JOIN school.students AS s ON s.course_id = c.id " +
            "WHERE c.id = ?";
    static final String UPDATE = "UPDATE school.courses SET course_name = ? WHERE id = ? RETURNING id";
    static final String DELETE = "DELETE FROM school.courses WHERE id = ? AND course_name = ? RETURNING id";

    @Override
    public int create(Course course) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            preparedStatement.setString(1, course.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("id");
                course.setId(result);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course read(Integer id) {
        Course course = new Course();
        course.setId(id);

        HashMap<Integer, Teacher> teacherHashMap = new HashMap<>();
        HashMap<Integer, Student> studentHashMap = new HashMap<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (course.getName() == null) {
                    course.setName(resultSet.getString("course_name"));
                }

                int teacherId = resultSet.getInt("teacher_id");
                if (!teacherHashMap.containsKey(teacherId) && teacherId != 0) {
                    teacherHashMap.put(teacherId, new TeacherDAO().read(teacherId));
                }

                int studentId = resultSet.getInt("student_id");
                if (!studentHashMap.containsKey(studentId) && studentId != 0) {
                    studentHashMap.put(studentId, new StudentsDAO().read(studentId));
                }
            }

            ArrayList<Teacher> teachers = new ArrayList<>(teacherHashMap.values());
            course.setTeachers(teachers);

            ArrayList<Student> students = new ArrayList<>(studentHashMap.values());
            course.setStudents(students);

            return course;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int update(Course course) {
        int result = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getId());
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
    public int delete(Course course) {
        int result = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, course.getId());
            preparedStatement.setString(2, course.getName());
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
