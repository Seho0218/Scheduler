package com.attendance.scheduler.notification.domain.post;

import com.attendance.scheduler.notification.domain.notice.NoticeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "notice")
@NoArgsConstructor(access = PROTECTED)
public class post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;

    @CreationTimestamp
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    private Timestamp modifiedDate;
}
