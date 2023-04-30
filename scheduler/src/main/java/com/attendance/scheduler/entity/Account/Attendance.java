package com.attendance.scheduler.entity.Account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Attendance")
public class Attendance {

    @Id @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "ATTENDENCE_ID")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime attendanceDate;

    @Column(nullable = false)
    private boolean attendanceChecked;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
}