package com.attendance.scheduler.teacher.repository;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.TeacherDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.attendance.scheduler.teacher.domain.QTeacherEntity.teacherEntity;

@Repository
@RequiredArgsConstructor
public class TeacherRepository {
    public final JPAQueryFactory queryFactory;

    public List<TeacherDTO> getTeacherList(){
        return queryFactory
                .select(Projections.fields(TeacherDTO.class,
                        teacherEntity.id,
                        teacherEntity.username,
                        teacherEntity.teacherName,
                        teacherEntity.approved
                        ))
                .from(teacherEntity)
                .fetch();
    }

    public Optional<TeacherEntity> getTeacherEntity(Long teacherId){
        return Optional.ofNullable(queryFactory
                .selectFrom(teacherEntity)
                .where(teacherEntity.id.eq(teacherId))
                .fetchOne());
    }
}
