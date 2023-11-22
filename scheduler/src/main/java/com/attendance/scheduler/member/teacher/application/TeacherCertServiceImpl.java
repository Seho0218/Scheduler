package com.attendance.scheduler.member.teacher.application;

import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.member.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.member.teacher.dto.FindIdDTO;
import com.attendance.scheduler.member.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.member.teacher.repository.TeacherJpaRepository;
import com.attendance.scheduler.member.teacher.domain.TeacherEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherCertServiceImpl implements TeacherCertService {

    private final TeacherJpaRepository teacherJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean idConfirmation(FindPasswordDTO findPasswordDTO) {
        return teacherJpaRepository
                .existsByUsername(findPasswordDTO.getUsername());
    }

    @Override
    public boolean emailConfirmation(FindPasswordDTO findPasswordDTO) {
        return teacherJpaRepository
                .existsByEmail(findPasswordDTO.getEmail());
    }

    @Override
    public Optional<FindIdDTO> findIdByEmail(FindIdDTO findIdDTO) {
        Optional<TeacherEntity> optionalTeacherEntity
                = Optional.ofNullable(teacherJpaRepository.findByUsernameIs(findIdDTO.getUsername()));

        return optionalTeacherEntity.map(teacherEntity -> {
            FindIdDTO resultDTO = new FindIdDTO();
            resultDTO.setUsername(teacherEntity.getUsername());
            resultDTO.setEmail(teacherEntity.getEmail());
            return resultDTO;
        });
    }

    @Override
    @Transactional
    public void initializePassword(PwdEditDTO pwdEditDTO) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String encodePassword = passwordEncoder.encode(pwdEditDTO.getPassword());

        pwdEditDTO.setPassword(encodePassword);

        final TeacherEntity byTeacherIdIs = teacherJpaRepository
                .findByUsernameIs(auth.getName());
        byTeacherIdIs.updatePassword(pwdEditDTO);
        teacherJpaRepository.save(byTeacherIdIs);
    }

    @Override
    public void updateEmail(EditEmailDTO editEmailDTO) {
        TeacherEntity teacherEntity = teacherJpaRepository.findByUsernameIs(editEmailDTO.getUsername());
        teacherEntity.updateEmail(editEmailDTO);
        teacherJpaRepository.save(teacherEntity);
    }
}
