package com.attendance.scheduler.Entity;

import com.attendance.scheduler.Entity.Account.StudentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Table(name = "class_table")
@NoArgsConstructor(access = PROTECTED)
public class ClassEntity {

    @Id
    private Long classId;

    private String studentName;

    private int counts;

    private int Monday;
    private int Tuesday;
    private int Wednesday;
    private int Thursday;
    private int Friday;

    @MapsId
    @OneToOne
    @JoinColumn(name = "STUDENT_ID")
    private StudentEntity studentEntity;


}
