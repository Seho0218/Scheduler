package com.attendance.scheduler.Entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacherId")
    private TeacherEntity teacherEntity;

    public void setTeacherEntity(TeacherEntity teacherEntity) {
        if (this.teacherEntity != null) {
            this.teacherEntity.getStudentEntityList().remove(this);
        }
        this.teacherEntity = teacherEntity;
        if(teacherEntity != null) {
            teacherEntity.addForeignKey(this);
        }
    }

    @OneToOne(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClassEntity classEntity;

    public void addClassForeignKey(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    @Builder
    public StudentEntity(Long id, String studentName, String studentPhoneNumber, String studentAddress, String studentDetailedAddress, String studentParentPhoneNumber, TeacherEntity teacherEntity) {
        this.id = id;
        this.studentName = studentName;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentAddress = studentAddress;
        this.studentDetailedAddress = studentDetailedAddress;
        this.studentParentPhoneNumber = studentParentPhoneNumber;
        this.teacherEntity = teacherEntity;
    }
}
