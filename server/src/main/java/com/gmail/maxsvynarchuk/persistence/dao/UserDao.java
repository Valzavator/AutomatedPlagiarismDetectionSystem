package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {

    /**
     * Retrieve user from database identified by email.
     * @param email identifier of user
     * @return optional, which contains retrieved object or {@code null}
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists in database.
     *
     * @param email user's identifier
     * @return {@code true} if exists else {@code false}
     */
    boolean existByEmail(String email);
}
