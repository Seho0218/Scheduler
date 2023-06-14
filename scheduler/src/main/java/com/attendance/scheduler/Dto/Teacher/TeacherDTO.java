package com.attendance.scheduler.Dto.Teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class TeacherDTO {

    private String teacherId;

    private String teacherPassword;

    private String teacherName;

    private String teacherEmail;

    private boolean approved;

}
