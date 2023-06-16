package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.DeleteClassDTO;

public interface AdminService {

    // 수업 삭제
    void deleteClass(DeleteClassDTO deleteClassDTO);

    //TODO 관리자 회원 가입
}
