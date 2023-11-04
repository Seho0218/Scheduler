package com.attendance.scheduler.Student.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentInformationDTO {

    private Long id;

    private String studentName;

    private String studentPhoneNumber;

    private String studentAddress;

    private String studentDetailedAddress;

    private String studentParentPhoneNumber;

    private String teacherName;
}
