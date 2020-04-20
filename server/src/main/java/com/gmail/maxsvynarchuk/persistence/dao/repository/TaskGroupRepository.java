package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
}