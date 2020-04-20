package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.PlagDetectionSettingDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.PlagDetectionSettingRepository;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PlagDetectionSettingDaoImpl implements PlagDetectionSettingDao {
    private final PlagDetectionSettingRepository repository;

    @Override
    public Optional<PlagDetectionSetting> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PlagDetectionSetting> findAll() {
        return repository.findAll();
    }

    @Override
    public PlagDetectionSetting save(PlagDetectionSetting obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(PlagDetectionSetting obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}