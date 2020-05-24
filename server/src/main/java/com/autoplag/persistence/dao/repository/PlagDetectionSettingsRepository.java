package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.PlagDetectionSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlagDetectionSettingsRepository extends JpaRepository<PlagDetectionSettings, Long> {
}