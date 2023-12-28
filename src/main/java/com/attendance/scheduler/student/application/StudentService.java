package com.attendance.scheduler.student.application;

import com.attendance.scheduler.comment.dto.CommentDTO;

public interface StudentService {

    boolean existStudentEntityByStudentName(String studentName);
    boolean existStudentEntityByStudentNameAndStudentParentPhoneNumber(CommentDTO commentDTO);
}
