package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.Condition;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {


    Page<NoticeDTO> pageNoticeList(Condition condition, Pageable pageable);

    void writeNotice(NoticeDTO noticeDTO);

    NoticeDTO findNoticeById(Long id);

    NoticeDTO editNoticeForm(Long id);

    void editNotice(NoticeDTO noticeDTO);

    void deleteNotice(Long id);
}
