package com.attendance.scheduler.config;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Admin implements ApplicationRunner{

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<AdminEntity> optionalAdminEntity = Optional
                .ofNullable(adminRepository.findByUsernameIs("admin"));
        optionalAdminEntity.orElseGet(() -> adminRepository.save(
                AdminEntity.builder()
                        .username("admin")
                        .email("adminTest@gmail.com")
                        .password(passwordEncoder.encode("root123!@#"))
                        .build()));

        Optional<TeacherEntity> optionalTeacherEntity = Optional
                .ofNullable(teacherRepository.findByUsernameIs("sampleTeacher"));
        optionalTeacherEntity.orElseGet(() -> teacherRepository.save(
                TeacherEntity.builder()
                        .username("sampleTeacher")
                        .email("sampleTeacher@gmail.com")
                        .teacherName("김교사")
                        .password(passwordEncoder.encode("123"))
                        .approved(true)
                        .build()));
    }

    @Scheduled(fixedRate = 3600000)
    public void updatePassword(){
        Optional<AdminEntity> optionalAdminEntity = Optional
                .ofNullable(adminRepository
                        .findByUsernameIs("admin"));
        optionalAdminEntity.ifPresent(
                adminEntity -> {
                    adminEntity.updatePassword(passwordEncoder.encode("root123!@#"));
                    adminRepository.save(adminEntity);
        });
    }
}