package com.machado.task_service.repository;


import com.machado.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);

    List<Task> findAByWorkId(Long workId);

    void deleteByIdIn(List<Long> taskIdsToBeDeleted);

    void deleteAllByWorkId(Long workId);
}
