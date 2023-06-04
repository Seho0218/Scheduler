package com.attendance.scheduler.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@Table(name = "class_table")
@NoArgsConstructor(access = PROTECTED)
public class ClassEntity {

    @Id
    private String studentName;

    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

//    @MapsId
//    @JoinColumn(name = "STUDENT_ID")
//    @OneToOne
//    private StudentEntity studentEntity;
//
    @Builder

    public ClassEntity(String studentName, Integer monday, Integer tuesday, Integer wednesday, Integer thursday, Integer friday, Timestamp updateTimeStamp) {
        this.studentName = studentName;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.updateTimeStamp = updateTimeStamp;
    }
}
