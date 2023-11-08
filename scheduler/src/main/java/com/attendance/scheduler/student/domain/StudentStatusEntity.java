package com.attendance.scheduler.student.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@Table(name = "student_status")
@NoArgsConstructor(access = PROTECTED)
public class StudentStatusEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "student_class")
    private Long id;

    private String studentName;

    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "studentId")
    private StudentEntity studentEntity;

    public void setStudentEntity(StudentEntity studentEntity) {
        if (this.studentEntity != null) {
            this.studentEntity.setStudentClassEntity(null);
        }
        this.studentEntity = studentEntity;
        if (studentEntity != null) {
            studentEntity.setStudentClassEntity(this);
        }
    }

    @Builder
    public StudentStatusEntity(Long id, String studentName, Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Timestamp updateTimeStamp, StudentEntity studentEntity) {
        this.id = id;
        this.studentName = studentName;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.updateTimeStamp = updateTimeStamp;
        this.studentEntity = studentEntity;
    }
}