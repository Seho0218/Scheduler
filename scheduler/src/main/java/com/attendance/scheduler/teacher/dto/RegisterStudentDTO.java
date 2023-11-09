package com.attendance.scheduler.teacher.dto;

import com.attendance.scheduler.student.domain.StudentEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegisterStudentDTO {

    @NotEmpty(message = "학생 이름을 정확히 입력해 주세요")
    private String studentName;

    @NotEmpty(message = "학생 전화번호를 입력해 주세요")
    private String studentPhoneNumber;

    @NotEmpty(message = "주소를 입력해 주세요")
    private String studentAddress;

    @NotEmpty(message = "상세주소를 입력해 주세요")
    private String studentDetailedAddress;

    @NotEmpty(message = "학부모 전화번호를 입력해 주세요")
    private String studentParentPhoneNumber;

    private String teacherName;

    private String teacherEntity;

    public StudentEntity toEntity(){
        return StudentEntity.builder()
                .studentName(studentName)
                .studentPhoneNumber(studentParentPhoneNumber)
                .studentParentPhoneNumber(studentPhoneNumber)
                .studentAddress(studentAddress)
                .studentDetailedAddress(studentDetailedAddress)
                .teacherName(teacherName)
                .build();
    }
}
