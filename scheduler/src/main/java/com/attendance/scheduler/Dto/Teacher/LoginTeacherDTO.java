package com.attendance.scheduler.Dto.Teacher;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class LoginTeacherDTO {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String teacherId;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String teacherPassword;
}
