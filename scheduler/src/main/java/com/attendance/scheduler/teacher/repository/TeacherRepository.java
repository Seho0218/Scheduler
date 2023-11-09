package com.attendance.scheduler.teacher.repository;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.attendance.scheduler.teacher.domain.QTeacherEntity.teacherEntity;

@Repository
@RequiredArgsConstructor
public class TeacherRepository {
    public final JPAQueryFactory queryFactory;

    public TeacherEntity getTeacherEntityById(Long teacherId){
        return queryFactory
                .select(Projections.fields(TeacherEntity.class,
                        teacherEntity.id,
                        teacherEntity.teacherName,
                        teacherEntity.username,
                        teacherEntity.password,
                        teacherEntity.email,
                        teacherEntity.approved))
                .from(teacherEntity)
                .where(teacherEntity.id.eq(teacherId))
                .fetchOne();
    }


}
