package com.attendance.scheduler.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AttendanceDTO {

    private Long id;

    private LocalDateTime attendanceDate;
}
