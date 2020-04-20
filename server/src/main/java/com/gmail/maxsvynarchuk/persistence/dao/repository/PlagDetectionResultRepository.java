package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlagDetectionResultRepository extends JpaRepository<PlagDetectionResult, Long> {
}