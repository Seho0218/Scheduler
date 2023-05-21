package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.DeleteClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ClassTableRepository classTableRepository;
    private final ClassMapper classMapper;

    public List<ClassDTO> findClassTable() {

        List<ClassEntity> classEntityList = classTableRepository.findAll();

        return classEntityList.stream()
                .map(classMapper::toClassDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClass(DeleteClassDTO deleteClassDTO) {
        classTableRepository.deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());

    }
}
