package org.example;

import org.example.jdbc.DBConnection;
import org.example.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {

    public static List<Student> getStudents(String query) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("student_name");
                int courseId = resultSet.getInt("course_id");

                students.add(new Student(name, courseId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
