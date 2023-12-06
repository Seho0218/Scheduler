package com.attendance.scheduler.course.repository;

import com.attendance.scheduler.course.domain.ClassEntity;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.course.dto.StudentClassDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attendance.scheduler.course.domain.QClassEntity.classEntity;

@Repository
@RequiredArgsConstructor
public class ClassRepository {

    public final JPAQueryFactory queryFactory;


    public List<ClassDTO> getStudentClassList(){
        return queryFactory
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
                .fetch();
    }

    public StudentClassDTO getStudentClassByStudentName(String studentName){
        return queryFactory
                .select(Projections.fields(StudentClassDTO.class,
                        classEntity.studentName,
                        classEntity.monday,
                        classEntity.tuesday,
                        classEntity.wednesday,
                        classEntity.thursday,
                        classEntity.friday,
                        classEntity.teacherName))
                .from(classEntity)
                .where(classEntity.studentName.eq(studentName))
                .fetchOne();
    }

    public List<StudentClassDTO> getStudentClassByTeacherName(String teacherName){
        return queryFactory
                .select(Projections.fields(StudentClassDTO.class,
                        classEntity.studentName,
                        classEntity.monday,
                        classEntity.tuesday,
                        classEntity.wednesday,
                        classEntity.thursday,
                        classEntity.friday,
                        classEntity.teacherName))
                .from(classEntity)
                .where(classEntity.teacherName.eq(teacherName))
                .fetch();
    }

    public List<ClassEntity> getStudentClassEntityByStudentName(String studentName) {
        return queryFactory
                .selectFrom(classEntity)
                .where(classEntity.studentName.eq(studentName))
                .fetch();
    }
}
