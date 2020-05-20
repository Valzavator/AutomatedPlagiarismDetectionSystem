package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.StudentDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.StudentRepository;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class StudentDaoImpl implements StudentDao {
    private final StudentRepository repository;

    @Override
    public Page<Student> findByCreatorId(Long creatorId, Pageable pageable) {
        return repository.findByCreatorId(creatorId, pageable);
    }

    @Override
    public Optional<Student> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Student save(Student obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Student obj) {
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