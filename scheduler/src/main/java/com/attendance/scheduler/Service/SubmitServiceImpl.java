package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Repository.ClassRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    public final ClassRepository classRepository;

    @Override
    public void saveClassTable(ClassDTO classDTO) {
        classRepository.saveClassTable(classDTO.toEntity());
    }
}
