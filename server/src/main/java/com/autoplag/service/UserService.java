package com.autoplag.service;

import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;

import java.util.Optional;

public interface UserService {

    User updateUser(User user);

    User addAccessTokenToUser(User user, AccessToken accessToken);

    boolean deleteAccessTokenToUser(User user, AuthorizationProvider authorizationProvider);

    boolean registerUser(User user);

    Optional<User> getUserById(Long userId);

    User getRequiredUserById(Long userId);

}
