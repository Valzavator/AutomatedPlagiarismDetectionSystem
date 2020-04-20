package com.gmail.maxsvynarchuk.persistence.dao.impl.springdatajpa;

import com.gmail.maxsvynarchuk.persistence.dao.GroupDao;
import com.gmail.maxsvynarchuk.persistence.dao.repository.GroupRepository;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GroupDaoImpl implements GroupDao {
    private final GroupRepository repository;

    @Override
    public Optional<Group> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return repository.findAll();
    }

    @Override
    public Group save(Group obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Group obj) {
        repository.delete(obj);
    }

    @Override
    public boolean exist(Long id) {
        return repository.existsById(id);
    }
}