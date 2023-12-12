package com.attendance.scheduler.member.student.repository;

import com.attendance.scheduler.member.student.domain.StudentEntity;
import com.attendance.scheduler.member.student.dto.StudentInformationDTO;
import com.attendance.scheduler.member.teacher.dto.StudentSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.attendance.scheduler.member.student.domain.QStudentEntity.studentEntity;
import static org.springframework.util.StringUtils.hasText;


@Repository
@RequiredArgsConstructor
public class StudentRepository {

    public final JPAQueryFactory queryFactory;

    public Page<StudentInformationDTO> studentInformationDTOList(StudentSearchCondition studentSearchCondition, Pageable pageable){
        List<StudentInformationDTO> studentInformationList = queryFactory
                .select(Projections.fields(StudentInformationDTO.class,
                        studentEntity.id,
                        studentEntity.studentName,
                        studentEntity.studentAddress,
                        studentEntity.studentDetailedAddress,
                        studentEntity.studentPhoneNumber,
                        studentEntity.studentParentPhoneNumber,
                        studentEntity.teacherName,
                        studentEntity.creationTimestamp
                ))
                .from(studentEntity)
                .where(
                        studentNameEq(studentSearchCondition.getStudentName()),
                        teacherNameEq(studentSearchCondition.getTeacherName())
                )
                .fetch();

        JPAQuery<Long> counts = queryFactory
                .select(studentEntity.count())
                .from(studentEntity)
                .where(
                        studentNameEq(studentSearchCondition.getStudentName()),
                        teacherNameEq(studentSearchCondition.getTeacherName())
                );

        return PageableExecutionUtils.getPage(studentInformationList, pageable, counts::fetchOne);
    }

    private BooleanExpression studentNameEq(String studentName) {
        return hasText(studentName) ? studentEntity.studentName.eq(studentName) : null;
    }

    private BooleanExpression teacherNameEq(String teacherName) {
        return hasText(teacherName) ? studentEntity.teacherName.eq(teacherName) : null;
    }

    public Optional<StudentEntity> getStudentEntity(Long studentId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(studentEntity)
                .where(studentEntity.id.eq(studentId))
                .fetchOne());
    }
}
