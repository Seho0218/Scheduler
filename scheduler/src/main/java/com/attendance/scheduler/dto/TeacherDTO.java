package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String teacherName;

    @NotEmpty
    private String teacherTel;
}
