package com.attendance.scheduler.student.domain;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "student")
@NoArgsConstructor(access = PROTECTED)
public class StudentEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "studentId")
    private Long id;

    private String studentName;

    private String studentPhoneNumber;

    private String studentAddress;

    private String studentDetailedAddress;

    private String studentParentPhoneNumber;

    @NotNull
    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacherId")
    private TeacherEntity teacherEntity;

    public void setTeacherEntity(TeacherEntity teacherEntity) {
        if (this.teacherEntity != null) {
            this.teacherEntity.getStudentEntityList().remove(this);
        }
        this.teacherEntity = teacherEntity;
        if(teacherEntity != null) {
            teacherEntity.setStudentEntity(this);
        }
    }

    @OneToOne(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentStatusEntity studentStatusEntity;

    public void setStudentClassEntity(StudentStatusEntity studentStatusEntity) {
        this.studentStatusEntity = studentStatusEntity;
    }

    @Builder
    public StudentEntity(Long id, String studentName, String studentPhoneNumber, String studentAddress, String studentDetailedAddress, String studentParentPhoneNumber, TeacherEntity teacherEntity, StudentStatusEntity studentStatusEntity) {
        this.id = id;
        this.studentName = studentName;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentAddress = studentAddress;
        this.studentDetailedAddress = studentDetailedAddress;
        this.studentParentPhoneNumber = studentParentPhoneNumber;
        this.teacherEntity = teacherEntity;
        this.studentStatusEntity = studentStatusEntity;
    }
}
