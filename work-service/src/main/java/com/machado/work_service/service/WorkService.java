package com.machado.work_service.service;


import com.machado.work_service.dto.WorkDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkService {
    List<WorkDTO> getAllWorks();

    WorkDTO getWork(Long id);

    WorkDTO updateWork(WorkDTO workDTO);

    WorkDTO deleteWork(Long id);

    WorkDTO saveWork(WorkDTO workDTO);
}
