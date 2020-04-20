package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Integer> {
}