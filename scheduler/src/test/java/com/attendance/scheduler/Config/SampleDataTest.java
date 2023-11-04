package com.attendance.scheduler.Config;

import com.attendance.scheduler.Course.Dto.ClassDTO;
import com.attendance.scheduler.Student.Dto.StudentInformationDTO;
import com.attendance.scheduler.Teacher.Dto.DeleteClassDTO;
import com.attendance.scheduler.Teacher.Dto.JoinTeacherDTO;
import com.attendance.scheduler.Teacher.Dto.TeacherDTO;
import com.attendance.scheduler.Student.StudentRepository;
import com.attendance.scheduler.Course.ClassService;
import com.attendance.scheduler.Teacher.TeacherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SampleDataTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JoinTeacherDTO sampleTeacherDataSet(){
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("sampleTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("sampleTeacherDataSet@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);
        return joinTeacherDTO;
    }

    public static StudentInformationDTO sampleStudentInformationDTO() {
        StudentInformationDTO studentInformationDTO = new StudentInformationDTO();
        studentInformationDTO.setStudentName("김샘플");
        studentInformationDTO.setStudentPhoneNumber("010-1234-1234");
        studentInformationDTO.setStudentParentPhoneNumber("010-1234-1233");
        studentInformationDTO.setStudentAddress("대한민국 저기 어디");
        studentInformationDTO.setStudentDetailedAddress("어디");
        return studentInformationDTO;
    }

    public ClassDTO sampleClass(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("sampleStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);
        return classDTO;
    }
        @Test
        @DisplayName("샘플 교사 정보")
        void saveSampleTeacherDataSet(){
            String encode = passwordEncoder.encode(sampleTeacherDataSet().getPassword());
            sampleTeacherDataSet().setPassword(encode);
            teacherService.joinTeacher(sampleTeacherDataSet());
        }


        @Test
        @DisplayName("샘플 학생 수강 정보")
        void saveSampleStudent(){
            classService.saveClassTable(sampleClass());
        }


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
