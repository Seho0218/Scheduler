package com.attendance.scheduler.notification.dto;

import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import com.attendance.scheduler.notification.domain.notice.NoticeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter
@ToString
@NoArgsConstructor
public class NoticeDTO {

    private Long id;
    private String title;
    private String content;
    private String name;
    private NoticeType type;
    private Timestamp creationTimestamp;
    private Timestamp modifiedDate;

    public NoticeEntity toEntity(){
        return NoticeEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .type(type)
                .creationTimestamp(creationTimestamp)
                .modifiedDate(modifiedDate)
                .build();
    }
}
