package com.autoplag.service;

import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;

public interface UserService {

    User addAccessTokenToUser(User user, AccessToken accessToken);

    boolean deleteAccessTokenToUser(User user, AuthorizationProvider authorizationProvider);

    boolean registerUser(User user);

    User getUserById(Long userId);

}
