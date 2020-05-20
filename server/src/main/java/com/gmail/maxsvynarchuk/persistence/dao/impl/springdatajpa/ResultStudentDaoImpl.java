package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.ResultStudentDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.ResultStudentRepository;
import com.gmail.maxsvynarchuk.persistence.domain.ResultStudent;
import com.gmail.maxsvynarchuk.persistence.domain.ResultStudentKey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ResultStudentDaoImpl implements ResultStudentDao {
    private final ResultStudentRepository repository;

    @Override
    public Optional<ResultStudent> findOne(ResultStudentKey id) {
        return repository.findById(id);
    }

    @Override
    public List<ResultStudent> findAll() {
        return repository.findAll();
    }

    @Override
    public ResultStudent save(ResultStudent obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(ResultStudent obj) {
        repository.delete(obj);
    }

    @Override
    public void deleteById(ResultStudentKey id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exist(ResultStudentKey id) {
        return repository.existsById(id);
    }
}