package com.attendance.scheduler.Dto;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class EmailDTO {

    private String username;

    private String email;

    @Builder
    public EmailDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
