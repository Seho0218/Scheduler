package com.attendance.scheduler.course.domain;

import com.attendance.scheduler.student.domain.StudentEntity;
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
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class ClassEntity {

    @Id
    private Long id;

    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

    @Version
    private Long version;

    @NotNull
    @ManyToOne(fetch = LAZY)
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

    @MapsId
    @OneToOne(fetch = LAZY)
    private StudentEntity studentEntity;

    public void setStudentEntity(StudentEntity studentEntity) {
        if(this.studentEntity != null){
            this.studentEntity.addClassEntity(null);
        }
        this.studentEntity = studentEntity;
        if (studentEntity != null) {
            studentEntity.addClassEntity(this);
        }
    }

    @Builder
    public ClassEntity(Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Timestamp updateTimeStamp) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.updateTimeStamp = updateTimeStamp;
    }
}