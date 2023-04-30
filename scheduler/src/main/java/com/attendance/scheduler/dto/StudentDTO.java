package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {

    @NotNull
    private Long id;

    @NotNull
    private String studentName;

    @NotNull
    private String studentTel;

    @NotNull
    private boolean attendanceChecked;
}
