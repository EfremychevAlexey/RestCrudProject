package org.example;

import org.example.jdbc.DBInit;
import org.example.jdbc.dao.modelDAO.CoursesDAO;
import org.example.jdbc.dao.modelDAO.StudentsDAO;
import org.example.jdbc.dao.modelDAO.TeachersDAO;
import org.example.model.Course;
import org.example.model.Student;
import org.example.model.Teacher;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        DBInit.initDB();
        StudentsDAO studentsDAO = StudentsDAO.getInstance();

//        Добавляем студента
        System.out.println("------------Добавляем студента: Ирина Демидова------------");
        Student student = new Student();
        student.setName("Ирина Демидова");
        student.setCourse(new Course("JAVA"));
        student.getCourse().setId(1);
        int id = studentsDAO.create(student);
        student.setId(id);
        System.out.println(id);

//        Получаем студентов
        System.out.println("------------Получаем студента: id=2------------");
        Student studentGet = studentsDAO.read(1);
        System.out.println(studentGet);

//        Обновляем студента
        System.out.println("------------Обновляем студента id=1, courxeId=5------------");
        Student studentUpdate = studentsDAO.read(1);
        studentUpdate.setCourse(new Course(1, "Java"));
        System.out.println(studentsDAO.update(studentUpdate));

//        Удаляем студента
        System.out.println("------------Удаляем студента id = 3------------");
        System.out.println(studentsDAO.delete(student));


        CoursesDAO coursesDAO = CoursesDAO.getInstance();
//        Добавляем курс
        System.out.println("------------Добавляем курс Wed-design------------");
        Course courseWeb = new Course("Wed-design");
        System.out.println(coursesDAO.create(courseWeb));

//        Получаем курс
        System.out.println("------------Получаем курс id = 1------------");
        Course courseRead = coursesDAO.read(5);

        System.out.println("Список учителей");
        for (Teacher t : courseRead.getTeachers()) {
            System.out.println(t);
        }

        System.out.println("Список студентов");
        for (Student s : courseRead.getStudents()) {
            System.out.println(s);
        }

//        Обновляем курс
        System.out.println("------------Обновляем курс id = 1, course_name = Java-разработка------------");
        Course courseUpdate = new Course(1, "Java-разработка");
        System.out.println(coursesDAO.update(courseUpdate));


//        Удаляем курс
        System.out.println("------------Удаляем курс Web-дизайн------------");
        System.out.println(coursesDAO.delete(courseWeb));


        TeachersDAO teacherDAO = TeachersDAO.getInstance();

        //        Добавляем учителя
        System.out.println("------------Добавляем учителя Васильев Алексендр------------");
        Teacher teacherNew = new Teacher("Васильев Алексендр");
        System.out.println(teacherDAO.create(teacherNew));

//        Получаем учителя
        System.out.println("------------Получаем учителя teacher_name = 1------------");
        Teacher teacherREAD = teacherDAO.read(1);
        for (Course c : teacherREAD.getCourses()) {
            System.out.println(c);
        }

//        Обновляем учителя
        System.out.println("------------Обновляем учителя ------------");
        Teacher teacherUpdate = new Teacher(1, "Демидов Дмитрий!!!!!!!!!");
        System.out.println(teacherDAO.update(teacherUpdate));

//        Удаляем курс
        System.out.println("------------Удаляем учителя teacherNew------------");
        System.out.println(teacherDAO.delete(teacherNew));
    }
}