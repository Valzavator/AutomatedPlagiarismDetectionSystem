package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;

import java.util.List;
import java.util.Optional;

public interface ProgrammingLanguageService {

    Optional<ProgrammingLanguage> getProgrammingLanguageById(Integer programmingLanguageId);

    List<ProgrammingLanguage> getAllProgrammingLanguages();

}
