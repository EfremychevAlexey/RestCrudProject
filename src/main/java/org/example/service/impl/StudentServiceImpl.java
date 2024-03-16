package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Student;
import org.example.repositoryDAO.CourseDAO;
import org.example.repositoryDAO.StudentDAO;
import org.example.repositoryDAO.impl.CourseDAOImpl;
import org.example.repositoryDAO.impl.StudentDAOImpl;
import org.example.service.StudentService;
import org.example.servlet.dto.StudentIncomingDto;
import org.example.servlet.dto.StudentOutGoingDto;
import org.example.servlet.dto.StudentUpdateDto;
import org.example.servlet.mapper.StudentDtoMapper;
import org.example.servlet.mapper.impl.StudentDtoMapperImpl;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private static StudentService instance;
    private static final StudentDAO studentDAO = StudentDAOImpl.getInstance();
    private static final StudentDtoMapper studentDtoMapper = StudentDtoMapperImpl.getInstance();

    private StudentServiceImpl() {
    }

    public static synchronized StudentService getInstance() {
        if (instance == null) {
            instance = new StudentServiceImpl();
        }
        return instance;
    }

    /**
     * Проверяем наличие записи в таблице по id
     * Если ее нет выбрасываем исключение
     * @param studentId
     * @throws NotFoundException
     */
    private void checkExistStudent(Long studentId) throws NotFoundException {
        if (!studentDAO.existById(studentId)) {
            throw new NotFoundException("Student not found.");
        }
    }

    /**
     * @return
     * Преобразуем полученного StudentIncomingDto в модель Student
     * Сохраняем модель Student в бд, получаем модель Student с id
     * Преобразуем модель Student в StudentOutGoingDto и возвращаем её.
     * @param studentIncomingDto
     */
    @Override
    public StudentOutGoingDto save(StudentIncomingDto studentIncomingDto) {
        Student student = studentDtoMapper.map(studentIncomingDto);
        student = studentDAO.save(student);
        return studentDtoMapper.map(student);
    }

    /**
     * Проверяем, есть ли в таблице students запись с id = studentUpdateDto.getId.
     * Если есть, преобразуем StudentUpdateDto в модель Student
     * Обновляем запись в бд.
     * Если запись в таблице отсутствует, выбрасываем исключении с соответствующим сообщением
     * @param studentUpdateDto
     * @throws NotFoundException
     */
    @Override
    public void update(StudentUpdateDto studentUpdateDto) throws NotFoundException {
        checkExistStudent(studentUpdateDto.getId());
        studentDAO.update(studentDtoMapper.map(studentUpdateDto));
    }

    /**
     *
     * Получаем модель Student из таблицы students по id
     * Если записи с таким id в таблице нет, выбрасываем исключение с соответствующим сообщением.
     * Если есть преобразуем Student в StudentOutGoingDto и возвращаем его
     * @param studentId
     * @return
     * @throws NotFoundException
     */
    @Override
    public StudentOutGoingDto findById(Long studentId) throws NotFoundException {
        Student student = studentDAO.findById(studentId).orElseThrow(() ->
                new NotFoundException("Student not found."));
        return studentDtoMapper.map(student);
    }

    /**
     * Получаем все записи из таблицы students,
     * преобразуем в StudentOutGoingDto и возвращаем список
     * @return
     */
    @Override
    public List<StudentOutGoingDto> findAll() {
        List<Student> allStudent = studentDAO.findAll();
        return studentDtoMapper.map(allStudent);
    }

    /**
     * Проверяем наличие записи в таблице students по id
     * Если записи с таким id в таблице нет, выбрасываем исключение с соответствующим сообщением.
     * Иначе удаляем запись
     * @param studentId
     * @throws NotFoundException
     */
    @Override
    public void delete(Long studentId) throws NotFoundException {
        if (studentDAO.existById(studentId)) {
            studentDAO.deleteById(studentId);
        } else {
            throw  new NotFoundException("Student not found.");
        }
    }
}
