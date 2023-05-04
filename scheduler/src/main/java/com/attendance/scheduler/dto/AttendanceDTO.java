package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private LocalDateTime attendanceDate;
}
