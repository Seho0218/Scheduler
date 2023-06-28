package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;

public interface AdminService {

    List<TeacherDTO> findAllTeacherAccount();
}
