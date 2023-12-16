package com.attendance.scheduler.course.repository;

import com.attendance.scheduler.course.dto.ClassDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.attendance.scheduler.course.domain.QClassEntity.classEntity;
import static com.attendance.scheduler.teacher.domain.QTeacherEntity.teacherEntity;

@SpringBootTest
class ClassJpaRepositoryTest {

    @Autowired(required=true)
    private JPAQueryFactory queryFactory;


    @Test
    public void getStudentClassList(){
        List<ClassDTO> fetch = queryFactory
                .select(Projections.fields(ClassDTO.class,
                        classEntity.studentName,
                        classEntity.monday,
                        classEntity.tuesday,
                        classEntity.wednesday,
                        classEntity.thursday,
                        classEntity.friday,
                        classEntity.teacherName,
                        classEntity.updateTimeStamp
                ))
                .from(classEntity)
                .join(classEntity.teacherEntity, teacherEntity)
                .on(classEntity.studentName.eq(teacherEntity.teacherName))
                .fetch();
        System.out.println("fetch = " + fetch);
    }

}