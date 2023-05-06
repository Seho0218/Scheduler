package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Repository.dao.ClassDAO;
import com.attendance.scheduler.Repository.jpa.SaveClassRepository;
import com.attendance.scheduler.Repository.jpa.SaveStudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    public final SaveClassRepository saveClassRepository;
    public final SaveStudentRepository saveStudentRepository;
    public final ClassDAO classDAO;

    @Override
    public void saveClassTable(ClassDTO classDTO) {

//        classDAO.save(classDTO);






    }
}
