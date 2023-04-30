package com.attendance.scheduler.entity.Account;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Admin")
public class Admin {

    @Id @GenericGenerator(name="system-uuid", strategy = "uuid")
    private Long id;

    private String adminName;

    private String adminTel;
}
