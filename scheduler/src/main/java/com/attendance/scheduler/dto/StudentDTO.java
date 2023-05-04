package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String studentName;

    @NotEmpty
    private String studentTel;

    @NotEmpty
    private boolean attendanceChecked;
}
