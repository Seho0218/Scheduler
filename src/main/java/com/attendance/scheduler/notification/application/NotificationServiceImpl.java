package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.attendance.scheduler.notification.repository.NotificationJpaRepository;
import com.attendance.scheduler.notification.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeDTO> findAllNoticeList() {
        return noticeRepository.findAllNoticeList();
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
