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
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@Table(name = "class_table")
@NoArgsConstructor(access = PROTECTED)
public class ClassEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "class_id")
    private Long id;

    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "teacher_id")
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

    @OneToOne
    @JoinColumn(name = "student_id")
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
    public ClassEntity(Long id, Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Timestamp updateTimeStamp, TeacherEntity teacherEntity, StudentEntity studentEntity) {
        this.id = id;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.updateTimeStamp = updateTimeStamp;
        this.teacherEntity = teacherEntity;
        this.studentEntity = studentEntity;
    }
}