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
    private String teacherId;

    private String duplicateTeacherId;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String teacherPassword;

    @NotEmpty(message = "이름을 입력해 주세요")
    private String teacherName;

    @NotEmpty(message = "이메일을 입력해 주세요")
    private String teacherEmail;

    private boolean approved;

    public static TeacherDTO getInstance(){
        return new TeacherDTO();
    }

    public TeacherEntity toEntity(){
        return TeacherEntity.builder()
                .teacherId(teacherId)
                .teacherName(teacherName)
                .teacherPassword(teacherPassword)
                .teacherEmail(teacherEmail)
                .approved(approved)
                .build();
    }

}
