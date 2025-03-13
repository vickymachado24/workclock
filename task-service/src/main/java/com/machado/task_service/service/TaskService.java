package com.machado.task_service.service;


import com.machado.task_service.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO updateTask(TaskDTO taskDTO);

    TaskDTO deleteTask(Long taskId);

    TaskDTO getTask(Long taskId);

    List<TaskDTO> getTasksByWordId(Long workId);

    void deleteTasksWithWorkIdAndListOfTasksId(Long workId, List<Long> taskIdsToBeDeleted);
}
