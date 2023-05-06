package com.attendance.scheduler.Entity.Account;

import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "admin")
@DiscriminatorValue("admin")
public class AdminEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false)
    private String adminName;

    @Column(nullable = false)
    private String adminTel;
}
