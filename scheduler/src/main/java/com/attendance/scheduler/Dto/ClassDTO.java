package com.attendance.scheduler.Dto;

import com.attendance.scheduler.Entity.ClassEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ClassDTO {

    //    학생 이름
    @NotEmpty(message="학생 이름을 정확히 입력해 주세요")
    private String studentName;

    //    수업 시간
    @NotNull(message="요일을 정확히 입력해 주세요")
    private Integer Monday;

    @NotNull(message="요일을 정확히 입력해 주세요")
    private Integer Tuesday;

    @NotNull(message="요일을 정확히 입력해 주세요")
    private Integer Wednesday;

    @NotNull(message="요일을 정확히 입력해 주세요")
    private Integer Thursday;

    @NotNull(message="요일을 정확히 입력해 주세요")
    private Integer Friday;

    private Timestamp updateTimeStamp;

    public ClassEntity toEntity() {
        return ClassEntity.builder()
                .studentName(studentName)
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
