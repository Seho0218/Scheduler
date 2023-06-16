package com.attendance.scheduler.Mapper;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface JoinTeacherMapper {
    JoinTeacherDTO toJoinTeacherDTO(TeacherEntity teacherEntity);
}