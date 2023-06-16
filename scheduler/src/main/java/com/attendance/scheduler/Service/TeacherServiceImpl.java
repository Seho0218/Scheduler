package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Mapper.JoinTeacherMapper;
import com.attendance.scheduler.Mapper.LoginTeacherMapper;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final JoinTeacherMapper joinTeacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoginTeacherMapper loginTeacherMapper;

    @Override
    public void joinTeacher(JoinTeacherDTO joinTeacherDTO) {
        String encode = passwordEncoder.encode(joinTeacherDTO.getTeacherPassword());
        joinTeacherDTO.setTeacherPassword(encode);
        teacherRepository.save(joinTeacherDTO.toEntity());
    }

    @Override
    public JoinTeacherDTO findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO) {
        final TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs(joinTeacherDTO.getTeacherId());
        return joinTeacherMapper.toJoinTeacherDTO(byTeacherIdIs);
    }

    @Override
    public TeacherDTO loginTeacher(LoginTeacherDTO loginTeacherDTO) {
        //final 붙인 이유. 변수값을 더이상 변경하지 않기 위해서
        final TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs(loginTeacherDTO.getTeacherId());
        boolean matches = passwordEncoder.matches(loginTeacherDTO.getTeacherPassword(), byTeacherIdIs.getTeacherPassword());
        if (matches) {
            return loginTeacherMapper.toTeacherDTO(byTeacherIdIs);
        }
        return null;
    }
}
