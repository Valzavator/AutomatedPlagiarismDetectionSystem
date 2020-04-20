package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}