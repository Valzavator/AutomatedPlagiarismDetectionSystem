package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.vcs.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}