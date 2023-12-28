package com.attendance.scheduler.student.domain;

import com.attendance.scheduler.comment.domain.entity.CommentEntity;
import com.attendance.scheduler.course.domain.ClassEntity;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "student")
@NoArgsConstructor(access = PROTECTED)
public class StudentEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "student_id")
    private Long id;

    private String studentName;
    private String studentPhoneNumber;
    private String studentAddress;
    private String studentDetailedAddress;
    private String studentParentPhoneNumber;

    @CreationTimestamp
    private Timestamp creationTimestamp;

    @NotNull
    @ManyToOne(fetch = LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacherEntity;

    public void setTeacherEntity(TeacherEntity teacherEntity) {
        if (this.teacherEntity != null) {
            this.teacherEntity.getStudentEntityList().remove(this);
        }
        this.teacherEntity = teacherEntity;
        if(teacherEntity != null) {
            teacherEntity.setStudentEntity(this);
        }
    }

    @OneToMany(mappedBy = "studentEntity")
    List<CommentEntity> commentEntityList = new ArrayList<>();

    public void addCommentEntity(CommentEntity commentEntity) {
        this.commentEntityList.add(commentEntity);
    }

    @OneToOne(mappedBy = "studentEntity")
    @PrimaryKeyJoinColumn
    private ClassEntity classEntity;

    public void addClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    @Builder
    public StudentEntity(Long id, String studentName, String studentPhoneNumber, String studentAddress, String studentDetailedAddress, String studentParentPhoneNumber, Timestamp creationTimestamp, TeacherEntity teacherEntity, List<CommentEntity> commentEntityList, ClassEntity classEntity) {
        this.id = id;
        this.studentName = studentName;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentAddress = studentAddress;
        this.studentDetailedAddress = studentDetailedAddress;
        this.studentParentPhoneNumber = studentParentPhoneNumber;
        this.creationTimestamp = creationTimestamp;
        this.teacherEntity = teacherEntity;
        this.commentEntityList = commentEntityList;
        this.classEntity = classEntity;
    }
}
