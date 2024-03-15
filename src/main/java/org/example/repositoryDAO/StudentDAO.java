package org.example.repositoryDAO;

import org.example.model.Course;
import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;

public interface StudentDAO extends  DAO<Student, Long>{

    void updateByCourseId(Long courseId);
    List<Student> findAllByCourseId(Long courseId);
}
