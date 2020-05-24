package com.autoplag.persistence.dao.impl.springdatajpa;

import com.autoplag.persistence.dao.UserDao;
import com.autoplag.persistence.dao.repository.UserRepository;
import com.autoplag.persistence.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private final UserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User save(User obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(User obj) {
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