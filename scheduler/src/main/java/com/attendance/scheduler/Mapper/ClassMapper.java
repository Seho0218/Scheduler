package com.attendance.scheduler.Mapper;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassDTO toClassDTO(ClassEntity classEntity);
}