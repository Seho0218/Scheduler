package com.attendance.scheduler.Admin;

import com.attendance.scheduler.Admin.Dto.ApproveTeacherDTO;
import com.attendance.scheduler.Admin.Dto.ChangeTeacherDTO;
import com.attendance.scheduler.Infra.Dto.EditEmailDTO;
import com.attendance.scheduler.Infra.Dto.EmailDTO;
import com.attendance.scheduler.Student.StudentEntity;
import com.attendance.scheduler.Student.StudentRepository;
import com.attendance.scheduler.Teacher.Dto.TeacherDTO;
import com.attendance.scheduler.Teacher.TeacherEntity;
import com.attendance.scheduler.Teacher.TeacherMapper;
import com.attendance.scheduler.Teacher.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentRepository studentRepository;

    @Override
    public List<TeacherDTO> getTeacherList() {
        List<TeacherEntity> optionalTeacherEntity = teacherRepository.findAll();
        return optionalTeacherEntity.stream()
                .map(teacherMapper::toTeacherDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<EmailDTO> findAdminEmailByID(EmailDTO emailDTO) {
        Optional<AdminEntity> adminAccount = Optional.ofNullable(adminRepository
                .findByUsernameIs(emailDTO.getUsername()));
        return adminAccount.map(adminEntity -> EmailDTO.builder()
                .username(adminEntity.getUsername())
                .email(adminEntity.getEmail())
                .build());
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
    public void changeExistTeacher(ChangeTeacherDTO changeTeacherDTO) {
        Optional<StudentEntity> studentEntityById = studentRepository
                .findStudentEntityById(changeTeacherDTO.getStudentId());
        Optional<TeacherEntity> teacherEntityById = teacherRepository
                .findTeacherEntityById(changeTeacherDTO.getTeacherId());

        TeacherEntity teacherEntity = teacherEntityById.get();
        StudentEntity studentEntity = studentEntityById.get();

        studentEntity.setTeacherEntity(teacherEntity);
        studentRepository.save(studentEntity);
    }

    @Override
    @Transactional
    public void deleteTeacherAccount(ApproveTeacherDTO approveTeacherDTO) {
        teacherRepository.deleteByUsernameIs(approveTeacherDTO.getUsername());
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