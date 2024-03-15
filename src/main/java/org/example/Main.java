package org.example;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.exception.NotFoundException;
import org.example.model.Course;
import org.example.model.Student;
import org.example.repositoryDAO.CourseDAO;
import org.example.repositoryDAO.StudentDAO;
import org.example.repositoryDAO.impl.CourseDAOImpl;
import org.example.repositoryDAO.impl.StudentDAOImpl;
import org.example.service.CourseService;
import org.example.service.StudentService;
import org.example.service.impl.CourseServiceImpl;
import org.example.service.impl.StudentServiceImpl;
import org.example.servlet.dto.CourseOutGoingDto;
import org.example.servlet.dto.StudentIncomingDto;
import org.example.servlet.dto.StudentOutGoingDto;
import org.example.servlet.mapper.CourseDtoMapper;
import org.example.servlet.mapper.StudentDtoMapper;
import org.example.servlet.mapper.impl.CourseDtoMapperImpl;
import org.example.servlet.mapper.impl.StudentDtoMapperImpl;
import org.example.util.DBInit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, InterruptedException, NotFoundException {

        ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        CourseService courseService = CourseServiceImpl.getInstance();
        StudentService studentService = StudentServiceImpl.getInstance();
        DBInit.init(connectionManager);
        CourseDAO courseDAO = CourseDAOImpl.getInstance();
        StudentDAO studentDAO = StudentDAOImpl.getInstance();
        StudentDtoMapper studentDtoMapper = StudentDtoMapperImpl.getInstance();
        CourseDtoMapper courseDtoMapper = CourseDtoMapperImpl.getInstance();

        System.out.println("------------------CourseOutGoingDto------------------");

        List<Course> courseList = courseDAO.findAll();
        for (Course c : courseList) {
            System.out.println(c);
        }

        System.out.println();
        System.out.println("------------------CourseOutGoingDto------------------");
        List<CourseOutGoingDto> courseOutGoingDtoList = courseService.findAll();
        for (CourseOutGoingDto c : courseOutGoingDtoList) {
            System.out.println(c);
        }

        System.out.println();
        System.out.println("------------------CourseOutGoingDto  by id------------------");
        Long courseId = Long.parseLong("1");
        CourseOutGoingDto courseOutGoingDto = courseService.findById(courseId);



        System.out.println();
        System.out.println("------------------Delete course by id------------------");
        Long courseIdDELETE = Long.parseLong("1");
        courseService.delete(courseIdDELETE);


        System.out.println();
        System.out.println("------------------Update course by id------------------");
        Course courseUpdate = new Course(3L, "Java1111", null, null);
        courseDAO.update(courseUpdate);

        System.out.println();
        System.out.println("------------------GetAll Student------------------");
        List<Student> studentList = studentDAO.findAll();
        List<StudentOutGoingDto> studentOutGoingDtoList = studentDtoMapper.map(studentList);

//        List<StudentOutGoingDto> studentOutGoingDtoList = studentService.findAll();

        for (StudentOutGoingDto s : studentOutGoingDtoList) {
            System.out.println(s);
        }


        System.out.println();
        System.out.println("------------------Add Student------------------");
        StudentIncomingDto studentIncomingDto = new StudentIncomingDto("Василий Петров");
        StudentOutGoingDto studentOutGoingDto = studentService.save(studentIncomingDto);
        System.out.println(studentOutGoingDto);








//        List<CourseOutGoingDto> courseDto = courseService.findAll();

//        for (CourseOutGoingDto c : courseDto) {
//            System.out.println(c);
//        }


//        DBInit.initDB();
//        StudentsDAO studentsDAO = StudentsDAO.getInstance();
//
////        Добавляем студента
//        System.out.println("------------Добавляем студента: Ирина Демидова------------");
//        Student student = new Student();
//        student.setName("Ирина Демидова");
//        student.setCourse(new Course("JAVA"));
//        student.getCourse().setId(1);
//        int id = studentsDAO.save(student);
//        student.setId(id);
//        System.out.println(id);
//
////        Получаем студентов
//        System.out.println("------------Получаем студента: id=2------------");
//        Student studentGet = studentsDAO.read(1);
//        System.out.println(studentGet);
//
////        Обновляем студента
//        System.out.println("------------Обновляем студента id=1, courxeId=5------------");
//        Student studentUpdate = studentsDAO.read(1);
//        studentUpdate.setCourse(new Course(1, "Java"));
//        System.out.println(studentsDAO.update(studentUpdate));
//
////        Удаляем студента
//        System.out.println("------------Удаляем студента id = 3------------");
//        System.out.println(studentsDAO.delete(student));
//
//
//        CourseDAOImpl coursesDAO = CourseDAOImpl.getInstance();
////        Добавляем курс
//        System.out.println("------------Добавляем курс Wed-design------------");
//        Course courseWeb = new Course("Wed-design");
//        System.out.println(coursesDAO.save(courseWeb));
//
////        Получаем курс
//        System.out.println("------------Получаем курс id = 1------------");
//        Course courseRead = coursesDAO.read(5);
//
//        System.out.println("Список учителей");
//        for (Teacher t : courseRead.getTeachers()) {
//            System.out.println(t);
//        }
//
//        System.out.println("Список студентов");
//        for (Student s : courseRead.getStudents()) {
//            System.out.println(s);
//        }
//
////        Обновляем курс
//        System.out.println("------------Обновляем курс id = 1, course_name = Java-разработка------------");
//        Course courseUpdate = new Course(1, "Java-разработка");
//        System.out.println(coursesDAO.update(courseUpdate));
//
//
////        Удаляем курс
//        System.out.println("------------Удаляем курс Web-дизайн------------");
//        System.out.println(coursesDAO.delete(courseWeb));
//
//
//        TeachersDAO teacherDAO = TeachersDAO.getInstance();
//
//        //        Добавляем учителя
//        System.out.println("------------Добавляем учителя Васильев Алексендр------------");
//        Teacher teacherNew = new Teacher("Васильев Алексендр");
//        System.out.println(teacherDAO.save(teacherNew));
//
////        Получаем учителя
//        System.out.println("------------Получаем учителя teacher_name = 1------------");
//        Teacher teacherREAD = teacherDAO.read(1);
//        for (Course c : teacherREAD.getCourses()) {
//            System.out.println(c);
//        }
//
////        Обновляем учителя
//        System.out.println("------------Обновляем учителя ------------");
//        Teacher teacherUpdate = new Teacher(1, "Демидов Дмитрий!!!!!!!!!");
//        System.out.println(teacherDAO.update(teacherUpdate));
//
////        Удаляем курс
//        System.out.println("------------Удаляем учителя teacherNew------------");
//        System.out.println(teacherDAO.delete(teacherNew));
    }
}