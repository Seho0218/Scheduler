package com.attendance.scheduler.Config;

import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<AdminEntity> optionalAdminEntity = Optional
                .ofNullable(adminRepository
                        .findByUsernameIs("admin"));
        optionalAdminEntity.orElseGet(() -> adminRepository.save(
                AdminEntity.builder()
                        .username("admin")
                        .email("adminTest@gmail.com")
                        .password(passwordEncoder.encode("root123!@#"))
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