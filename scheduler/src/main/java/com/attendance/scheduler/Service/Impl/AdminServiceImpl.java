package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.EditEmailDTO;
import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Mapper.TeacherMapper;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
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
    public List<EmailDTO> findAdminEmailByID(EmailDTO emailDTO) {
        AdminEntity adminAccount = adminRepository
                .findByUsernameIs(emailDTO.getUsername());
        EmailDTO build = EmailDTO.builder()
                .username(adminAccount.getUsername())
                .email(adminAccount.getEmail())
                .build();
        return Collections.singletonList(build);
    }

    @Override
    @Transactional
    public void grantAuth(ApproveTeacherDTO approveTeacherDTO) {
        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(approveTeacherDTO.getUsername());
        teacherEntity.updateApprove(true);
        teacherRepository.save(teacherEntity);
    }

    @Override
    @Transactional
    public void revokeAuth(ApproveTeacherDTO approveTeacherDTO) {
        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(approveTeacherDTO.getUsername());
        teacherEntity.updateApprove(false);
        teacherRepository.save(teacherEntity);
    }

    @Override
    @Transactional
    public void updateEmail(EditEmailDTO editEmailDTO) {
        AdminEntity byUsernameIs = adminRepository
                .findByUsernameIs(editEmailDTO.getUsername());
        byUsernameIs.updateEmail(editEmailDTO.getEmail());
        adminRepository.save(byUsernameIs);
    }
}