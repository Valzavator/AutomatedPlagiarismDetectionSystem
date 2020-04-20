package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.TaskGroupDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.TaskGroupRepository;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskGroupImpl implements TaskGroupDao {
    private final TaskGroupRepository repository;

    @Override
    public Optional<TaskGroup> findOne(Long id) {
        return repository.findById(id);
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
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}