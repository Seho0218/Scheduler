package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.domain.notice.NoticeType;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceImplTest {

    @Autowired private NotificationService notificationService;

    @Test
    void pageNoticeList() {

        NoticeDTO noticeDTO = new NoticeDTO();

        for (int i = 0; i < 50; i++) {
            noticeDTO.setTitle(String.valueOf(i));
            noticeDTO.setContent("123");
            noticeDTO.setAuthor("관리자");
            noticeDTO.setType(NoticeType.FAQ);

            notificationService.writeNotice(noticeDTO);
        }

    }

    @Test
    void writeNotice() {
    }

    @Test
    void findNoticeById() {
    }

    @Test
    void deleteNotice() {
    }
}