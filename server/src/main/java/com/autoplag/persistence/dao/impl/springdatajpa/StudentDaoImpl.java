package com.autoplag.persistence.dao.impl.springdatajpa;

import com.autoplag.persistence.dao.StudentDao;
import com.autoplag.persistence.dao.repository.StudentRepository;
import com.autoplag.persistence.domain.Student;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class StudentDaoImpl implements StudentDao {
    private final StudentRepository repository;

    @Override
    public Page<Student> findByCreatorId(Long creatorId, Pageable pageable) {
        return repository.findByCreatorId(creatorId, pageable);
    }

    @Override
    public List<Student> findAllNotAddedToCourse(Long userId, Long courseId, Sort sort) {
        return repository.findAllNotAddedToCourse(userId, courseId, sort);
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
    public boolean existByFullName(String fullName) {
        return repository.existsByFullName(fullName);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}