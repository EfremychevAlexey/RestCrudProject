package org.example.jdbc.dao;

import org.example.model.Course;
import org.example.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentDAOInterface extends  DAO<Student, Integer>{

    ArrayList<Student> readAllByCourse(Course course);
}
