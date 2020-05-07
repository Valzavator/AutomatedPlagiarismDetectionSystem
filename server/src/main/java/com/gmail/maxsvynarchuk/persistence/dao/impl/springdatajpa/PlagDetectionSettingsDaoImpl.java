package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.PlagDetectionSettingDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.PlagDetectionSettingsRepository;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
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
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}