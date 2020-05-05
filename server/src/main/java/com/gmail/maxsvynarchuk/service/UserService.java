package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;

import java.util.Optional;

public interface UserService {

    User updateUser(User user);

    User addAccessTokenToUser(User user, AccessToken accessToken);

    boolean deleteAccessTokenToUser(User user, AuthorizationProvider authorizationProvider);

    boolean registerUser(User user);

    Optional<User> getUserById(Long userId);

    User getRequiredUserById(Long userId);

}
