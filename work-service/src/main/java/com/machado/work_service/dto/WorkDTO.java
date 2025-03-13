package com.machado.work_service.dto;


import com.machado.work_service.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkDTO implements Serializable {

    private Long id;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private Boolean ongoing;

    private Float percentageCompletion;

    private LocalDateTime updatedAt;

    private List<TaskDTO> associatedTasks;


}
