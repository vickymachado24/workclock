package com.machado.work_service.controller;


import com.machado.work_service.dto.WorkDTO;
import com.machado.work_service.service.WorkService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/work")
@Slf4j
@RestController
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("")
    @CircuitBreaker(name = "task", fallbackMethod = "getAllWorksFallback")
    @TimeLimiter(name = "task")
    @Retry(name = "task")
    public CompletableFuture<ResponseEntity<List<WorkDTO>>> getAllWorks() {
        log.info("Work Controller - Received Request to get all tasks");
        List<WorkDTO> workList = workService.getAllWorks();
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(workList, HttpStatus.OK));
    }

    @GetMapping("{id}")
    @CircuitBreaker(name = "task", fallbackMethod = "getWorkFallback")
    @TimeLimiter(name = "task")
    @Retry(name = "task")
    public  CompletableFuture<ResponseEntity<WorkDTO>> getWork(@PathVariable Long id) {
        log.info("Work Controller - Received Request to get work with id {}", id);
        WorkDTO work = workService.getWork(id);
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(work, HttpStatus.OK));
    }

    @PutMapping("{id}")
    @CircuitBreaker(name = "task", fallbackMethod = "updateWorkFallback")
    @TimeLimiter(name = "task")
    @Retry(name = "task")
    public  CompletableFuture<ResponseEntity<WorkDTO>> updateWork(@RequestBody WorkDTO workDTO) {
        log.info("Work Controller - Received Request to update work with id {}", workDTO.getId());
        WorkDTO work = workService.updateWork(workDTO);
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(work, HttpStatus.OK));
    }

    @DeleteMapping("{id}")
    @CircuitBreaker(name = "task", fallbackMethod = "deleteWorkFallback")
    @TimeLimiter(name = "task")
    @Retry(name = "task")
    public  CompletableFuture<ResponseEntity<String>> deleteWork(@PathVariable Long id) {
        log.info("Work Controller - Received Request to delete work with id {}", id);
        workService.deleteWork(id);
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("Successfully Deleted Work", HttpStatus.NO_CONTENT));
    }

    @PostMapping("")
    @CircuitBreaker(name = "task", fallbackMethod = "saveWorkFallback")
    @TimeLimiter(name = "task")
    @Retry(name = "task")
    public CompletableFuture<ResponseEntity<WorkDTO>> saveWork(@RequestBody WorkDTO workDTO) {
        log.info("Work Controller - Received Request to save work");
        WorkDTO work = workService.saveWork(workDTO);
        return CompletableFuture.supplyAsync(() ->new ResponseEntity<>(work, HttpStatus.CREATED));
    }

    // ---- Fallback Methods ----

    public CompletableFuture<ResponseEntity<List<WorkDTO>>> getAllWorksFallback(Throwable ex) {
        log.error("Fallback triggered for getAllWorks due to: {}", ex.getMessage());
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(List.of(), HttpStatus.SERVICE_UNAVAILABLE));
    }

    public CompletableFuture<ResponseEntity<WorkDTO>> getWorkFallback(Long id, Throwable ex) {
        log.error("Fallback triggered for getWork({}) due to: {}", id, ex.getMessage());
        WorkDTO workDTO = new WorkDTO();
        workDTO.setErrorMessage("Service Down, Please try again later");
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(workDTO, HttpStatus.SERVICE_UNAVAILABLE));
    }

    public CompletableFuture<ResponseEntity<WorkDTO>> updateWorkFallback(WorkDTO workDTO, Throwable ex) {
        log.error("Fallback triggered for updateWork({}) due to: {}", workDTO.getId(), ex.getMessage());
        WorkDTO errorWorkDto = new WorkDTO();
        workDTO.setErrorMessage("Service Down, Please try again later");
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(errorWorkDto, HttpStatus.SERVICE_UNAVAILABLE));
    }

    public CompletableFuture<ResponseEntity<String>> deleteWorkFallback(Long id, Throwable ex) {
        log.error("Fallback triggered for deleteWork({}) due to: {}", id, ex.getMessage());
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("Delete operation failed due to service unavailability", HttpStatus.SERVICE_UNAVAILABLE));
    }

    public CompletableFuture<ResponseEntity<WorkDTO>> saveWorkFallback(WorkDTO workDTO, Throwable ex) {
        log.error("Fallback triggered for saveWork due to: {}", ex.getMessage());
        WorkDTO errorWorkDto = new WorkDTO();
        workDTO.setErrorMessage("Service Down, Please try again later");
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(errorWorkDto, HttpStatus.SERVICE_UNAVAILABLE));
    }
}



