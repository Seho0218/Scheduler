package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Mapper.JoinTeacherMapper;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

    private final TeacherRepository teacherRepository;
    private final JoinTeacherMapper joinTeacherMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void joinTeacher(JoinTeacherDTO joinTeacherDTO) {
        String encode = passwordEncoder
                .encode(joinTeacherDTO.getPassword());
        joinTeacherDTO.setPassword(encode);
        teacherRepository.save(joinTeacherDTO.toEntity());
    }

    @Override
    public JoinTeacherDTO findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO) {
        //final 붙인 이유. 변수값을 더이상 변경하지 않기 위해서
        final TeacherEntity byTeacherIdIs = teacherRepository
                .findByUsernameIs(joinTeacherDTO.getUsername());
        return joinTeacherMapper.toJoinTeacherDTO(byTeacherIdIs);
    }
}
