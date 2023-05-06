package com.attendance.scheduler.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeacherDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String teacherName;

    @NotEmpty
    private String teacherTel;
}
