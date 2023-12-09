package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.NoticeDTO;

import java.util.List;

public interface NotificationService {


    List<NoticeDTO> findAllNoticeList();

    void writeNotice(NoticeDTO notificationDTO);

    NoticeDTO findNoticeById(Long id);

    void deleteNotice(Long id);
}
