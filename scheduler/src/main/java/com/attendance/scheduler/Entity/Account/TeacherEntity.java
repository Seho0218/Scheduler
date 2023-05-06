package com.attendance.scheduler.Entity.Account;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "teacher")
@DiscriminatorValue("teacher")
public class TeacherEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEACHER_ID")
    private Long teacherId;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private String teacherTel;

    @OneToMany(mappedBy = "teacherEntity", fetch = FetchType.EAGER)
    private List<StudentEntity> studentEntitySet = new ArrayList<>();

}
