package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Repository.jpa.FindClassTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final FindClassTableRepository findClassTableRepository;
    private final ClassMapper classMapper;

    public List<ClassDTO> findClassTable() {

        List<ClassEntity> classEntityList = findClassTableRepository.findAll();

        return classEntityList.stream()
                .map(classMapper::toClassDTO)
                .collect(Collectors.toList());
    }
}
