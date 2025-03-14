package com.machado.work_service.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private String errorMessage;
}
