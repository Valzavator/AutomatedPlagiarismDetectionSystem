package com.autoplag.persistence.dao.impl.springdatajpa;

import com.autoplag.persistence.dao.GroupDao;
import com.autoplag.persistence.dao.repository.GroupRepository;
import com.autoplag.persistence.domain.Group;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GroupDaoImpl implements GroupDao {
    private final GroupRepository repository;

    @Override
    public Page<Group> findByCourseId(Long courseId, Pageable pageable) {
        return repository.findByCourseId(courseId, pageable);
    }

    @Override
    public Optional<Group> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return repository.findAll();
    }

    @Override
    public Group save(Group obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Group obj) {
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

    @Override
    public void deleteByIdAndCourseId(Long groupId, Long courseId) {
        if (repository.deleteByIdAndCourseId(groupId, courseId) != 1) {
            throw new EmptyResultDataAccessException(1);
        }
    }

}