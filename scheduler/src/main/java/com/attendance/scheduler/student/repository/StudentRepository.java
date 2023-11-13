package com.attendance.scheduler.student.repository;

import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.attendance.scheduler.student.domain.QStudentEntity.studentEntity;

@Repository
@RequiredArgsConstructor
public class StudentRepository {

    public final JPAQueryFactory queryFactory;

    public List<StudentInformationDTO> studentInformationDTOList(){
        return queryFactory
                .select(Projections.fields(StudentInformationDTO.class,
                        studentEntity.id,
                        studentEntity.studentName,
                        studentEntity.studentAddress,
                        studentEntity.studentDetailedAddress,
                        studentEntity.studentPhoneNumber,
                        studentEntity.studentParentPhoneNumber,
                        studentEntity.teacherName
                ))
                .from(studentEntity)
                .fetch();
    }

    public Optional<StudentEntity> getStudentEntity(Long studentId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(studentEntity)
                .where(studentEntity.id.eq(studentId))
                .fetchOne());
    }
}
