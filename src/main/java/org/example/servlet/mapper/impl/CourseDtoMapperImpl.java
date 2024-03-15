package org.example.servlet.mapper.impl;

import org.example.model.Course;
import org.example.servlet.dto.*;
import org.example.servlet.mapper.CourseDtoMapper;
import org.example.servlet.mapper.StudentDtoMapper;
import org.example.servlet.mapper.TeacherDtoMapper;

import java.util.List;
import java.util.Optional;

public class CourseDtoMapperImpl implements CourseDtoMapper {
    private static final TeacherDtoMapper teacherDtoMapper = TeacherDtoMapperImpl.getInstance();
    private static final StudentDtoMapper studentDtoMapper = StudentDtoMapperImpl.getInstance();

    private static CourseDtoMapper instance;

    public CourseDtoMapperImpl() {
    }

    public static synchronized CourseDtoMapper getInstance() {
        if (instance == null) {
            instance = new CourseDtoMapperImpl();
        }
        return instance;
    }


    @Override
    public Course map(CourseIncomingDto courseIncomingDto) {
        return new Course(
                null,
                courseIncomingDto.getName(),
                null,
                null
        );
    }

    @Override
    public CourseOutGoingDto map(Course course) {
        return new CourseOutGoingDto(
                course.getId(),
                course.getName(),
                studentDtoMapper.mapSmallOutGoingList(course.getStudents()),
                teacherDtoMapper.mapSmallOutGoingList(course.getTeachers())
        );
    }

    @Override
    public Course map(CourseUpdateDto courseUpdateDto) {
        return new Course(
                courseUpdateDto.getId(),
                courseUpdateDto.getName(),
                null,
                null
        );
    }

    @Override
    public CourseSmallOutGoingDto mapSmallCourse(Optional<Course> optionalCourse) {
        CourseSmallOutGoingDto courseSmallOutGoingDto = null;
        Course course = null;

        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
            return new CourseSmallOutGoingDto(
                    course.getId(),
                    course.getName()
            );
        } else {
            return courseSmallOutGoingDto;
        }
    }

    @Override
    public List<CourseOutGoingDto> map(List<Course> courseList) {
        return courseList.stream().map(this::map).toList();
    }

    @Override
    public List<Course> mapUpdateList(List<CourseUpdateDto> courseList) {
        return null;
    }
}