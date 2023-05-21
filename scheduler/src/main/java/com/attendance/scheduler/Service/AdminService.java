package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.DeleteClassDTO;

import java.util.List;

public interface AdminService {

    // 수업 유무 조회
    List<ClassDTO> findClassTable();

    // 수업 삭제
    void deleteClass(DeleteClassDTO deleteClassDTO);
}
