package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.TaskGroupDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.TaskGroupRepository;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskGroupDaoImpl implements TaskGroupDao {
    private final TaskGroupRepository repository;

    @Override
    public List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus() {
        return repository.findByPlagDetectionStatusAndExpiryDateBefore(
                PlagDetectionStatus.PENDING,
                new Date());
    }

    @Override
    public Optional<TaskGroup> findByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status) {
        return repository.findByIdAndPlagDetectionStatus(id, status);
    }

    @Override
    public Optional<TaskGroup> findOne(TaskGroupKey taskGroupKey) {
        return repository.findById(taskGroupKey);
    }

    @Override
    public List<TaskGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public TaskGroup save(TaskGroup obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(TaskGroup obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(TaskGroupKey taskGroupKey) {
        return repository.existsById(taskGroupKey);
    }
}