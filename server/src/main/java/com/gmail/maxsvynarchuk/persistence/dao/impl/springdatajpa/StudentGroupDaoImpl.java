package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.StudentGroupDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.StudentGroupRepository;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class StudentGroupDaoImpl implements StudentGroupDao {
    private final StudentGroupRepository repository;

    @Override
    public Set<StudentGroup> findAllWhoHaveTask(Task task) {
        return repository.findAllWhoHaveTask(task);
    }

    @Override
    public Optional<StudentGroup> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<StudentGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public StudentGroup save(StudentGroup obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(StudentGroup obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}