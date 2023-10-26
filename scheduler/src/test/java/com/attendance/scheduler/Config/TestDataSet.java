package com.attendance.scheduler.Config;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;

public class TestDataSet {

    public static ClassDTO testStudent(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("testStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);
        return classDTO;
    }

    public static ClassDTO test2Student(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("test2Student");
        classDTO.setMonday(2);
        classDTO.setTuesday(3);
        classDTO.setWednesday(4);
        classDTO.setThursday(5);
        classDTO.setFriday(6);
        return classDTO;
    }

    public static ClassDTO testStudent_duplicated(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("test3Student");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);
        return classDTO;
    }

    public static JoinTeacherDTO sampleTeacherDataSet(){
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("sampleTeacherDataSet");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("sampleTeacherDataSet@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);
        return joinTeacherDTO;
    }

}
