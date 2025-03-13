package com.machado.task_service.controller;


import com.machado.task_service.dto.TaskDTO;
import com.machado.task_service.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/alltask")
    @ResponseStatus
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        log.info("Task Controller - Received Request to get all task");
        List<TaskDTO> alltasks = taskService.getAllTasks();
        return new ResponseEntity<>(alltasks, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TaskDTO> saveTask(@RequestBody TaskDTO taskDto){
        log.info("Task Controller - Received Request to save task");
        TaskDTO savedTaskDto = taskService.createTask(taskDto);
        return new ResponseEntity<>(savedTaskDto, HttpStatus.CREATED);
    }

    @PatchMapping("")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDto){
        log.info("Task Controller - Received Request to update task with id {}", taskDto.getId());
        TaskDTO updatedTaskDto = taskService.updateTask(taskDto);
        return new ResponseEntity<>(updatedTaskDto, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        log.info("Task Controller - Received Request to delete task with id {}", taskId);
        TaskDTO deletedTaskDto = taskService.deleteTask(taskId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{workId}")
    public ResponseEntity<String> deleteTasksWithWorkIdAndListOfTasksId(@PathVariable Long workId, @RequestBody List<Long> taskIdsToBeDeleted){
        if(taskIdsToBeDeleted.isEmpty()) log.info("Task Controller - Received Request to delete all task with workId {}", workId);
        else log.info("Task Controller - Received Request to delete task with ids {} for wordId", taskIdsToBeDeleted, workId);
        taskService.deleteTasksWithWorkIdAndListOfTasksId(workId, taskIdsToBeDeleted);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskDetails(@PathVariable Long taskId){
        log.info("Task Controller - Received Request to get task details for with id {}", taskId);
        TaskDTO taskDTO = taskService.getTask(taskId);
        return new ResponseEntity<TaskDTO>(taskDTO, HttpStatus.OK);
    }

    @GetMapping("/by-work/{workId}")
    public ResponseEntity<List<TaskDTO>> getTaskDetailsWithWorkId(@PathVariable Long workId){
        log.info("Task Controller - Received Request to get task details for with word id {}", workId);
        List<TaskDTO> taskDTOList = taskService.getTasksByWordId(workId);
        return new ResponseEntity<>(taskDTOList, HttpStatus.OK);
    }
}
