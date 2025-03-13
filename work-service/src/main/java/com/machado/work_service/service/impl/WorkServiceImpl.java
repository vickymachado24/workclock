package com.machado.work_service.service.impl;


import com.machado.work_service.service.TaskClient;
import com.machado.work_service.service.WorkService;
import com.machado.work_service.dto.TaskDTO;
import com.machado.work_service.dto.WorkDTO;
import com.machado.work_service.entity.Work;
import com.machado.work_service.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class  WorkServiceImpl implements WorkService {
    
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private TaskClient taskClient;
    
    @Override
    public List<WorkDTO> getAllWorks() {
        List<WorkDTO> workList = workRepository.findAll().stream().
                map(work -> workToDTO(work)).collect( Collectors.toList());
        return workList;
    }

    @Override
    public WorkDTO getWork(Long id) {
        Optional<Work> work = workRepository.findById(id);

        if(work.isEmpty())return null;
        WorkDTO workDTO = workToDTO(work.get());
        workDTO.setAssociatedTasks(taskClient.getTasksByWorkId(id));
        return workDTO;
    }

    @Override
    public WorkDTO updateWork(WorkDTO workDTO) {
        Optional<Work> workOptional = workRepository.findById(workDTO.getId());
        if(workOptional.isEmpty()) return null;
        Set<Long> toUpdateTasksId = workDTO.getAssociatedTasks().stream().
                map(TaskDTO::getId).collect(Collectors.toSet());
        Work work = dtoToWork(workDTO);
        List<Long> tasksToBeDeleted = null;
        if( !toUpdateTasksId.isEmpty()) {
            List<Long> previousTaskList = taskClient.getTasksByWorkId(workDTO.getId()).stream().map(TaskDTO::getId).collect(Collectors.toList());
            tasksToBeDeleted = previousTaskList.stream().filter(taskId -> !toUpdateTasksId.contains(taskId)).collect(Collectors.toList());
        }
        Work savedWork = workRepository.save(work);
        taskClient.deleteTasksWithWorkIdAndListOfTasksId(workDTO.getId(), tasksToBeDeleted);
        return workToDTO(savedWork);
    }

    @Override
    public WorkDTO deleteWork(Long id) {
        workRepository.deleteById(id);
        taskClient.deleteTasksWithWorkIdAndListOfTasksId(id, null);
        return null;
    }

    @Override
    public WorkDTO saveWork(WorkDTO workDTO) {
        return null;
    }


    public WorkDTO workToDTO( Work work){
        WorkDTO workDTO = new WorkDTO();
        workDTO.setId(work.getId());
        workDTO.setDescription(work.getDescription());
        workDTO.setStartDate(work.getStartDate());
        workDTO.setEndDate(work.getEndDate());
        workDTO.setOngoing(work.getOngoing());
        workDTO.setCreatedAt(work.getCreatedAt());
        workDTO.setUpdatedAt(work.getUpdatedAt());
        workDTO.setAssociatedTasks(taskClient.getTasksByWorkId(work.getId()));
        return workDTO;
    }

    public Work dtoToWork( WorkDTO workDTO) {
        Work work = new Work();

        work.setId(workDTO.getId());
        work.setDescription(workDTO.getDescription());
        work.setCreatedAt(workDTO.getCreatedAt());
        work.setStartDate(workDTO.getStartDate());
        work.setOngoing(workDTO.getOngoing());


        return work;
    }
}
