package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Service.CertService;
import com.attendance.scheduler.Service.JoinService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class CertControllerTest {

    @Autowired
    private CertService certService;

    @Autowired
    private JoinService joinService;

    @Test
    void idEmailConfirm() {

        //Given
        //회원가입
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinService.joinTeacher(joinTeacherDTO);

        //비밀번호 찾기 정보
        FindPasswordDTO findPasswordDTO = new FindPasswordDTO();
        findPasswordDTO.setEmail("ghdtpgh8913@gmail.com");
        findPasswordDTO.setUsername("testTeacher");

        //When
        boolean id = certService.idConfirmation(findPasswordDTO);
        boolean email = certService.emailConfirmation(findPasswordDTO);

        //Then
        assertTrue(id);
        assertTrue(email);
    }

    @Test
    void authNumCheck(){

        //Given
        HttpSession session = new MockHttpSession();
        FindPasswordDTO findPasswordDTO = new FindPasswordDTO();
        findPasswordDTO.setUsername("testTeacher");
        findPasswordDTO.setEmail("ghdtpgh8913@gmail.com");

        certService.setupAuthNum(findPasswordDTO, session);

        //When
        Map<String, Object> sessionAuthNumMap = (Map<String, Object>) session.getAttribute(findPasswordDTO.getEmail());

        //Then
        assertFalse(sessionAuthNumMap.isEmpty());

    }
}