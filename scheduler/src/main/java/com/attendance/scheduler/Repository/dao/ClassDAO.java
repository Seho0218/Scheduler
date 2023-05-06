package com.attendance.scheduler.Repository.dao;

import com.attendance.scheduler.Dto.ClassDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassDAO {

    void save(ClassDTO classDTO);
}
