package com.attendance.scheduler.entity.Account;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Teacher")
public class Teacher {

    @Id @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "TEACHER_ID")
    private Long id;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private String teacherTel;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private List<Student> studentSet = new ArrayList<>();

}
