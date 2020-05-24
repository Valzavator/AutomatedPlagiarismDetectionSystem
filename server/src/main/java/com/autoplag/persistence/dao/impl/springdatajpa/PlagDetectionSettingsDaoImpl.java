package com.autoplag.persistence.dao.impl.springdatajpa;

import com.autoplag.persistence.dao.PlagDetectionSettingDao;
import com.autoplag.persistence.dao.repository.PlagDetectionSettingsRepository;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PlagDetectionSettingsDaoImpl implements PlagDetectionSettingDao {
    private final PlagDetectionSettingsRepository repository;

    @Override
    public Optional<PlagDetectionSettings> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PlagDetectionSettings> findAll() {
        return repository.findAll();
    }

    @Override
    public PlagDetectionSettings save(PlagDetectionSettings obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(PlagDetectionSettings obj) {
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