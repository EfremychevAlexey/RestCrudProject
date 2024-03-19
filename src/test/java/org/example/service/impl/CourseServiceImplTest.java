package org.example.service.impl;

import org.example.model.Course;
import org.example.repositoryDAO.CourseDAO;
import org.example.repositoryDAO.StudentDAO;
import org.example.repositoryDAO.TeacherDAO;
import org.example.repositoryDAO.impl.CourseDAOImpl;
import org.example.service.CourseService;
import org.example.servlet.dto.CourseIncomingDto;
import org.example.servlet.dto.CourseOutGoingDto;
import org.example.servlet.mapper.CourseDtoMapper;
import org.example.servlet.mapper.impl.CourseDtoMapperImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;

class CourseServiceImplTest {

    private static CourseService courseService;
    private static CourseDAO mockCourseDao;
    private static CourseDAOImpl oldCourseInstance;

    private static void setMock(CourseDAO mock) {
        try {
            Field instance = CourseDAOImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldCourseInstance = (CourseDAOImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockCourseDao = Mockito.mock(CourseDAO.class);
        setMock(mockCourseDao);
        courseService = CourseServiceImpl.getInstance();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = CourseDAOImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldCourseInstance);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(mockCourseDao);
    }

    @Test
    void save() {
        Long expectedId = 1L;

        CourseIncomingDto dto = new CourseIncomingDto("f1 course");
        Course course = new Course(expectedId, "f1 course", List.of(), List.of());

        Mockito.doReturn(course).when(mockCourseDao).save(Mockito.any(Course.class));

        CourseOutGoingDto result = courseService.save(dto);

        Assertions.assertEquals(expectedId, result.getId());
    }
























}
