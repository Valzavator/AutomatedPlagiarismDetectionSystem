package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.User;

import java.util.Optional;

public interface UserService {

    boolean registerUser(User user);

    Optional<User> getUserById(Long userId);

}
