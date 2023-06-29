package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Mapper.TeacherMapper;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public List<TeacherDTO> findAllTeacherAccount() {
        List<TeacherEntity> allTeacherAccount = teacherRepository.findAll();
        return allTeacherAccount.stream()
                .map(teacherMapper::toTeacherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void approveTeacher(ApproveTeacherDTO approveTeacherDTO) {
        TeacherEntity byTeacherIdIs = teacherRepository
                .findByTeacherIdIs(approveTeacherDTO.getTeacherId());
        byTeacherIdIs.updateApprove(approveTeacherDTO.isApproved());
        teacherRepository.save(byTeacherIdIs);
    }
}
