package com.attendance.scheduler.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class StudentEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String studentName;

    private String studentPhoneNumber;

    private String studentAddress;

    private String studentParentPhoneNumber;

}
