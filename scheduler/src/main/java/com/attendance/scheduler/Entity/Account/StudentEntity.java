package com.attendance.scheduler.Entity.Account;

import com.attendance.scheduler.Entity.AttendanceEntity;
import com.attendance.scheduler.Entity.ClassEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)

@Table(name = "student")
@DiscriminatorValue("student")
public class StudentEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private Long studentId;

    private String studentName;

    private String studentTel;

    private boolean attendanceChecked;

    private LocalDateTime attendanceTime;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private TeacherEntity teacherEntity;

    @OneToMany(mappedBy = "studentEntity", fetch = FetchType.LAZY)
    private List<AttendanceEntity> studentSet = new ArrayList<>();

    @OneToOne(mappedBy = "studentEntity")
    private ClassEntity classEntity;


}
