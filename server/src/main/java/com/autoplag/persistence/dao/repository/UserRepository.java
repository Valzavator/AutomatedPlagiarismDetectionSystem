package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "User.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}