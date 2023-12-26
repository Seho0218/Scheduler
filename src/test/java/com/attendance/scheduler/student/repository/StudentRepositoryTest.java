package com.attendance.scheduler.student.repository;

import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.attendance.scheduler.student.domain.QStudentEntity.studentEntity;
import static com.attendance.scheduler.teacher.domain.QTeacherEntity.teacherEntity;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    void studentInformationDTOList() {

        List<StudentInformationDTO> studentInformationList = queryFactory
                .select(Projections.fields(StudentInformationDTO.class,
                        studentEntity.id,
                        studentEntity.studentName,
                        studentEntity.studentAddress,
                        studentEntity.studentDetailedAddress,
                        studentEntity.studentPhoneNumber,
                        studentEntity.studentParentPhoneNumber,
                        teacherEntity.teacherName,
                        studentEntity.creationTimestamp))
                .from(studentEntity)
                .join(teacherEntity)
                .on(studentEntity.teacherEntity.eq(teacherEntity))
                .fetch();

        System.out.println("studentInformationList = " + studentInformationList);
    }
}