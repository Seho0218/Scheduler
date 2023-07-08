package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Mapper.TeacherMapper;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public List<TeacherDTO> getTeacherList() {
        List<TeacherEntity> byTeacherIdIs = teacherRepository.findAll();
        return byTeacherIdIs.stream()
                .map(teacherMapper::toTeacherDTO)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void approveAuth(ApproveTeacherDTO approveTeacherDTO) {
        TeacherEntity byTeacherIdIs = teacherRepository
                .findByTeacherIdIs(approveTeacherDTO.getTeacherId());
        approveTeacherDTO.setApproved(true);
        byTeacherIdIs.updateApprove(approveTeacherDTO.isApproved());
        teacherRepository.save(byTeacherIdIs);
    }

    @Override
    @Transactional
    public void revokeAuth(ApproveTeacherDTO approveTeacherDTO) {
        TeacherEntity byTeacherIdIs = teacherRepository
                .findByTeacherIdIs(approveTeacherDTO.getTeacherId());
        approveTeacherDTO.setApproved(false);
        byTeacherIdIs.updateApprove(approveTeacherDTO.isApproved());
        teacherRepository.save(byTeacherIdIs);
    }
}
