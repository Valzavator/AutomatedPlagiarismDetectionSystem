package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}