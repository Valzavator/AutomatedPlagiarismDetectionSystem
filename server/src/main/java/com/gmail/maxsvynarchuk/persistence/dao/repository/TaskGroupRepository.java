package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, TaskGroupKey> {

    @EntityGraph(value = "TaskGroup.detail", type = EntityGraph.EntityGraphType.FETCH)
    List<TaskGroup> findByPlagDetectionStatusAndExpiryDateBefore(PlagDetectionStatus status, Date expiryDate);

}