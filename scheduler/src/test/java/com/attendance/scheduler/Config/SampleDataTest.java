package com.attendance.scheduler.Config;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.Teacher.DeleteClassDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.ClassService;
import com.attendance.scheduler.Service.TeacherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SampleDataTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClassService classService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClassDTO sampleStudent(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("sampleStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);
        return classDTO;
    }

    public JoinTeacherDTO sampleTeacherDataSet(){
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("sampleTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("sampleTeacherDataSet@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);
        return joinTeacherDTO;
    }
    @Test
    @DisplayName("샘플 학생 강의 정보")
    void saveSampleStudent(){
        classService.saveClassTable(sampleStudent());
    }

    @Test
    @DisplayName("샘플 교사 정보")
    void saveSampleTeacherDataSet(){
        String encode = passwordEncoder.encode(sampleTeacherDataSet().getPassword());
        sampleTeacherDataSet().setPassword(encode);
        teacherService.joinTeacher(sampleTeacherDataSet());
    }

    @Test
    @DisplayName("샘플 학생 정보")
    void studentEntity(){
    }

    @Test
    @DisplayName("샘플 데이터 삭제")
    void deleteSampleData(){
        DeleteClassDTO deleteClassDTO = new DeleteClassDTO();
        List<String> classList = new ArrayList<>();

        classList.add("sampleStudent");
        deleteClassDTO.setDeleteClassList(classList);
        classService.deleteClass(deleteClassDTO);


        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setUsername("김교사");
        teacherService.deleteTeacher(teacherDTO);
    }
}
