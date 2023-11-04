package com.attendance.scheduler.admin.application;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.dto.*;
import com.attendance.scheduler.admin.repository.AdminRepository;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.repository.StudentRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.teacher.dto.TeacherDTO;
import com.attendance.scheduler.teacher.repository.TeacherMapper;
import com.attendance.scheduler.teacher.repository.TeacherRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;

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
    public boolean emailConfirmation(FindPasswordDTO findPasswordDTO) {
        return adminRepository.existsByEmail(findPasswordDTO.getEmail());
    }

    @Override
    @Transactional
    public void setupAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session) {

        String teacherId = findPasswordDTO.getUsername();
        String email = findPasswordDTO.getEmail();

        StringBuilder authNum = new StringBuilder();

        for(int i=0;i<6;i++) {
            authNum.append((int) (Math.random() * 10));
        }

        log.info("인증번호={}", authNum);
        log.info("이메일={} ", email);

        sendAuthNum(email, authNum.toString());

        Map<String, Object> authNumMap = new HashMap<>();

        LocalDateTime expiryDateTime= LocalDateTime.now().plusMinutes(5);

        authNumMap.put(teacherId, authNum.toString());
        authNumMap.put("endTime", expiryDateTime);

        session.setMaxInactiveInterval(300);
        session.setAttribute(teacherId, authNumMap);
    }

    public void sendAuthNum(String userEmail, String authNum) {
        SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
        simpleMailMessage.setFrom("dev.hsayho@gmail.com");
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject("비밀번호 찾기 인증번호");

        String text = "인증번호는 " + authNum + "입니다";

        simpleMailMessage.setText(text);
        new Thread(() -> mailSender.send(simpleMailMessage)).start();
    }

    @Override
    @Transactional
    public void initializePassword(PwdEditDTO pwdEditDTO) {
        final String encodePassword = passwordEncoder.encode(pwdEditDTO.getPassword());

        pwdEditDTO.setPassword(encodePassword);

        AdminEntity adminEntity = adminRepository.findByUsernameIs(pwdEditDTO.getUsername());
        adminRepository.save(adminEntity);
    }

    @Override
    @Transactional
    public void updateEmail(EditEmailDTO editEmailDTO) {
        AdminEntity adminEntity = adminRepository
                .findByUsernameIs(editEmailDTO.getUsername());
        adminEntity.updateEmail(editEmailDTO);
        adminRepository.save(adminEntity);
    }
}