package com.attendance.scheduler.teacher.domain;

import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class TeacherEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "teacherId")
    private Long id;

    private String username;

    private String name;

    private String password;

    private String email;

    @Transient
    private String role;

    @Column(columnDefinition = "boolean default '0'")
    private boolean approved;

    @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.PERSIST)
    List<StudentEntity> studentEntityList = new ArrayList<>();

    public void addForeignKey(StudentEntity studentEntity) {
        this.studentEntityList.add(studentEntity);
    }

    public void updatePassword(PwdEditDTO pwdEditDTO) {
        this.password = pwdEditDTO.getPassword();
    }

    public void updateApprove(boolean approved) {
        this.approved = approved;
    }

    public void updateEmail(EditEmailDTO editEmailDTO) {
        this.email = editEmailDTO.getEmail();
    }

    @Builder
    public TeacherEntity(Long id, String username, String name, String password, String email, boolean approved) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.approved = approved;
    }
}
