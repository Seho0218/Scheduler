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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public List<TeacherDTO> getTeacherList() {
        List<TeacherEntity> optionalTeacherEntity = teacherRepository.findAll();
        return optionalTeacherEntity.stream()
                .map(teacherMapper::toTeacherDTO)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void approveAuth(ApproveTeacherDTO approveTeacherDTO) {
        Optional<TeacherEntity> optionalTeacherEntity = teacherRepository
                .findByUsernameIs(approveTeacherDTO.getUsername());
        optionalTeacherEntity.ifPresent(teacherEntity -> {
            teacherEntity.updateApprove(true);
            teacherRepository.save(teacherEntity);
        });
    }

    @Override
    @Transactional
    public void revokeAuth(ApproveTeacherDTO approveTeacherDTO) {
        Optional<TeacherEntity> optionalTeacherEntity = teacherRepository
                .findByUsernameIs(approveTeacherDTO.getUsername());
        approveTeacherDTO.setApproved(false);
        optionalTeacherEntity.ifPresent(teacherEntity -> {
            teacherEntity.updateApprove(false);
            teacherRepository.save(teacherEntity);
        });
    }
}
