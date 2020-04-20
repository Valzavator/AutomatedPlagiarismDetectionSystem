package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.CourseDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.CourseRepository;
import com.gmail.maxsvynarchuk.persistence.domain.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CourseDaoImpl implements CourseDao {
    private final CourseRepository repository;

    @Override
    public Optional<Course> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public Course save(Course obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Course obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}