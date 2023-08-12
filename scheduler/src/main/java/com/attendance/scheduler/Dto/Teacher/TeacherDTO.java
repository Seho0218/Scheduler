package com.attendance.scheduler.Dto.Teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class TeacherDTO {

    private String username;

    private String password;

    private String name;

    private String email;

    private String role;

    private boolean approved;

}
