package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlagDetectionSettingRepository extends JpaRepository<PlagDetectionSetting, Long> {
}