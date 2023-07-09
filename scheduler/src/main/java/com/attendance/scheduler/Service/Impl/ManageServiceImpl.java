package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Teacher.DeleteClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {

    private final ClassTableRepository classTableRepository;

    @Override
    @Transactional
    public void deleteClass(DeleteClassDTO deleteClassDTO) {
        classTableRepository.deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());
    }
}
