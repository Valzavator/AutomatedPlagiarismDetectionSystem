package com.autoplag.service;

import com.autoplag.persistence.domain.ProgrammingLanguage;

import java.util.List;

public interface ProgrammingLanguageService {

    ProgrammingLanguage getProgrammingLanguageById(Integer programmingLanguageId);

    List<ProgrammingLanguage> getAllProgrammingLanguages();

}
