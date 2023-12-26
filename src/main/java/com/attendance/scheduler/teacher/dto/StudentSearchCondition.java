package com.attendance.scheduler.teacher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentSearchCondition {

    private String studentName;
    private String teacherName;
}
