package com.attendance.scheduler.infra.email;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmailMessageDTO {
    private String from;
    private String to;
    private String subject;
    private String message;
}
