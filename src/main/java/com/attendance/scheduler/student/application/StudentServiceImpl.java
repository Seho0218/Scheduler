package com.attendance.scheduler.student.application;

import com.attendance.scheduler.comment.dto.CommentDTO;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import com.attendance.scheduler.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentRepository studentRepository;

    @Override
    public boolean existStudentEntityByStudentName(String studentName) {
        return studentJpaRepository.existsByStudentNameIs(studentName);
    }

    @Override
    public boolean existStudentEntityByStudentNameAndStudentParentPhoneNumber(CommentDTO commentDTO) {
        return studentRepository.existStudentEntityByStudentNameAndStudentParentPhoneNumber(commentDTO);
    }
}
