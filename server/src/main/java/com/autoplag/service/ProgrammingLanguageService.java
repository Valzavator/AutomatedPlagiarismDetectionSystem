package com.autoplag.service;

import com.autoplag.persistence.domain.ProgrammingLanguage;

import java.util.List;
import java.util.Optional;

public interface ProgrammingLanguageService {

    Optional<ProgrammingLanguage> getProgrammingLanguageById(Integer programmingLanguageId);

    List<ProgrammingLanguage> getAllProgrammingLanguages();

}
