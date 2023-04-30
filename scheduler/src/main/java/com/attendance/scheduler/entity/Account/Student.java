package com.attendance.scheduler.entity.Account;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Student")
public class Student {

    @Id @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "STUDENT_ID")
    private Long id;

    private String studentName;

    private String studentTel;

    private LocalDateTime attendanceTime;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;
}
