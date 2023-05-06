package com.attendance.scheduler.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttendanceDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private LocalDateTime attendanceDate;
}
