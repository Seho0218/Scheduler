package com.attendance.scheduler.entity;

import com.attendance.scheduler.entity.Account.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ClassTable {

    @Id
    private Long id;

    private int Monday;
    private int Tuesday;
    private int Wednesday;
    private int Thursday;
    private int Friday;

    @MapsId
    @OneToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
}
