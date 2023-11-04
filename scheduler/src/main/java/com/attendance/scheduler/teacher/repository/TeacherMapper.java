package com.attendance.scheduler.teacher.repository;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.TeacherDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDTO toTeacherDTO(TeacherEntity teacherEntity);
}