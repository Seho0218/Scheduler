package com.attendance.scheduler.Entity.Account;

import com.attendance.scheduler.Entity.AttendanceEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor
@Table(name = "student")
@DiscriminatorValue("student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private Long studentId;

    private String studentName;

    private String studentTel;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private TeacherEntity teacherEntity;

    @OneToMany(mappedBy = "studentEntity", fetch = FetchType.LAZY)
    private List<AttendanceEntity> studentSet = new ArrayList<>();

//    @OneToOne(mappedBy = "studentEntity")
//    private ClassEntity classEntity;
//
//    @Builder
//    public StudentEntity(Long studentId, String studentName, String studentTel, ClassEntity classEntity) {
//        this.studentId = studentId;
//        this.studentName = studentName;
//        this.studentTel = studentTel;
//        this.classEntity = classEntity;
//        this.classEntity.setStudentEntity(this);
//    }
}
