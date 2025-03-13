package com.machado.task_service.dto;


import com.machado.task_service.entity.Task;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private Boolean ongoing;

    private LocalDateTime updatedAt;

    public TaskDTO(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.startDate = task.getStartDate();
        this.endDate = task.getEndDate();
        this.createdAt = task.getCreatedAt();
        this.updatedAt =  task.getUpdatedAt();
        this.ongoing  = task.getOngoing();
    }
}
