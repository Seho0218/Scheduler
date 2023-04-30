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

    @Column(nullable = false)
    private String adminName;

    @Column(nullable = false)
    private String adminTel;
}
