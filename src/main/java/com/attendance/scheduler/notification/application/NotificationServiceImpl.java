package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminRepository;
import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import com.attendance.scheduler.notification.dto.Condition;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.attendance.scheduler.notification.repository.NoticeRepository;
import com.attendance.scheduler.notification.repository.NotificationJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;

    @Override
    public Page<NoticeDTO> pageNoticeList(Condition condition, Pageable pageable) {
        return noticeRepository.pageNoticeList(condition, pageable);
    }

    @Override
    @Transactional
    public void writeNotice(NoticeDTO noticeDTO) {
        AdminEntity admin = adminRepository.findByUsernameIs(noticeDTO.getName());
        NoticeEntity entity = noticeDTO.toEntity();
        entity.setAdminEntity(admin);
        notificationJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<NoticeDTO> findNoticeById(Long id) {
        return noticeRepository.findNoticeById(id);
    }

    @Override
    public Optional<NoticeDTO> editNoticeForm(Long id) {
        return noticeRepository.editNoticeForm(id);
    }

    @Override
    @Transactional
    public void editNotice(NoticeDTO noticeDTO) {
        Optional<NoticeEntity> byId = notificationJpaRepository.findById(noticeDTO.getId());
        if (byId.isPresent()) {
            NoticeEntity noticeEntity = byId.get();
            noticeEntity.updateTitle(noticeDTO.getTitle());
            noticeEntity.updateContent(noticeDTO.getContent());
            notificationJpaRepository.save(noticeEntity);
        }
    }

    @Override
    @Transactional
    public void deleteNotice(Long id) {
        notificationJpaRepository.deleteById(id);
    }
}
