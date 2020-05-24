package com.autoplag.service.impl;

import com.autoplag.persistence.dao.RoleDao;
import com.autoplag.persistence.dao.UserDao;
import com.autoplag.persistence.domain.Role;
import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.type.RoleType;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.presentation.exception.AppException;
import com.autoplag.presentation.exception.BadRequestException;
import com.autoplag.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User updateUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User addAccessTokenToUser(User user, AccessToken accessToken) {
        user.addAccessToken(accessToken);
        return userDao.save(user);
    }

    @Override
    public boolean deleteAccessTokenToUser(User user, AuthorizationProvider authorizationProvider) {
        AccessToken accessToken = user.getAccessToken(authorizationProvider);
        if (user.removeAccessToken(accessToken)) {
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean registerUser(User user) {
        log.debug("Attempt to register new user");

        if (userDao.existByEmail(user.getEmail())) {
            return false;
        }

        RoleType roleType = RoleType.ROLE_USER;
        if (Objects.nonNull(user.getRole()) &&
                Objects.nonNull(user.getRole().getName())) {
            roleType = user.getRole().getName();
        }
        Role role = roleDao.findByName(roleType)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRole(role);
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        userDao.save(user);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(Long userId) {
        return userDao.findOne(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public User getRequiredUserById(Long userId) {
        return userDao.findOne(userId)
                .orElseThrow(BadRequestException::new);
    }

}
