package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.persistence.domain.TaskGroupKey;
import com.autoplag.persistence.domain.type.PlagDetectionStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, TaskGroupKey> {

    @EntityGraph(value = "TaskGroup.scheduler-process.detail", type = EntityGraph.EntityGraphType.FETCH)
    List<TaskGroup> findByPlagDetectionStatusAndExpiryDateBefore(PlagDetectionStatus status, Date expiryDate);

    Optional<TaskGroup> findByIdAndPlagDetectionStatus(TaskGroupKey id, PlagDetectionStatus status);

    @EntityGraph(value = "TaskGroup.settings-results.detail", type = EntityGraph.EntityGraphType.FETCH)
    Optional<TaskGroup> findById(TaskGroupKey id);

}