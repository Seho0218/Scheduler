package com.attendance.scheduler.entity.Account;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Student")
public class Student {

    @Id @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "STUDENT_ID")
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String studentTel;

    @Column(nullable = false)
    private boolean attendanceChecked;

    @Column(nullable = false)
    private LocalDateTime attendanceTime;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private List<Attendance> studentSet = new ArrayList<>();
}
