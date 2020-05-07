package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlagDetectionSettingsRepository extends JpaRepository<PlagDetectionSettings, Long> {
}