package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.teacher.dto.FindIdDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.teacher.repository.TeacherJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                = Optional.ofNullable(teacherJpaRepository.findByEmailIs(findIdDTO.getEmail()));

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
        final String encodePassword = passwordEncoder.encode(pwdEditDTO.getPassword());
        pwdEditDTO.setPassword(encodePassword);

        TeacherEntity teacherEntity = teacherJpaRepository.findByUsernameIs(pwdEditDTO.getUsername());
        teacherEntity.updatePassword(pwdEditDTO);
        teacherJpaRepository.save(teacherEntity);
    }

    @Override
    @Transactional
    public void updateEmail(EditEmailDTO editEmailDTO) {
        TeacherEntity teacherEntity = teacherJpaRepository.findByUsernameIs(editEmailDTO.getUsername());
        teacherEntity.updateEmail(editEmailDTO);
        teacherJpaRepository.save(teacherEntity);
    }
}
