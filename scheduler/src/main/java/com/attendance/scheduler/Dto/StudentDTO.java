package com.attendance.scheduler.Dto;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class StudentDTO {

    private Long id;

    private String studentName;

    private String studentTel;

//    public StudentEntity toEntity(){
//        return StudentEntity.builder()
//                .studentName(studentName)
//                .studentTel(studentTel)
//                .build();
//    }
}
