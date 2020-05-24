package com.autoplag.persistence.dao.impl.springdatajpa;

import com.autoplag.persistence.dao.TaskDao;
import com.autoplag.persistence.dao.repository.TaskRepository;
import com.autoplag.persistence.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskDaoImpl implements TaskDao {
    private final TaskRepository repository;

    @Override
    public Page<Task> findAllByCourseId(Long courseId, Pageable pageable) {
        return repository.findByCourseId(courseId, pageable);
    }

    @Override
    public List<Task> findAllByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId) {
        return repository.findAllByCourseIdAndNotAssignedToGroup(courseId, groupId);
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
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }

}