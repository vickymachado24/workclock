package com.machado.work_service.controller;


import com.machado.work_service.dto.WorkDTO;
import com.machado.work_service.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/work")
@Slf4j
@RestController
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("")
    public ResponseEntity<List<WorkDTO>> getAllWorks(){
        log.info("Work Controller - Received Request to get all task");
        List<WorkDTO> workList =  workService.getAllWorks();
        return  new ResponseEntity<>(workList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkDTO> getWork(@PathVariable Long id){
        log.info("Work Controller - Received Request to get work with id {}", id);
        WorkDTO work =  workService.getWork(id);
        return  new ResponseEntity<>(work, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<WorkDTO> updateWork(@RequestBody WorkDTO workDTO){
        log.info("Work Controller - Received Request to update work with id {}", workDTO.getId());
        WorkDTO work =  workService.updateWork(workDTO);
        return  new ResponseEntity<>(work, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWork(@PathVariable Long id){
        log.info("Work Controller - Received Request to delete work with id {}", id);
        WorkDTO work =  workService.deleteWork(id);
        return  new ResponseEntity<>("Successfully Deleted Work", HttpStatus.NO_CONTENT);
    }
    @PostMapping("")
    public ResponseEntity<WorkDTO> saveWork(@RequestBody WorkDTO workDTO){
        log.info("Work Controller - Received Request to save work");
        WorkDTO work =  workService.saveWork(workDTO);
        return  new ResponseEntity<>(work, HttpStatus.CREATED);
    }
}



