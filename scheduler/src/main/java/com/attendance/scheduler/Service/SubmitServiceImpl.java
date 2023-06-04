package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassSaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    private final ClassSaveRepository classSaveRepository;

    @Override
    public void saveClassTable(ClassDTO classDTO) {

        if(classDTO.getStudentName()!=null) {
            String trimmedStudentName = classDTO.getStudentName().trim();
            classDTO.setStudentName(trimmedStudentName);
            classSaveRepository.save(classDTO.toEntity());
        }
    }
}
