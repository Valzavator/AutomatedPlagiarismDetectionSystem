package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.RoleDao;
import com.gmail.maxsvynarchuk.persistence.dao.UserDao;
import com.gmail.maxsvynarchuk.persistence.domain.Role;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.RoleType;
import com.gmail.maxsvynarchuk.presentation.exception.AppException;
import com.gmail.maxsvynarchuk.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Transactional
    @Override
    public Optional<User> getUserById(Long userId) {
        return userDao.findOne(userId);
    }

}
