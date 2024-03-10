package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminRepository;
import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import com.attendance.scheduler.notification.domain.notice.NoticeType;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.attendance.scheduler.notification.repository.NoticeJpaRepository;
import com.attendance.scheduler.notification.repository.NoticeRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class NotificationServiceImplTest {

    @Autowired private NoticeJpaRepository noticeJpaRepository;
    @Autowired private NoticeRepository noticeRepository;
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
            noticeJpaRepository.save(entity);
            entityManager.flush();

        }
        entityManager.clear();
    }

    @Test
    void writeNotice() {

        //given //when
        NoticeDTO noticeDTO = new NoticeDTO();

        for (int i = 0; i < 1; i++) {
            noticeDTO.setTitle(String.valueOf(i));
            noticeDTO.setContent("123");
            noticeDTO.setName("관리자");
            noticeDTO.setType(NoticeType.FAQ);
            AdminEntity admin = adminRepository.findByUsernameIs("admin");
            NoticeEntity entity = noticeDTO.toEntity();
            entity.setAdminEntity(admin);
            noticeJpaRepository.save(entity);
        }
        //then

        List<NoticeEntity> all = noticeJpaRepository.findAll();
        long count = all.size();
        Assertions.assertEquals(1, count);

    }
}