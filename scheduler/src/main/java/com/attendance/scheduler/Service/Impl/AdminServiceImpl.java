package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Admin.EmailEditDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final AdminRepository adminRepository;

    @Override
    public List<TeacherDTO> getTeacherList() {
        List<TeacherEntity> optionalTeacherEntity = teacherRepository.findAll();
        return optionalTeacherEntity.stream()
                .map(teacherMapper::toTeacherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO findAdminAccountById(AdminDTO adminDTO) {
        AdminEntity byUsernameIs = adminRepository
                .findByUsernameIs(adminDTO.getUsername());
        AdminDTO resultDTO = new AdminDTO();
        resultDTO.setUsername(byUsernameIs.getUsername());
        resultDTO.setAdminEmail(byUsernameIs.getAdminEmail());
        return resultDTO;
    }

    @Override
    @Transactional
    public void approveAuth(ApproveTeacherDTO approveTeacherDTO) {
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
        approveTeacherDTO.setApproved(false);
        teacherEntity.updateApprove(false);
        teacherRepository.save(teacherEntity);
    }

    @Override
    @Transactional
    public void updateEmail(EmailEditDTO emailEditDTO) {
        AdminEntity byUsernameIs = adminRepository
                .findByUsernameIs(emailEditDTO.getUsername());
        byUsernameIs.updateEmail(emailEditDTO.getAdminEmail());
        adminRepository.save(byUsernameIs);
    }
}