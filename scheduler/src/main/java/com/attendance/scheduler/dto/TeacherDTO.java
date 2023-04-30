package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherDTO {

    @NotNull
    private Long id;

    @NotNull
    private String teacherName;

    @NotNull
    private String teacherTel;
}
