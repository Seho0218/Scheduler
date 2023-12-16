package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminRepository;
import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import com.attendance.scheduler.notification.domain.notice.NoticeType;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.attendance.scheduler.notification.repository.NotificationJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceImplTest {

    @Autowired private NotificationJpaRepository notificationJpaRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private EntityManager entityManager;

    @Test
    @Transactional
//    @Rollback(value = false)
    void pageNoticeList() {

        NoticeDTO noticeDTO = new NoticeDTO();

        for (int i = 0; i < 150; i++) {
            noticeDTO.setTitle(String.valueOf(i));
            noticeDTO.setContent("123");
            noticeDTO.setName("관리자");
            noticeDTO.setType(NoticeType.FAQ);
            AdminEntity admin = adminRepository.findByUsernameIs("admin");
            NoticeEntity entity = noticeDTO.toEntity();
            entity.setAdminEntity(admin);
            notificationJpaRepository.save(entity);
            entityManager.flush();

        }
        entityManager.clear();
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