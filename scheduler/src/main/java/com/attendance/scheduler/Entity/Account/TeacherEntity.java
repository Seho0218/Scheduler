package com.attendance.scheduler.Entity.Account;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "teacher")
@DiscriminatorValue("teacher")
public class TeacherEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TEACHER_ID")
    private Long teacherId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String teacherName;

    @OneToMany(mappedBy = "teacherEntity", fetch = EAGER)
    private List<StudentEntity> studentEntitySet = new ArrayList<>();

}
