package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.AccessTokenDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.AccessTokenRepository;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccessTokenDaoImpl implements AccessTokenDao {
    private final AccessTokenRepository repository;

    @Override
    public Optional<AccessToken> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<AccessToken> findAll() {
        return repository.findAll();
    }

    @Override
    public AccessToken save(AccessToken obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(AccessToken obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}