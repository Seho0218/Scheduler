package com.attendance.scheduler.course.repository;

import com.attendance.scheduler.course.dto.ClassDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.attendance.scheduler.course.domain.QClassEntity.classEntity;
import static com.attendance.scheduler.student.domain.QStudentEntity.studentEntity;
import static com.attendance.scheduler.teacher.domain.QTeacherEntity.teacherEntity;

@SpringBootTest
class ClassRepositoryTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void getStudentClassList(){
        List<ClassDTO> fetch = queryFactory
                .select(Projections.fields(ClassDTO.class,
                        studentEntity.studentName,
                        classEntity.monday,
                        classEntity.tuesday,
                        classEntity.wednesday,
                        classEntity.thursday,
                        classEntity.friday,
                        teacherEntity.teacherName,
                        classEntity.updateTimeStamp))
                .from(classEntity)
                .join(teacherEntity)
                .on(classEntity.teacherEntity.eq(teacherEntity))
                .join(classEntity)
                .on(classEntity.studentEntity.eq(studentEntity))
                .fetch();
        System.out.println("fetch = " + fetch);
    }

    @Test
    public void getStudentClassByTeacherEntity(){

    }
}