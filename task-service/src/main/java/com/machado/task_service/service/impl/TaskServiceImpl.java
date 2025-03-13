package com.machado.task_service.service.impl;


import com.machado.task_service.service.TaskService;
import com.machado.task_service.dto.TaskDTO;
import com.machado.task_service.entity.Task;
import com.machado.task_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskDTO> getAllTasks(){
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(task ->new TaskDTO(task)).collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO  taskDTO) {
        Task task = new Task(taskDTO);
        task = taskRepository.save(task);

        return new TaskDTO(task);
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {

        Optional<Task> taskOptional = taskRepository.findById(taskDTO.getId());
        if(taskOptional.isEmpty())
            return null;
        Task toUpdate = new Task(taskDTO);
        return new TaskDTO(taskRepository.save(toUpdate));
    }

    @Override
    public TaskDTO deleteTask(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(taskOptional.isEmpty()) return null;
        taskRepository.deleteById(taskId);
        return new TaskDTO( taskOptional.get() );
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        Optional<Task> task =  taskRepository.findById(taskId);
        return task.isEmpty()? null: new TaskDTO(task.get());
    }


    @Override
    public List<TaskDTO> getTasksByWordId(Long workId) {
        List<Task> taskList = taskRepository.findAByWorkId(workId);
        return taskList.stream().map(task ->new TaskDTO(task)).collect(Collectors.toList());
    }

    @Override
    public void deleteTasksWithWorkIdAndListOfTasksId(Long workId, List<Long> taskIdsToBeDeleted) {
        if(taskIdsToBeDeleted.isEmpty()) taskRepository.deleteAllByWorkId(workId);
        else taskRepository.deleteByIdIn(taskIdsToBeDeleted);
    }
}
