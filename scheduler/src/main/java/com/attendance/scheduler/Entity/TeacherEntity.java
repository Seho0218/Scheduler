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
    private Long id;

    private String teacherId;

    private String name;

    private String password;

    private String email;

    @Column(columnDefinition = "boolean default '0'")
    private boolean approved;

    @Builder
    public TeacherEntity(Long id, String teacherId, String name, String password, String email, boolean approved) {
        this.id = id;
        this.teacherId = teacherId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.approved = approved;
    }
}
