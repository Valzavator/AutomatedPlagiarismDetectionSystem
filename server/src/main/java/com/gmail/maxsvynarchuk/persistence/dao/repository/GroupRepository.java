package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}