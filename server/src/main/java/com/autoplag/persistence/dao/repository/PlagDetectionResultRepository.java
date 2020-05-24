package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.PlagDetectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlagDetectionResultRepository extends JpaRepository<PlagDetectionResult, Long> {
}