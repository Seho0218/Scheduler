package com.attendance.scheduler.board.dto;

import com.attendance.scheduler.board.domain.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter
@ToString
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private String name;
    private Integer views;
    private Timestamp creationTimestamp;
    private Timestamp modifiedDate;

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .views(views)
                .creationTimestamp(creationTimestamp)
                .modifiedDate(modifiedDate)
                .build();
    }
}
