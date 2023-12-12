package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.Condition;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.attendance.scheduler.notification.repository.NoticeRepository;
import com.attendance.scheduler.notification.repository.NotificationJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public Page<NoticeDTO> pageNoticeList(Condition condition, Pageable pageable) {
        return noticeRepository.pageNoticeList(condition, pageable);
    }

    @Override
    @Transactional
    public void writeNotice(NoticeDTO noticeDTO) {
        notificationJpaRepository.save(noticeDTO.toEntity());
    }

    @Override
    public NoticeDTO findNoticeById(Long id) {
        return noticeRepository.findPostById(id);
    }

    @Override
    @Transactional
    public void deleteNotice(Long id) {
        notificationJpaRepository.deleteById(id);
    }
}
