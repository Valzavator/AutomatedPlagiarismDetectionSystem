package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.TaskDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.TaskRepository;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskDaoImpl implements TaskDao {
    private final TaskRepository repository;

    @Override
    public List<Task> findAllTaskByCourseId(Long courseId) {
        return repository.findAllByCourseId(courseId);
    }

    @Override
    public Optional<Task> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Task save(Task obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Task obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }

}