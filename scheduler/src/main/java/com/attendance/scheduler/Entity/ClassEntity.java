package com.attendance.scheduler.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@DynamicUpdate
@Table(name = "class_table")
@NoArgsConstructor(access = PROTECTED)
public class ClassEntity {

    @Id
    private String studentName;

    private int counts;

    private int Monday;
    private int Tuesday;
    private int Wednesday;
    private int Thursday;
    private int Friday;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

//    @MapsId
//    @JoinColumn(name = "STUDENT_ID")
//    @OneToOne
//    private StudentEntity studentEntity;
//
    @Builder
    public ClassEntity(String studentName, int counts, int monday, int tuesday, int wednesday, int thursday, int friday, Timestamp updateTimeStamp) {
        this.studentName = studentName;
        this.counts = counts;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        Thursday = thursday;
        Friday = friday;
        this.updateTimeStamp = updateTimeStamp;
    }
}
