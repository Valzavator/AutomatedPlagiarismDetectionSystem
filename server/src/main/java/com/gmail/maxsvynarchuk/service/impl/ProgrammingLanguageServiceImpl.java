package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.ProgrammingLanguageDao;
import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;
import com.gmail.maxsvynarchuk.service.ProgrammingLanguageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProgrammingLanguageServiceImpl implements ProgrammingLanguageService {
    private final ProgrammingLanguageDao programmingLanguageDao;

    @Transactional
    @Override
    public Optional<ProgrammingLanguage> getProgrammingLanguageById(Integer programmingLanguageId) {
        return programmingLanguageDao.findOne(programmingLanguageId);
    }

    @Transactional
    @Override
    public List<ProgrammingLanguage> getAllProgrammingLanguages() {
        return programmingLanguageDao.findAll();
    }
}
