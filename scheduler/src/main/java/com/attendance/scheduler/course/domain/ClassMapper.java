package com.attendance.scheduler.course.domain;

import com.attendance.scheduler.course.dto.ClassDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassDTO toClassDTO(ClassEntity classEntity);
}