package com.attendance.scheduler.Course;

import com.attendance.scheduler.Course.Dto.ClassDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassDTO toClassDTO(ClassEntity classEntity);
}