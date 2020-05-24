package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Integer> {
}