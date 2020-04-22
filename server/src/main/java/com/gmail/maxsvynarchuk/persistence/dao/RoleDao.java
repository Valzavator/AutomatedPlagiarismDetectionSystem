package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Role;
import com.gmail.maxsvynarchuk.persistence.domain.type.RoleType;

import java.util.Optional;

public interface RoleDao extends GenericDao<Role, Integer> {

    Optional<Role> findByName(RoleType name);

}
