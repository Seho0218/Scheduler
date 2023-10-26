package com.attendance.scheduler.Dto;

import com.attendance.scheduler.Entity.StudentEntity;
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

    public StudentEntity toEntity(){
        return StudentEntity.builder()
                .studentName(studentName)
                .studentPhoneNumber(studentParentPhoneNumber)
                .studentParentPhoneNumber(studentPhoneNumber)
                .studentAddress(studentAddress)
                .studentDetailedAddress(studentDetailedAddress)
                .build();
    }
}
