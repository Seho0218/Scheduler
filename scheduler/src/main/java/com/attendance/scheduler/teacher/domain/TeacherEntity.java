package com.attendance.scheduler.teacher.domain;

import com.attendance.scheduler.course.domain.ClassEntity;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "teacher")
@NoArgsConstructor(access = PROTECTED)
public class TeacherEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "teacherId")
    private Long id;

    private String username;

    private String teacherName;

    private String password;

    private String email;

    @Transient
    private String role;

    @Column(columnDefinition = "boolean default '0'")
    private boolean approved;

    @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.PERSIST)
    Set<StudentEntity> studentEntityList = new LinkedHashSet<>();

    @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ClassEntity> classEntity = new LinkedHashSet<>();

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity.add(classEntity);
    }

    public void setStudentEntity(StudentEntity studentEntity) {
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
    public TeacherEntity(Long id, String username, String teacherName, String password, String email, String role, boolean approved, Set<StudentEntity> studentEntityList, Set<ClassEntity> classEntity) {
        this.id = id;
        this.username = username;
        this.teacherName = teacherName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.approved = approved;
        this.studentEntityList = studentEntityList;
        this.classEntity = classEntity;
    }
}
