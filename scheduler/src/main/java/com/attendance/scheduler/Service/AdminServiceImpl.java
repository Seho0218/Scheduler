package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.DeleteClassDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TeacherRepository teacherRepository;
    private final ClassTableRepository classTableRepository;

    @Override
    public void joinTeacher(JoinTeacherDTO joinTeacherDTO) {
        teacherRepository.save(joinTeacherDTO.toEntity());
    }

    @Override
    public TeacherDTO findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO) {
        return teacherRepository.findByTeacherIdIs(joinTeacherDTO.getTeacherId());
    }

    @Override
    public void deleteClass(DeleteClassDTO deleteClassDTO) {
        classTableRepository.deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());
    }
}
