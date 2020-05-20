package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskDao extends GenericDao<Task, Long> {

    Page<Task> findAllByCourseId(Long courseId, Pageable pageable);

    List<Task> findAllByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

}
