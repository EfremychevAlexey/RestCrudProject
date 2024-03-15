package org.example.servlet.mapper;

import org.example.model.Teacher;
import org.example.servlet.dto.TeacherIncomingDto;
import org.example.servlet.dto.TeacherOutGoingDto;
import org.example.servlet.dto.TeacherSmallOutGoingDto;
import org.example.servlet.dto.TeacherUpdateDto;

import java.util.List;

public interface TeacherDtoMapper {

    Teacher map(TeacherIncomingDto teacherIncomingDto);

    TeacherOutGoingDto map(Teacher teacher);

    TeacherSmallOutGoingDto mapSmallOutGoing(Teacher teacher);

    Teacher map (TeacherUpdateDto teacherUpdateDto);

    List<TeacherOutGoingDto> map(List<Teacher> teacherList);

    List<TeacherSmallOutGoingDto> mapSmallOutGoingList(List<Teacher> teacherList);

    List<Teacher> mapUpdateList(List<TeacherUpdateDto> teacherList);
}
