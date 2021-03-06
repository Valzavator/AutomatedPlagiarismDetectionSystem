package com.autoplag.persistence.dao.impl.springdatajpa;

import com.autoplag.persistence.dao.RoleDao;
import com.autoplag.persistence.dao.repository.RoleRepository;
import com.autoplag.persistence.domain.Role;
import com.autoplag.persistence.domain.type.RoleType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleDaoImpl implements RoleDao {
    private final RoleRepository repository;

    @Override
    public Optional<Role> findByName(RoleType name) {
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
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exist(Integer id) {
        return repository.existsById(id);
    }

}