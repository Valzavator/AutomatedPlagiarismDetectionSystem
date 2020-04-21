package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.RoleDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.RoleRepository;
import com.gmail.maxsvynarchuk.persistence.domain.Role;
import com.gmail.maxsvynarchuk.persistence.domain.type.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleDaoImpl implements RoleDao {
    private final RoleRepository repository;

    @Override
    public Optional<Role> findByName(UserRole name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<Role> findOne(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role save(Role obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Role obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Integer id) {
        return repository.existsById(id);
    }

}