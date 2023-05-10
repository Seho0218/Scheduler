package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;

import java.util.List;

public interface AdminService {

    List<ClassDTO> findClassTable();
}
