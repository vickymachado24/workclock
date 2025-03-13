package com.machado.work_service.service;

import com.machado.work_service.dto.TaskDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class TaskClient {


    private final WebClient webClient;

    public TaskClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://task-service").build();
    }

    public List<TaskDTO> getTasksByWorkId(Long workId){
        return webClient.get().uri("api/task/by-work/", uriBuilder -> uriBuilder.path(workId.toString()).build())
                .retrieve().bodyToFlux(TaskDTO.class).collectList().block();
    };


    public Void deleteTasksWithWorkIdAndListOfTasksId(Long workId, List<Long> tasksToBeDeleted){
        return webClient.method(HttpMethod.DELETE)
                .uri("/api/task/", workId)
                .bodyValue(BodyInserters.fromValue(tasksToBeDeleted))  // Send request body
                .retrieve()
                .bodyToMono(Void.class).block();
    }
}