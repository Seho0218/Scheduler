package com.attendance.scheduler.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class TeacherEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long Id;

    private String teacherId;

    private String teacherName;

    private String teacherPassword;

    private String teacherEmail;

    @Column(columnDefinition = "boolean default '0'")
    private boolean approved;

    @Builder

    public TeacherEntity(Long id, String teacherId, String teacherName, String teacherPassword, String teacherEmail, boolean approved) {
        Id = id;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPassword = teacherPassword;
        this.teacherEmail = teacherEmail;
        this.approved = approved;
    }
}
