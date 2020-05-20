package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.PlagDetectionResultDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.PlagDetectionResultRepository;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PlagDetectionResultDaoImpl implements PlagDetectionResultDao {
    private final PlagDetectionResultRepository repository;

    @Override
    public Optional<PlagDetectionResult> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PlagDetectionResult> findAll() {
        return repository.findAll();
    }

    @Override
    public PlagDetectionResult save(PlagDetectionResult obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(PlagDetectionResult obj) {
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