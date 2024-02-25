package org.example;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        DBInit.initDB();

        List<Student> students = CRUDUtils.getStudents("SELECT * FROM myDB.students");

        students.forEach(System.out::println);
    }
}