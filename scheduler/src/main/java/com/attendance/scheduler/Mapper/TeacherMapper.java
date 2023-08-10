package com.attendance.scheduler.Mapper;

import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDTO toTeacherDTO(TeacherEntity teacherEntity);
}