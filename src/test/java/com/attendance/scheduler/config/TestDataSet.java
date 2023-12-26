package com.attendance.scheduler.config;

import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.member.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.member.teacher.dto.RegisterStudentDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestDataSet {

    public static ClassDTO testStudentClassDataSet(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("김학생");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);
        return classDTO;
    }

    public static ClassDTO test2StudentClassDataSet(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("이학생");
        classDTO.setMonday(2);
        classDTO.setTuesday(3);
        classDTO.setWednesday(4);
        classDTO.setThursday(5);
        classDTO.setFriday(6);
        return classDTO;
    }

    public static ClassDTO testStudent_duplicated(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("박학생");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);
        return classDTO;
    }

    public static JoinTeacherDTO testTeacherDataSet(){
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("testTeacherDataSet@gmail.com");
        joinTeacherDTO.setTeacherName("김교사");
        joinTeacherDTO.setApproved(true);
        return joinTeacherDTO;
    }

    public static RegisterStudentDTO testStudentInformationDTO(){
        RegisterStudentDTO studentInformationDTO = new RegisterStudentDTO();
        studentInformationDTO.setStudentName("김학생");
        studentInformationDTO.setStudentPhoneNumber("010-1234-1234");
        studentInformationDTO.setStudentParentPhoneNumber("010-1234-1233");
        studentInformationDTO.setStudentAddress("대한민국 저기 어디");
        studentInformationDTO.setStudentDetailedAddress("어디");

        studentInformationDTO.setTeacherUsername("testTeacher");
        return studentInformationDTO;

    }

    public static RegisterStudentDTO test2StudentInformationDTO(){
        RegisterStudentDTO studentInformationDTO = new RegisterStudentDTO();
        studentInformationDTO.setStudentName("이학생");
        studentInformationDTO.setStudentPhoneNumber("010-4321-4321");
        studentInformationDTO.setStudentParentPhoneNumber("010-4321-4322");
        studentInformationDTO.setStudentAddress("대한민국 저기 먼데");
        studentInformationDTO.setStudentDetailedAddress("먼데");
        studentInformationDTO.setTeacherUsername("testTeacher");

        return studentInformationDTO;

    }

}
