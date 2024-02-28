package org.example;

import org.example.jdbc.DBInit;
import org.example.jdbc.dao.CoursesDAO;
import org.example.jdbc.dao.StudentsDAO;
import org.example.jdbc.dao.TeacherDAO;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Teacher;

import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        DBInit.initDB();
        StudentsDAO studentsDAO = new StudentsDAO();
////        Добавляем студента
//        System.out.println("------------Добавляем студента: Денис Асамбаев курс = 2, Petr Ivanov курс = 1------------");
//        studentsDAO.create(new Student("Денис Асамбаев", 2));
//        studentsDAO.create(new Student("Petr Ivanov", 1));
//
//        for (Student s : studentsDAO.readAll()) {
//            System.out.println(s);
//        }
//
////        Получаем студентов
//        System.out.println("------------Получаем студентов: Alexey Efremychev------------");
//        ArrayList<Student> students = studentsDAO.read("Alexey Efremychev");
//        for (Student s : students) {
//            System.out.println(s);
//        }
//
//        System.out.println("------------Получаем студентов: id=2------------");
//        Student student = studentsDAO.read(2);
//        System.out.println(student);
//
////        Обновляем студента
//        System.out.println("------------Обновляем студента id=1, courxeId=5------------");
//        studentsDAO.update(1, 5);
//
//        for (Student s : studentsDAO.readAll()) {
//            System.out.println(s);
//        }
//
////        Удаляем студента
//        System.out.println("------------Удаляем студента id = 1------------");
//        studentsDAO.delete(1);
//        for (Student s : studentsDAO.readAll()) {
//            System.out.println(s);
//        }





//        List<Student> students = CRUDUtils.getStudents("SELECT * FROM school.students");
//        students.forEach(System.out::println);

        CoursesDAO coursesDAO = new CoursesDAO();
////        Добавляем курс
//        System.out.println("------------Добавляем курс Wed-design------------");
//        Course course = new Course("Wed-design");
//        System.out.println(coursesDAO.create(course));
//        for (Course c : coursesDAO.readAll()) {
//            System.out.println(c);
//        }
//
////        Получаем курс
//        System.out.println("------------Получаем курс course_name = Java------------");
//        System.out.println(coursesDAO.read("Java"));
//
//        System.out.println("------------Получаем курс id = 4------------");
//        System.out.println(coursesDAO.read(4));
//
////        Обновляем курс
//        System.out.println("------------Обновляем курс id = 1, course_name = Java-разработка------------");
//        coursesDAO.update(1, "Java-разработка");
//        for (Course c : coursesDAO.readAll()) {
//            System.out.println(c);
//        }
//
////        Удаляем курс
//        System.out.println("------------Удаляем курс id = 6------------");
//        coursesDAO.delete(6);
//        for (Course c : coursesDAO.readAll()) {
//            System.out.println(c);
//        }

        TeacherDAO teacherDAO = new TeacherDAO();
//        ////        Добавляем курс
//        System.out.println("------------Добавляем учителя Васильев Алексендр------------");
//        Teacher teacher = new Teacher("Васильев Алексендр");
//        System.out.println(teacherDAO.create(teacher));
//        for (Teacher c : teacherDAO.readAll()) {
//            System.out.println(c);
//        }
//
////        Получаем курс
//        System.out.println("------------Получаем учителя teacher_name = Дудин Виктор------------");
//        System.out.println(teacherDAO.read("Дудин Виктор"));
//
//        System.out.println("------------Получаем учителя id = 2------------");
//        System.out.println(teacherDAO.read(2));
//
////        Обновляем курс
//        System.out.println("------------Обновляем учителя id = 1, name = Murzich Roman------------");
//        teacherDAO.update(1, "Murzich Roman");
//        for (Teacher c : teacherDAO.readAll()) {
//            System.out.println(c);
//        }
//
////        Удаляем курс
//        System.out.println("------------Удаляем учителя id = 6------------");
//        teacherDAO.delete(6);
//        for (Teacher c : teacherDAO.readAll()) {
//            System.out.println(c);
//        }

    }
}