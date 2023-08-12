package com.attendance.scheduler.Dto.Teacher;

import com.attendance.scheduler.Entity.TeacherEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class JoinTeacherDTO {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;

    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;

    private boolean approved;

    public static TeacherDTO getInstance(){
        return new TeacherDTO();
    }

    public TeacherEntity toEntity(){
        return TeacherEntity.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .approved(approved)
                .build();
    }
}
