package org.example.servlet.mapper;

import org.example.model.Course;
import org.example.servlet.dto.CourseIncomingDto;
import org.example.servlet.dto.CourseOutGoingDto;
import org.example.servlet.dto.CourseSmallOutGoingDto;
import org.example.servlet.dto.CourseUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CourseDtoMapper {
    Course map(CourseIncomingDto courseIncomingDto);

    Course map(CourseUpdateDto courseUpdateDto);

    CourseSmallOutGoingDto mapSmallCourse(Optional<Course> optionalCourse);

    CourseOutGoingDto map(Course course);

    List<CourseOutGoingDto> map(List<Course> courseList);

    List<Course> mapUpdateList(List<CourseUpdateDto> courseList);
}
