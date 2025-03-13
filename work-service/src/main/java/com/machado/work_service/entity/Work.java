package com.machado.work_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private Boolean ongoing;

    private LocalDateTime updatedAt;

//
//    /**
//     * No longer managed by Spring Data JPA, we can manually add or remove based on service or API calls
//     */
//    @Transient
//    private List<Task>  taskList;

}
