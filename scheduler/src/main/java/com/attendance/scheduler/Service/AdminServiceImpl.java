package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.DeleteClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ClassTableRepository classTableRepository;

    @Override
    public void deleteClass(DeleteClassDTO deleteClassDTO) {
        classTableRepository.deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());
    }
}
