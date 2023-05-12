package com.attendance.scheduler.Dto;

import com.attendance.scheduler.Entity.ClassEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Timestamp;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ClassDTO {

    //    학생 이름
    @NotEmpty
    private String studentName;
    //    수업 횟수
    private int counts;

    //    수업 시간
    @NotEmpty
    private Integer Monday;

    @NotEmpty
    private Integer Tuesday;

    @NotEmpty
    private Integer Wednesday;

    @NotEmpty
    private Integer Thursday;

    @NotEmpty
    private Integer Friday;

    private Timestamp updateTimeStamp;

    public ClassEntity toEntity() {
        return ClassEntity.builder()
                .studentName(studentName)
                .counts(counts)
                .monday(Monday)
                .tuesday(Tuesday)
                .wednesday(Wednesday)
                .thursday(Thursday)
                .friday(Friday)
                .updateTimeStamp(updateTimeStamp)
                .build();
    }

    public static ClassDTO classTableInstance(){
        return new ClassDTO();
    }
}
