package com.attendance.scheduler.comment.application;

import com.attendance.scheduler.comment.domain.entity.CommentEntity;
import com.attendance.scheduler.comment.dto.CommentDTO;
import com.attendance.scheduler.comment.repository.CommentJpaRepository;
import com.attendance.scheduler.comment.repository.CommentRepository;
import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import com.attendance.scheduler.notification.repository.NoticeJpaRepository;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import com.attendance.scheduler.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public final NoticeJpaRepository noticeJpaRepository;
    public final StudentJpaRepository studentJpaRepository;
    public final StudentRepository studentRepository;
    public final CommentRepository commentRepository;
    public final CommentJpaRepository commentJpaRepository;

    @Override
    public List<CommentDTO> getCommentList(Long id) {
        return commentRepository.getCommentList(id);
    }

    @Override
    @Transactional
    public void saveComment(CommentDTO commentDTO) {
        CommentEntity entity = commentDTO.toEntity();
        NoticeEntity noticeEntity = noticeJpaRepository.findNoticeEntityById(commentDTO.getNoticeId());
        StudentEntity studentEntity = studentJpaRepository.findStudentEntityByStudentName(commentDTO.getCommentAuthor());

        entity.setNoticeEntity(noticeEntity);
        entity.setStudentEntity(studentEntity);

        commentJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteComment(CommentDTO commentDTO) {

    }
}
