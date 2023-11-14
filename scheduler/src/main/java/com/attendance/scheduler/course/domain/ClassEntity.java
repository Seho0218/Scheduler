package com.attendance.scheduler.course.domain;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
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

    private String teacherName;


    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacherId")
    private TeacherEntity teacherEntity;

    public void setTeacherEntity(TeacherEntity teacherEntity) {
        if (this.teacherEntity != null) {
            this.teacherEntity.setClassEntity(null);
        }
        this.teacherEntity = teacherEntity;
        if (teacherEntity != null) {
            teacherEntity.setClassEntity(this);
        }
    }

    public void updateTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Builder
    public ClassEntity(Long id, String studentName, Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Timestamp updateTimeStamp, String teacherName, TeacherEntity teacherEntity) {
        this.id = id;
        this.studentName = studentName;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.updateTimeStamp = updateTimeStamp;
        this.teacherName = teacherName;
        this.teacherEntity = teacherEntity;
    }
}