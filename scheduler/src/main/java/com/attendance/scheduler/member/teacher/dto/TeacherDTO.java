package com.attendance.scheduler.member.teacher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class TeacherDTO {

    private Long id;

    private String username;

    private String password;

    private String teacherName;

    private String email;

    private String role;

    private boolean approved;

}
