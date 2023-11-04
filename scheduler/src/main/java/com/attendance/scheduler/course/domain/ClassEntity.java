package com.attendance.scheduler.course.domain;

import com.attendance.scheduler.student.domain.StudentEntity;
import jakarta.persistence.*;
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
@Table(name = "class_table")
@NoArgsConstructor(access = PROTECTED)
public class ClassEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "classId")
    private Long id;

    private String studentName;

    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "studentId")
    private StudentEntity studentEntity;

    public void setStudentEntity(StudentEntity studentEntity) {
        if (this.studentEntity != null) {
            this.studentEntity.addClassForeignKey(null);
        }
        this.studentEntity = studentEntity;
        if (studentEntity != null) {
            studentEntity.addClassForeignKey(this);
        }
    }

    @Builder
    public ClassEntity(Long id, String studentName, Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Timestamp updateTimeStamp, StudentEntity studentEntity) {
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