package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.ProgrammingLanguageDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.ProgrammingLanguageRepository;
import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProgrammingLanguageImpl implements ProgrammingLanguageDao {
    private final ProgrammingLanguageRepository repository;

    @Override
    public Optional<ProgrammingLanguage> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ProgrammingLanguage> findAll() {
        return repository.findAll();
    }

    @Override
    public ProgrammingLanguage save(ProgrammingLanguage obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(ProgrammingLanguage obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Integer id) {
        return repository.existsById(id);
    }
}