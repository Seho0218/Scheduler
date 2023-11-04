package com.attendance.scheduler.Teacher;

import com.attendance.scheduler.Teacher.Dto.TeacherDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDTO toTeacherDTO(TeacherEntity teacherEntity);
}