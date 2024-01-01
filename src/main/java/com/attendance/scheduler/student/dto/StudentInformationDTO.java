package com.attendance.scheduler.student.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

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

    private Timestamp creationTimestamp;

    public String getStudentPhoneNumber(){
        return studentPhoneNumber.substring(0,3) + "-" + studentPhoneNumber.substring(3,7) + "-" + studentPhoneNumber.substring(7,11);
    }

    public void setStudentPhoneNumber(String studentPhoneNumber){
        this.studentPhoneNumber = studentPhoneNumber.replace("-", "");
    }

    public String getStudentParentPhoneNumber(){
        return studentParentPhoneNumber.substring(0,3) + "-" + studentParentPhoneNumber.substring(3,7) + "-" + studentParentPhoneNumber.substring(7,11);
    }

    public void setStudentParentPhoneNumber(String studentParentPhoneNumber){
        this.studentParentPhoneNumber = studentParentPhoneNumber.replace("-", "");
    }

}
