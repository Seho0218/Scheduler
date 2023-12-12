package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.Condition;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {


    Page<NoticeDTO> pageNoticeList(Condition condition, Pageable pageable);

    void writeNotice(NoticeDTO notificationDTO);

    NoticeDTO findNoticeById(Long id);

    void deleteNotice(Long id);
}
