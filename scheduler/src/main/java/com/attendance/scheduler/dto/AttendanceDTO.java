package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceDTO {

    @NotNull
    private Long id;

    @NotNull
    private LocalDateTime attendanceDate;
}
