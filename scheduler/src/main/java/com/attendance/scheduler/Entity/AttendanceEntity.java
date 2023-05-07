package com.attendance.scheduler.Entity;

import com.attendance.scheduler.Entity.Account.StudentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "attendance")
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("attendance")
public class AttendanceEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ATTENDANCE_ID")
    private Long attendanceId;

    @Column(nullable = false)
    private LocalDateTime attendanceDate;

    @Column(nullable = false)
    private boolean attendanceChecked;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private StudentEntity studentEntity;
}
