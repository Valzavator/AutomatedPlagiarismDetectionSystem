package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.Role;
import com.autoplag.persistence.domain.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType name);

}