package com.autoplag.persistence.dao;

import com.autoplag.persistence.domain.Role;
import com.autoplag.persistence.domain.type.RoleType;

import java.util.Optional;

public interface RoleDao extends GenericDao<Role, Integer> {

    Optional<Role> findByName(RoleType name);

}
